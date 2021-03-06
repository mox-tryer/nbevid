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
import java.util.prefs.BackingStoreException;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import mox.nbevid.explorer.EvidPreferences;
import mox.nbevid.explorer.PasswordPanel;
import mox.nbevid.explorer.editors.DbItemsEditorPanel;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.model.Year;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
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
  private final DbInfo dbInfo;

  private static final OpenAction openAction = new OpenAction();
  private static final CloseAction closeAction = new CloseAction();
  private static final ChangePasswordAction changePasswordAction = new ChangePasswordAction();
  private static final NewYearAction newYearAction = new NewYearAction();

  private DatabaseNode(DbInfo dbInfo) {
    super(Children.create(new YearFactory(dbInfo), true), Lookups.fixed(dbInfo, new DatabaseEditorCookie()));
    this.dbInfo = dbInfo;

    setIconBaseWithExtension("mox/nbevid/explorer/resources/db.png");

    setDisplayName(dbInfo.getName());
  }

  public static DatabaseNode create(File dbFile) {
    return new DatabaseNode(DbInfoRegistry.getInstance().findOrCreate(dbFile));
  }

  @Override
  public Action getPreferredAction() {
    return openAction;
  }

  @Override
  public Action[] getActions(boolean context) {
    return new Action[] {getPreferredAction(), closeAction, changePasswordAction, null, newYearAction};
  }


  private static class YearFactory extends ChildFactory.Detachable<Integer> {
    private final DbInfo dbInfo;

    public YearFactory(DbInfo dbInfo) {
      this.dbInfo = dbInfo;
    }

    @Override
    protected boolean createKeys(List<Integer> toPopulate) {
      if (dbInfo.getDb() != null) {
        toPopulate.addAll(dbInfo.getDb().getYearKeys());
      }
      return true;
    }

    @Override
    protected Node createNodeForKey(Integer key) {
      return YearNode.create(key, dbInfo);
    }

    @Override
    protected void addNotify() {
      if (!dbInfo.isDbOpened()) {
        dbInfo.load();
      }
      if (dbInfo.getDb() != null) {
        dbInfo.getDb().addYearsChangeListener((e) -> refresh(false));
      }
      dbInfo.addDbOpenChangeListener((e) -> refresh(false));
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
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        
        if (!dbInfo.isDbOpened()) {
          if (!dbInfo.load()) {
            return;
          }
        }
        
        final SpendingsDatabase db = dbInfo.getDb();

        int proposedYear = LocalDate.now().getYear();
        for (int yi : db.getYearKeys()) {
          if (yi == proposedYear) {
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
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        final DatabaseEditorCookie editorCookie = node.getLookup().lookup(DatabaseEditorCookie.class);
        
        if (!dbInfo.isDbOpened()) {
          if (!dbInfo.load()) {
            return;
          }
        }
        
        editorCookie.openEditor(node, dbInfo.getDb(), dbInfo);
      }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
      if ((activatedNodes == null) || (activatedNodes.length == 0)) {
        return false;
      }

      for (Node node : activatedNodes) {
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
  
  @NbBundle.Messages("LBL_Action_Close=Close")
  public static class CloseAction extends NodeAction {
    private static final long serialVersionUID = 1L;

    private CloseAction() {
      putValue(NAME, Bundle.LBL_Action_Close());
    }

    @Override
    protected boolean asynchronous() {
      return false;
    }

    @NbBundle.Messages({
      "# {0} - db name",
      "MSG_CloseAction_Error=Error closing database {0}"
    })
    @Override
    protected void performAction(Node[] activatedNodes) {
      for (Node node : activatedNodes) {
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        
        try {
          EvidPreferences.getInstance().removeEvidInstance(dbInfo.getDbFile().getAbsolutePath());
        } catch (BackingStoreException ex) {
          DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(Bundle.MSG_CloseAction_Error(dbInfo.getName()), NotifyDescriptor.ERROR_MESSAGE));
        }
      }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
      if ((activatedNodes == null) || (activatedNodes.length == 0)) {
        return false;
      }

      for (Node node : activatedNodes) {
        if (node.getLookup().lookup(DbInfo.class) == null) {
          return false;
        }
        
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        if (dbInfo.isDirty()) {
          return false;
        }
      }

      return true;
    }

    @Override
    public String getName() {
      return Bundle.LBL_Action_Close();
    }

    @Override
    public HelpCtx getHelpCtx() {
      return null;
    }
  }
  
  @NbBundle.Messages("LBL_Action_ChangePassword=Change Password...")
  public static class ChangePasswordAction extends NodeAction {
    private static final long serialVersionUID = 1L;

    private ChangePasswordAction() {
      putValue(NAME, Bundle.LBL_Action_ChangePassword());
    }

    @Override
    protected boolean asynchronous() {
      return false;
    }

    @NbBundle.Messages("ChangePasswordAction.dialogTitle=Change Password")
    @Override
    protected void performAction(Node[] activatedNodes) {
      for (Node node : activatedNodes) {
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        
        PasswordPanel pp = new PasswordPanel();
        DialogDescriptor dd = new DialogDescriptor(pp, Bundle.ChangePasswordAction_dialogTitle());
        if (DialogDescriptor.OK_OPTION.equals(DialogDisplayer.getDefault().notify(dd))) {
          dbInfo.changePassword(pp.getPassword());
        }
      }
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
      if ((activatedNodes == null) || (activatedNodes.length == 0)) {
        return false;
      }

      for (Node node : activatedNodes) {
        if (node.getLookup().lookup(DbInfo.class) == null) {
          return false;
        }
        
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);
        if (!dbInfo.isDbOpened()) {
          return false;
        }
      }

      return true;
    }

    @Override
    public String getName() {
      return Bundle.LBL_Action_ChangePassword();
    }

    @Override
    public HelpCtx getHelpCtx() {
      return null;
    }
  }
}
