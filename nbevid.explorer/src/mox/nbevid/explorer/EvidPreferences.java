/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;
import org.openide.util.NbPreferences;


/**
 *
 * @author martin
 */
public class EvidPreferences {
  private static final EvidPreferences instance = new EvidPreferences();

  private static final String EVID_INSTANCE_NODE_PREFIX = "evidInst.";
  private static final String EVID_INSTANCE_LAST_ID_PROPERTY = "evidInstLastId";
  private static final String NAME_PROPERTY = "name";
  private static final String PATH_PROPERTY = "path";

  private final Preferences prefs;

  private final ChangeSupport evidInstChangeSupport = new ChangeSupport(this);

  private EvidPreferences() {
    this.prefs = NbPreferences.forModule(EvidPreferences.class);

    this.prefs.addNodeChangeListener(new NodeChangeListener() {
      @Override
      public void childAdded(NodeChangeEvent evt) {
        if (evt.getChild().name().startsWith(EVID_INSTANCE_NODE_PREFIX)) {
          evidInstChangeSupport.fireChange();
        }
      }

      @Override
      public void childRemoved(NodeChangeEvent evt) {
        if (evt.getChild().name().startsWith(EVID_INSTANCE_NODE_PREFIX)) {
          evidInstChangeSupport.fireChange();
        }
      }
    });
  }

  public static EvidPreferences getInstance() {
    return instance;
  }

  public void addEvidInstancesChangeListener(ChangeListener listener) {
    evidInstChangeSupport.addChangeListener(listener);
  }

  public void removeEvidInstancesChangeListener(ChangeListener listener) {
    evidInstChangeSupport.removeChangeListener(listener);
  }

  public List<EvidInstance> allEvidInstances() throws BackingStoreException {
    ArrayList<EvidInstance> instances = new ArrayList<>();

    for (String child : prefs.childrenNames()) {
      if (child.startsWith(EVID_INSTANCE_NODE_PREFIX)) {
        Preferences evidInstancePrefs = prefs.node(child);
        String name = evidInstancePrefs.get(NAME_PROPERTY, "");
        String path = evidInstancePrefs.get(PATH_PROPERTY, "");
        if (!name.isEmpty() && !path.isEmpty()) {
          int id = Integer.parseInt(child.substring(EVID_INSTANCE_NODE_PREFIX.length()));
          instances.add(new EvidInstance(id, name, path));
        }
      }
    }

    return instances;
  }

  public void addEvidInstance(String name, String path) throws BackingStoreException {
    final int lastIdUsed = prefs.getInt(EVID_INSTANCE_LAST_ID_PROPERTY, 0);
    final int newId = lastIdUsed + 1;
    prefs.putInt(EVID_INSTANCE_LAST_ID_PROPERTY, newId);
    final Preferences evidInstancePrefs = prefs.node(EVID_INSTANCE_NODE_PREFIX + newId);
    evidInstancePrefs.put("name", name);
    evidInstancePrefs.put("path", path);
    evidInstancePrefs.flush();
    prefs.flush();
  }
  
  public void removeEvidInstance(String path) throws BackingStoreException {
    final ArrayList<String> disinheritedChildren = new ArrayList<>();
    
    for (String child : prefs.childrenNames()) {
      if (child.startsWith(EVID_INSTANCE_NODE_PREFIX)) {
        Preferences evidInstancePrefs = prefs.node(child);
        String instPath = evidInstancePrefs.get(PATH_PROPERTY, "");
        if (instPath.equals(path)) {
          disinheritedChildren.add(child);
        }
      }
    }
    
    for (String disinheritedChild : disinheritedChildren) {
      Preferences evidInstancePrefs = prefs.node(disinheritedChild);
      if (evidInstancePrefs != null) {
        evidInstancePrefs.removeNode();
      }
    }
  }
  
  public boolean hasEvidInstance(String path) throws BackingStoreException {
    for (EvidInstance evidInst : allEvidInstances()) {
      if (Objects.equals(evidInst.getPath(), path)) {
        return true;
      }
    }
    
    return false;
  }


  public static final class EvidInstance {
    private final int id;
    private final String name;
    private final String path;

    public EvidInstance(int id, String name, String path) {
      this.id = id;
      this.name = name;
      this.path = path;
    }

    public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getPath() {
      return path;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 17 * hash + this.id;
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
      final EvidInstance other = (EvidInstance) obj;
      return this.id == other.id;
    }
  }
}
