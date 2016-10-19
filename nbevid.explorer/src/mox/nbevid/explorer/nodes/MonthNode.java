/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import java.beans.BeanInfo;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import mox.nbevid.explorer.editors.MonthEditorPanel;
import mox.nbevid.model.YearMonth;
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
public class MonthNode extends AbstractNode implements Lookup.Provider {
  private static final OpenAction openAction = new OpenAction();
  
//  private final YearMonth yearMonth;
  
  private MonthNode(YearMonth yearMonth, DbInfo dbInfo) {
    super(Children.LEAF, Lookups.fixed(yearMonth, dbInfo));
    
//    this.yearMonth = yearMonth;
    
    setIconBaseWithExtension("mox/nbevid/explorer/resources/month.png");
    setDisplayName(NbBundle.getMessage(MonthNode.class, "month." + yearMonth.getMonth().name() + ".text"));
  }
  
  public static MonthNode create(YearMonth yearMonth, DbInfo dbInfo) {
    return new MonthNode(yearMonth, dbInfo);
  }
  
  @Override
  public Action getPreferredAction() {
    return openAction;
  }

  @Override
  public Action[] getActions(boolean context) {
    return new Action[] {getPreferredAction()};
  }
  
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
        final YearMonth yearMonth = node.getLookup().lookup(YearMonth.class);
        final DbInfo dbInfo = node.getLookup().lookup(DbInfo.class);

        MultiViewDescription[] descriptions = new MultiViewDescription[1];
        descriptions[0] = new MonthEditorPanel.Description(node.getIcon(BeanInfo.ICON_COLOR_16x16), yearMonth, dbInfo);

        TopComponent dbEditor = MultiViewFactory.createMultiView(descriptions, descriptions[0]);

        dbEditor.setDisplayName(String.format("%s %d", NbBundle.getMessage(MonthNode.class, "month." + yearMonth.getMonth().name() + ".text"), yearMonth.getYear().getYear()));
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
        if (node.getLookup().lookup(YearMonth.class) == null) {
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
