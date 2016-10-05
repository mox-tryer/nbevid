/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.beans.BeanInfo;
import java.io.File;
import javax.swing.Action;
import mox.nbevid.explorer.editors.DbItemsEditorPanel;
import mox.nbevid.model.SpendingsDatabase;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.nodes.AbstractNode;
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

  public DatabaseNode(String name, File dbDirectory, SpendingsDatabase db) {
    // TODO: tu uz by mala prist nacitana SpendingsDatabase (zatial bez rokov)
    //super(Children.create(factory, true));
    super(Children.LEAF, Lookups.fixed(db, new DbInfo(dbDirectory)));
    this.name = name;
    this.dbDirectory = dbDirectory;
    this.db = db;

    setIconBaseWithExtension("mox/nbevid/explorer/resources/db.png");

    setDisplayName(name);
  }

  @Override
  public Action getPreferredAction() {
    return openAction;
  }

  @Override
  public Action[] getActions(boolean context) {
    return new Action[] {getPreferredAction()};
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

        MultiViewDescription[] descriptions = new MultiViewDescription[1];
        descriptions[0] = new DbItemsEditorPanel.Description(node.getIcon(BeanInfo.ICON_COLOR_16x16), db, dbInfo);

        TopComponent dbEditor = MultiViewFactory.createMultiView(descriptions, descriptions[0]);

        dbEditor.setDisplayName(db.getName());
        dbEditor.open();
        dbEditor.requestActive();
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
