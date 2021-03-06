/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import mox.nbevid.explorer.nodes.DbInfo;
import mox.nbevid.explorer.nodes.DbInfoRegistry;
import mox.nbevid.persistence.PersisterException;
import org.netbeans.api.progress.BaseProgressUtils;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;


@ActionID(
        category = "File",
        id = "mox.nbevid.explorer.OpenDatabaseAction"
)
@ActionRegistration(
        iconBase = "mox/nbevid/explorer/openProject.png",
        displayName = "#CTL_OpenDatabaseAction"
)
@ActionReferences({
  @ActionReference(path = "Menu/File", position = 1350),
  @ActionReference(path = "Toolbars/File", position = 350)
})
@Messages({"CTL_OpenDatabaseAction=&Open Database", "CTL_OpenDatabaseTitle=Open Database"})
public final class OpenDatabaseAction implements ActionListener {
  @Messages({"MSG_ErrorOpeningDb=Error occured while opening database", "MSG_OpeningDb=Opening database", "CTL_OpeningDbTitle=Opening Database",
    "MSG_AlreadyOpened=Database is already opened"})
  @Override
  public void actionPerformed(ActionEvent e) {
    final OpenDatabasePanel panel = new OpenDatabasePanel();
    final DialogDescriptor dd = new DialogDescriptor(panel, Bundle.CTL_OpenDatabaseTitle());
    panel.addChangeListener((chEv) -> dd.setValid(panel.validateValues()));
    dd.setValid(panel.validateValues());
    if (DialogDisplayer.getDefault().notify(dd).equals(DialogDescriptor.OK_OPTION)) {
      final ProgressHandle progress = ProgressHandle.createHandle(Bundle.MSG_OpeningDb());
      BaseProgressUtils.runOffEventThreadWithProgressDialog(() -> {
        try {
          if (EvidPreferences.getInstance().hasEvidInstance(panel.getDbFile().getCanonicalPath())) {
            DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(Bundle.MSG_AlreadyOpened(), NotifyDescriptor.Message.ERROR_MESSAGE));
            return;
          }
          
          File dbFile = panel.getDbFile().getCanonicalFile();
          String name = FileUtil.toFileObject(dbFile).getName();

          DbInfo dbInfo = new DbInfo(dbFile);
          dbInfo.load(panel.getPassword());
          
          DbInfoRegistry.getInstance().put(dbInfo);
          
          EvidPreferences.getInstance().addEvidInstance(name, dbFile.getAbsolutePath());
        } catch (BackingStoreException | IOException | PersisterException ex) {
          NotifyDescriptor.Message msg = new NotifyDescriptor.Message(Bundle.MSG_ErrorOpeningDb(), NotifyDescriptor.ERROR_MESSAGE);
          DialogDisplayer.getDefault().notifyLater(msg);
          Exceptions.printStackTrace(ex);
        }
      }, Bundle.CTL_OpeningDbTitle(), progress, false, 200, 1000);
    }
  }
}
