/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
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
      //
    }
  }
}
