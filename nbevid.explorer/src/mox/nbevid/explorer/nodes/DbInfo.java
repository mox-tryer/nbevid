/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.event.ChangeListener;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.persistence.PersisterException;
import mox.nbevid.persistence.SpendingsDbPersister;
import org.netbeans.spi.actions.AbstractSavable;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;


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
  
  private boolean dirty = false;
  
  private final ChangeSupport dbOpenedChangeSupport = new ChangeSupport(this);

  public DbInfo(File dbFile) {
    this.name = FileUtil.toFileObject(dbFile).getName();
    this.dbFile = dbFile;
    this.db = null;
  }

  public void dbChanged() {
    register();
    
    this.dirty = true;
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

  public boolean load() {
    if (isDbOpened()) {
      return true;
    }

    final UnlockDatabasePanel panel = new UnlockDatabasePanel();
    if (DialogDescriptor.OK_OPTION.equals(DialogDisplayer.getDefault().notify(new DialogDescriptor(panel, Bundle.UnlockDatabasePanel_title())))) {
      try {
        load(panel.getPassword());
        return true;
      } catch (PersisterException ex) {
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Bundle.UnlockDatabasePanel_message_error(), NotifyDescriptor.ERROR_MESSAGE));
      }
    }
    
    return false;
  }

  public void load(char[] password) throws PersisterException {
    synchronized (dbLock) {
      if (db == null) {
        setPassword(password);
        this.db = SpendingsDbPersister.getDefault().load(dbFile, this.password);
        dbOpenedChangeSupport.fireChange();
      }
    }
  }
  
  public void addDbOpenChangeListener(ChangeListener l) {
    dbOpenedChangeSupport.addChangeListener(l);
  }
  
  public void removeDbOpenChangeListener(ChangeListener l) {
    dbOpenedChangeSupport.removeChangeListener(l);
  }

  private void setPassword(char[] password) {
    if (password == null) {
      this.password = new char[0];
    } else {
      this.password = new char[password.length];
      System.arraycopy(password, 0, this.password, 0, password.length);
    }
  }
  
  public void changePassword(char[] password) {
    setPassword(password);
    dbChanged();
  }
  
  public void save(SpendingsDatabase db, char[] password) throws IOException {
    synchronized (dbLock) {
      this.db = db;
      setPassword(password);
      
      handleSave();
    }
  }

  public File getDbFile() {
    return dbFile;
  }

  @Override
  protected String findDisplayName() {
    return db.getName();
  }

  public boolean isDirty() {
    return dirty;
  }

  @Override
  protected void handleSave() throws IOException {
    synchronized (dbLock) {
      try {
        SpendingsDbPersister.getDefault().save(db, dbFile, password);
        this.dirty = false;
      } catch (PersisterException ex) {
        if (ex.getCause() instanceof IOException) {
          throw (IOException) ex.getCause();
        } else {
          throw new IOException(ex);
        }
      }
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
