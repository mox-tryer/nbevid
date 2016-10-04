/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.io.File;
import mox.nbevid.model.SpendingsDatabase;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;


/**
 *
 * @author martin
 */
public class DatabaseNode extends AbstractNode {
  private final String name;
  private final File dbDirectory;
  private final SpendingsDatabase db;

  public DatabaseNode(String name, File dbDirectory, SpendingsDatabase db) {
    // TODO: tu uz by mala prist nacitana SpendingsDatabase (zatial bez rokov)
    //super(Children.create(factory, true));
    super(Children.LEAF);
    this.name = name;
    this.dbDirectory = dbDirectory;
    this.db = db;
  }

//  @Override
//  public String getHtmlDisplayName() {
//    return name;
//  }

  @Override
  public String getDisplayName() {
    return name;
  }
}
