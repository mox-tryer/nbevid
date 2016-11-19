/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.io.File;
import java.io.IOException;
import java.util.Objects;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.persistence.SpendingsDbPersister;
import org.netbeans.spi.actions.AbstractSavable;


/**
 *
 * @author martin
 */
public class DbInfo extends AbstractSavable {
  private final String name;
  private final File dbFile;
  
  private final Object dbLock = new Object();
  private SpendingsDatabase db;
  private char[] password;

  public DbInfo(String name, File dbDirectory) {
    this.name = name;
    this.dbFile = dbDirectory;
    this.db = null;
  }
  
  public void dbChanged() {
    register();
  }

  public String getName() {
    return name;
  }

  public SpendingsDatabase getDb() {
    synchronized (dbLock) {
      return db;
    }
  }
  
  public boolean isDbOpened() {
    synchronized (dbLock) {
      return db != null;
    }
  }
  
  public void load(char[] password) throws IOException {
    synchronized (dbLock) {
      if (db == null) {
        this.password = new char[password.length];
        System.arraycopy(password, 0, this.password, 0, password.length);
        this.db = SpendingsDbPersister.getDefault().load(dbFile, this.password);
      }
    }
  }

  public File getDbFile() {
    return dbFile;
  }

  @Override
  protected String findDisplayName() {
    return db.getName();
  }

  @Override
  protected void handleSave() throws IOException {
    synchronized (dbLock) {
      SpendingsDbPersister.getDefault().save(db, dbFile, password);
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.dbFile);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DbInfo other = (DbInfo) obj;
    return Objects.equals(this.dbFile, other.dbFile);
  }
}
