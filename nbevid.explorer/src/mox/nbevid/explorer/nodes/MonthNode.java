/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import mox.nbevid.model.YearMonth;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;


/**
 *
 * @author martin
 */
public class MonthNode extends AbstractNode implements Lookup.Provider {
  private MonthNode(YearMonth yearMonth, DbInfo dbInfo) {
    super(Children.LEAF);
    
    setDisplayName(NbBundle.getMessage(MonthNode.class, "month." + yearMonth.getMonth().name() + ".text"));
  }
  
  public static MonthNode create(YearMonth yearMonth, DbInfo dbInfo) {
    return new MonthNode(yearMonth, dbInfo);
  }
}
