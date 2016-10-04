/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;
import mox.nbevid.persistence.SpendingsDbPersister;
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
    private final Preferences prefs;
    
    Factory() {
      this.prefs = NbPreferences.forModule(NbEvidExplorerRootNode.class);
      
      this.prefs.addNodeChangeListener(new NodeChangeListener() {
        @Override
        public void childAdded(NodeChangeEvent evt) {
          processEvent(evt);
        }

        @Override
        public void childRemoved(NodeChangeEvent evt) {
          processEvent(evt);
        }

        private void processEvent(NodeChangeEvent evt) {
          if (evt.getChild().name().startsWith("evidInst.")) {
            refresh(false);
          }
        }
      });      
    }
    
    @Override
    protected boolean createKeys(List<RootNodeKey> toPopulate) {
      ArrayList<RootNodeKey> keys = new ArrayList<>();
      try {
        for (String child : prefs.childrenNames()) {
          if (child.startsWith("evidInst.")) {
            Preferences evidInstancePrefs = prefs.node(child);
            String name = evidInstancePrefs.get("name", "");
            String path = evidInstancePrefs.get("path", "");
            if (!name.isEmpty() && !path.isEmpty()) {
              keys.add(new RootNodeKey(name, new File(path)));
            }
          }
        }
      } catch (BackingStoreException ex) {
        Exceptions.printStackTrace(ex);
      }
      
      Collections.sort(keys);
      toPopulate.addAll(keys);
      
      return true;
    }

    @Override
    protected Node createNodeForKey(RootNodeKey key) {
      return key.getRootNode();
    }
    
    public void reloadRootNodes() {
      refresh(false);
    }
  }
  
  private static class RootNodeKey implements Comparable<RootNodeKey> {
    private final String name;
    private final File dbDirectory;

    public RootNodeKey(String name, File dbDirectory) {
      this.name = name;
      this.dbDirectory = dbDirectory;
    }

    private Node getRootNode() {
      try {
        // TODO: dokoncit
        return new DatabaseNode(name, dbDirectory, SpendingsDbPersister.getDefault().load(dbDirectory));
      } catch (IOException ex) {
        Exceptions.printStackTrace(ex);
        return null;
      }
    }

    @Override
    public int compareTo(RootNodeKey o) {
      return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 97 * hash + Objects.hashCode(this.dbDirectory);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final RootNodeKey other = (RootNodeKey) obj;
      if (!Objects.equals(this.dbDirectory, other.dbDirectory)) {
        return false;
      }
      return true;
    }
  }
}
