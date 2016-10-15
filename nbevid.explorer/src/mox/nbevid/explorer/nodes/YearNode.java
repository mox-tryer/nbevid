/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import mox.nbevid.model.YearInfo;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;


/**
 *
 * @author martin
 */
public class YearNode extends AbstractNode implements Lookup.Provider {
  private final YearInfo yearInfo;
  
  public YearNode(YearInfo yearInfo) {
    super(Children.LEAF);
    this.yearInfo = yearInfo;
    
    this.setDisplayName(Integer.toString(yearInfo.getYear()));
  }
}
