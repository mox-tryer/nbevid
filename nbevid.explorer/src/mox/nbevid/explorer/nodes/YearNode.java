/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.util.List;
import mox.nbevid.model.Month;
import mox.nbevid.model.Year;
import mox.nbevid.model.YearMonth;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;


/**
 *
 * @author martin
 */
public class YearNode extends AbstractNode implements Lookup.Provider {
  private final MonthFactory monthFactory;
  private final Year year;
  private final DbInfo dbInfo;

  private YearNode(MonthFactory monthFactory, Year year, DbInfo dbInfo) {
    super(Children.create(monthFactory, true), Lookups.fixed(year, dbInfo));
    this.monthFactory = monthFactory;
    this.year = year;
    this.dbInfo = dbInfo;

    setIconBaseWithExtension("mox/nbevid/explorer/resources/year.png");
    this.setDisplayName(Integer.toString(year.getYear()));
  }

  public static YearNode create(int yearKey, DbInfo dbInfo) {
    final Year year = dbInfo.getDb().getYear(yearKey);
    final MonthFactory monthFactory = new MonthFactory(year, dbInfo);
    return new YearNode(monthFactory, year, dbInfo);
  }


  private static class MonthFactory extends ChildFactory<YearMonth> {
    private final Year year;
    private final DbInfo dbInfo;

    public MonthFactory(Year year, DbInfo dbInfo) {
      this.year = year;
      this.dbInfo = dbInfo;
    }

    @Override
    protected boolean createKeys(List<YearMonth> toPopulate) {
      for (Month month : Month.values()) {
        toPopulate.add(year.getMonth(month));
      }
      return true;
    }

    @Override
    protected Node createNodeForKey(YearMonth key) {
      return MonthNode.create(key, dbInfo);
    }
  }
}
