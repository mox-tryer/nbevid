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
            
            EvidPreferences.getInstance().addEvidInstance(db.getName(), panel.getDbFolder().getCanonicalPath());
          } catch (BackingStoreException | IOException ex) {
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(Bundle.MSG_ErrorCreatingDb(), NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(msg);
            Exceptions.printStackTrace(ex);
          }

        }
      }, Bundle.CTL_CreatingDbTitle(), progress, false, 200, 1000);
    }
  }
}
