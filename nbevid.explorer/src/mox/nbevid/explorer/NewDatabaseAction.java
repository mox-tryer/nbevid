/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mox.nbevid.model.SpendingsDatabase;
import mox.nbevid.persistence.SpendingsDbPersister;
import org.netbeans.api.progress.BaseProgressUtils;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;


@ActionID(
        category = "File",
        id = "mox.nbevid.explorer.NewDatabaseAction"
)
@ActionRegistration(
        iconBase = "mox/nbevid/explorer/newProject.png",
        displayName = "#CTL_NewDatabaseAction"
)
@ActionReferences({
  @ActionReference(path = "Menu/File", position = 1300),
  @ActionReference(path = "Toolbars/File", position = 300),
  @ActionReference(path = "Shortcuts", name = "D-N")
})
@Messages({"CTL_NewDatabaseAction=&New Database", "CTL_NewDatabaseTitle=New Database"})
public final class NewDatabaseAction implements ActionListener {
  @Messages({"MSG_ErrorCreatingDb=Error occured while creating database", "MSG_CreatingDb=Creating database", "CTL_CreatingDbTitle=Creating Database"})
  @Override
  public void actionPerformed(ActionEvent e) {
    final NewDatabasePanel panel = new NewDatabasePanel();
    final DialogDescriptor dd = new DialogDescriptor(panel, Bundle.CTL_NewDatabaseTitle());
    panel.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        dd.setValid(panel.validateValues());
      }
    });
    dd.setValid(panel.validateValues());
    if (DialogDisplayer.getDefault().notify(dd).equals(DialogDescriptor.OK_OPTION)) {
      final ProgressHandle progress = ProgressHandle.createHandle(Bundle.MSG_CreatingDb());
      BaseProgressUtils.runOffEventThreadWithProgressDialog(new Runnable() {
        @Override
        public void run() {
          try {
            SpendingsDatabase db = new SpendingsDatabase(panel.getDbName());
            SpendingsDbPersister.getDefault().save(db, panel.getDbFolder());
            
            final Preferences prefs = NbPreferences.forModule(NbEvidExplorerRootNode.class);
            final int lastIdUsed = prefs.getInt("evidInstLastId", 0);
            final int newId = lastIdUsed + 1;
            prefs.putInt("evidInstLastId", newId);
            final Preferences evidInstancePrefs = prefs.node("evidInst." + newId);
            evidInstancePrefs.put("name", db.getName());
            evidInstancePrefs.put("path", panel.getDbFolder().getAbsolutePath());
            evidInstancePrefs.flush();
            prefs.flush();
          } catch (BackingStoreException | IOException ex) {
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(Bundle.MSG_ErrorCreatingDb(), NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(msg);
            Exceptions.printStackTrace(ex);
          }

        }
      }, Bundle.CTL_CreatingDbTitle(), progress, false, 200, 1000);

//      TopComponent nbEvidExplorer = WindowManager.getDefault().findTopComponent("NbEvidExplorerTopComponent");
//      if (nbEvidExplorer instanceof ExplorerManager.Provider) {
//        final ExplorerManager manager = ((ExplorerManager.Provider) nbEvidExplorer).getExplorerManager();
//        final Node rootNode = manager.getRootContext();
//        rootNode.
//      } else {
//        NotifyDescriptor.Message msg = new NotifyDescriptor.Message(Bundle.MSG_ErrorCreatingDb(), NotifyDescriptor.ERROR_MESSAGE);
//        DialogDisplayer.getDefault().notify(msg);
//      }
    }
  }
}
