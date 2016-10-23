/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.io.IOException;
import java.util.List;
import mox.nbevid.model.Month;
import mox.nbevid.model.Year;
import mox.nbevid.model.YearInfo;
import mox.nbevid.model.YearMonth;
import mox.nbevid.persistence.SpendingsDbPersister;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;


/**
 *
 * @author martin
 */
public class YearNode extends AbstractNode implements Lookup.Provider {
  private final MonthFactory monthFactory;
  private final YearInfo yearInfo;
  private final DbInfo dbInfo;
  private final InstanceContent yearLookupContent;
  private final Lookup yearLookup;

  private YearNode(MonthFactory monthFactory, YearInfo yearInfo, DbInfo dbInfo, InstanceContent yearLookupContent, Lookup yearLookup) {
    super(Children.create(monthFactory, true), new ProxyLookup(Lookups.fixed(yearInfo, dbInfo), yearLookup));
    this.monthFactory = monthFactory;
    this.yearInfo = yearInfo;
    this.dbInfo = dbInfo;
    this.yearLookupContent = yearLookupContent;
    this.yearLookup = yearLookup;

    setIconBaseWithExtension("mox/nbevid/explorer/resources/year.png");
    this.setDisplayName(Integer.toString(yearInfo.getYear()));
  }

  public static YearNode create(YearInfo yearInfo, DbInfo dbInfo) {
    final InstanceContent yearLookupContent = new InstanceContent();
    final Lookup yearLookup = new AbstractLookup(yearLookupContent);
    final MonthFactory monthFactory = new MonthFactory(yearInfo, dbInfo, yearLookupContent, yearLookup);
    return new YearNode(monthFactory, yearInfo, dbInfo, yearLookupContent, yearLookup);
  }


  private static class MonthFactory extends ChildFactory<YearMonth> {
    private final YearInfo yearInfo;
    private final DbInfo dbInfo;
    private final InstanceContent yearLookupContent;
    private final Lookup yearLookup;
    
    private final Object yearLock = new Object();

    public MonthFactory(YearInfo yearInfo, DbInfo dbInfo, InstanceContent yearLookupContent, Lookup yearLookup) {
      this.yearInfo = yearInfo;
      this.dbInfo = dbInfo;
      this.yearLookupContent = yearLookupContent;
      this.yearLookup = yearLookup;
    }
    
    Year getYear() throws IOException {
      synchronized (yearLock) {
        Year year;
        if (!yearInfo.isLoaded()) {
          year = SpendingsDbPersister.getDefault().load(dbInfo.getDbDirectory(), yearInfo);
          yearInfo.getDb().addLoadedYear(year);
        } else {
          year = yearInfo.get();
        }
        
        if (yearLookup.lookup(Year.class) == null) {
          yearLookupContent.add(year);
        }
        
        return year;
      }
    }

    @NbBundle.Messages({"MSG_YearLoadError=Error while loading year"})
    @Override
    protected boolean createKeys(List<YearMonth> toPopulate) {
      Year year;
      try {
        year = getYear();
      } catch (IOException ex) {
        DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(Bundle.MSG_YearLoadError(), NotifyDescriptor.ERROR_MESSAGE));
        return true;
      }
      
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
