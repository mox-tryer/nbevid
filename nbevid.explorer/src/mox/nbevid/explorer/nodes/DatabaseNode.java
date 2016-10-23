/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import javax.swing.Action;
import mox.nbevid.explorer.editors.DbItemsEditorPanel;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.model.Year;
import mox.nbevid.model.YearInfo;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.NodeAction;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;


/**
 *
 * @author martin
 */
public class DatabaseNode extends AbstractNode implements Lookup.Provider {
  private final String name;
  private final File dbDirectory;
  private final SpendingsDatabase db;

  private static final OpenAction openAction = new OpenAction();
  private static final NewYearAction newYearAction = new NewYearAction();

  private DatabaseNode(String name, File dbDirectory, SpendingsDatabase db, DbInfo dbInfo) {
    super(Children.create(new YearFactory(db, dbInfo), true), Lookups.fixed(db, dbInfo, new DatabaseEditorCookie()));
    this.name = name;
    this.dbDirectory = dbDirectory;
    this.db = db;

    setIconBaseWithExtension("mox/nbevid/explorer/resources/db.png");

    setDisplayName(name);
  }

  public static DatabaseNode create(String name, File dbDirectory, SpendingsDatabase db) {
    return new DatabaseNode(name, dbDirectory, db, new DbInfo(db, dbDirectory));
  }

  @Override
  public Action getPreferredAction() {
    return openAction;
  }

  @Override
  public Action[] getActions(boolean context) {
    return new Action[] {getPreferredAction(), null, newYearAction};
  }


  private static class YearFactory extends ChildFactory<YearInfo> {
    private final SpendingsDatabase db;
    private final DbInfo dbInfo;

    public YearFactory(SpendingsDatabase db, DbInfo dbInfo) {
      this.db = db;
      this.dbInfo = dbInfo;
      db.addYearsChangeListener((e) -> refresh(false));
    }

    @Override
    protected boolean createKeys(List<YearInfo> toPopulate) {
      toPopulate.addAll(db.getYearInfos());
      return true;
    }

    @Override
    protected Node createNodeForKey(YearInfo key) {
      return YearNode.create(key, dbInfo);
    }
  }


  @NbBundle.Messages("LBL_Action_NewYear=New Year...")
  public static class NewYearAction extends NodeAction {
    private static final long serialVersionUID = 1L;

    private NewYearAction() {
      putValue(NAME, Bundle.LBL_Action_NewYear());
    }

    @Override
    protected boolean asynchronous() {
      return false;
    }

    @NbBundle.Messages("LBL_NewYearTitle=New Year")
    @Override
    protected void performAction(Node[] activatedNodes) {
      for (Node node : activatedNodes) {
        final SpendingsDatabase db = node.getLookup().lookup(SpendingsDatabase.class);
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);

        int proposedYear = LocalDate.now().getYear();
        for (YearInfo yi : db.getYearInfos()) {
          if (yi.getYear() == proposedYear) {
            proposedYear++;
          }
        }

        NewYearPanel p = new NewYearPanel(proposedYear, db);
        DialogDescriptor dd = new DialogDescriptor(p, Bundle.LBL_NewYearTitle());
        p.addChangeListener((e) -> dd.setValid(p.validateValues()));
        if (DialogDisplayer.getDefault().notify(dd).equals(DialogDescriptor.OK_OPTION)) {
          Year newYear = new Year(db, p.getSelectedYear(), p.getSelectedItems());
          db.addNewYear(newYear);
          dbInfo.dbChanged();
        }
      }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
      if ((activatedNodes == null) || (activatedNodes.length != 1)) {
        return false;
      }

      for (Node node : activatedNodes) {
        if (node.getLookup().lookup(SpendingsDatabase.class) == null) {
          return false;
        }

        if (node.getLookup().lookup(DbInfo.class) == null) {
          return false;
        }
      }

      return true;
    }

    @Override
    public String getName() {
      return Bundle.LBL_Action_NewYear();
    }

    @Override
    public HelpCtx getHelpCtx() {
      return null;
    }
  }


  public static final class DatabaseEditorCookie {
    private TopComponent editor = null;
    private final Object lock = new Object();

    public void openEditor(Node node, SpendingsDatabase db, DbInfo dbInfo) {
      synchronized (lock) {
        if (editor == null) {
          MultiViewDescription[] descriptions = new MultiViewDescription[1];
          descriptions[0] = new DbItemsEditorPanel.Description(node.getIcon(BeanInfo.ICON_COLOR_16x16), db, dbInfo);

          editor = MultiViewFactory.createMultiView(descriptions, descriptions[0]);
          
          TopComponent.getRegistry().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
              if (TopComponent.Registry.PROP_TC_CLOSED.equals(evt.getPropertyName())) {
                if (evt.getNewValue() == editor) {
                  TopComponent.getRegistry().removePropertyChangeListener(this);

                  editor = null;
                }
              }
            }
          });
        }

        editor.setDisplayName(db.getName());
        editor.open();
        editor.requestActive();
      }
    }
  }


  @NbBundle.Messages("LBL_Action_Open=Open")
  public static class OpenAction extends NodeAction {
    private static final long serialVersionUID = 1L;

    private OpenAction() {
      putValue(NAME, Bundle.LBL_Action_Open());
    }

    @Override
    protected boolean asynchronous() {
      return false;
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
      for (Node node : activatedNodes) {
        final SpendingsDatabase db = node.getLookup().lookup(SpendingsDatabase.class);
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        final DatabaseEditorCookie editorCookie = node.getLookup().lookup(DatabaseEditorCookie.class);
        
        editorCookie.openEditor(node, db, dbInfo);
      }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
      if ((activatedNodes == null) || (activatedNodes.length == 0)) {
        return false;
      }

      for (Node node : activatedNodes) {
        if (node.getLookup().lookup(SpendingsDatabase.class) == null) {
          return false;
        }

        if (node.getLookup().lookup(DbInfo.class) == null) {
          return false;
        }
        
        if (node.getLookup().lookup(DatabaseEditorCookie.class) == null) {
          return false;
        }
      }

      return true;
    }

    @Override
    public String getName() {
      return Bundle.LBL_Action_Open();
    }

    @Override
    public HelpCtx getHelpCtx() {
      return null;
    }
  }
}
