/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.io.File;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;


/**
 *
 * @author martin
 */
public class NbEvidExplorerRootNode extends AbstractNode {
  private NbEvidExplorerRootNode(Factory factory) {
    super(Children.create(factory, true));
  }
  
  public static NbEvidExplorerRootNode create() {
    return new NbEvidExplorerRootNode(new Factory());
  }

  @Override
  public String getHtmlDisplayName() {
    return "Evid Explorer";
  }

  @Override
  public String getDisplayName() {
    return "Evid Explorer";
  }

  @Override
  public String getName() {
    return "Evid Explorer";
  }
  
  private static class Factory extends ChildFactory<RootNodeKey> {
    @Override
    protected boolean createKeys(List<RootNodeKey> toPopulate) {
      Preferences prefs = NbPreferences.forModule(NbEvidExplorerRootNode.class);
      
      try {
        for (String child : prefs.childrenNames()) {
          if (child.startsWith("evidInst.")) {
            Preferences evidInstancePrefs = prefs.node(child);
            String name = evidInstancePrefs.get("name", "");
            String path = evidInstancePrefs.get("path", "");
            if (!name.isEmpty() && !path.isEmpty()) {
              toPopulate.add(new RootNodeKey(name, new File(path)));
            }
          }
        }
      } catch (BackingStoreException ex) {
        Exceptions.printStackTrace(ex);
      }
      
      return true;
    }

    @Override
    protected Node createNodeForKey(RootNodeKey key) {
      return key.getRootNode();
    }
  }
  
  private static class RootNodeKey {
    private final String name;
    private final File file;

    public RootNodeKey(String name, File file) {
      this.name = name;
      this.file = file;
    }

    private Node getRootNode() {
      // TODO: dokoncit
      return new DatabaseNode();
    }
  }
}
