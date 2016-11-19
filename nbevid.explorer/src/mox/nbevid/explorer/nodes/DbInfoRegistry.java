/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.io.File;
import java.util.HashMap;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;


/**
 *
 * @author martin
 */
public class DbInfoRegistry {
  private static final DbInfoRegistry INSTANCE = new DbInfoRegistry();
  
  private final Object lock = new Object();
  private final HashMap<String, DbInfo> dbInfos = new HashMap<>();
  
  private DbInfoRegistry() {
  }

  public static DbInfoRegistry getInstance() {
    return INSTANCE;
  }
  
  public DbInfo findOrCreate(File dbFile) {
    synchronized (lock) {
      final String path = dbFile.getAbsolutePath();
      DbInfo db = dbInfos.get(path);
      if (db == null) {
        FileObject fo = FileUtil.toFileObject(dbFile);
        db = new DbInfo(fo.getName(), dbFile);
        dbInfos.put(path, db);
      }
      return db;
    }
  }
  
  public void put(DbInfo dbInfo) {
    if (dbInfo == null) {
      return;
    }
    
    synchronized (lock) {
      final String path = dbInfo.getDbFile().getAbsolutePath();
      dbInfos.put(path, dbInfo);
    }
  }
}
