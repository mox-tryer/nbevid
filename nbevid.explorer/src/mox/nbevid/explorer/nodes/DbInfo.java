/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.io.File;


/**
 *
 * @author martin
 */
public class DbInfo {
  private final File dbDirectory;

  public DbInfo(File dbDirectory) {
    this.dbDirectory = dbDirectory;
  }

  public File getDbDirectory() {
    return dbDirectory;
  }
}
