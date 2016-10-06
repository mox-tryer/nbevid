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
  private final SpendingsDatabase db;
  private final File dbDirectory;

  public DbInfo(SpendingsDatabase db, File dbDirectory) {
    this.db = db;
    this.dbDirectory = dbDirectory;
  }
  
  public void dbChanged() {
    register();
  }

  public SpendingsDatabase getDb() {
    return db;
  }

  public File getDbDirectory() {
    return dbDirectory;
  }

  @Override
  protected String findDisplayName() {
    return db.getName();
  }

  @Override
  protected void handleSave() throws IOException {
    SpendingsDbPersister.getDefault().save(db, dbDirectory);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + Objects.hashCode(this.dbDirectory);
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
    return Objects.equals(this.dbDirectory, other.dbDirectory);
  }
}
