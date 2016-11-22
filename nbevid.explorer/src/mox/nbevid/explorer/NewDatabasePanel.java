/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.io.File;
import java.io.IOException;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;


/**
 *
 * @author martin
 */
@NbBundle.Messages({"CTL_SelectFolder=Select"})
public class NewDatabasePanel extends javax.swing.JPanel {
  private static final long serialVersionUID = 1L;
  private final ChangeSupport changeSupport = new ChangeSupport(this);
  
  /**
   * Creates new form NewDatabasePanel
   */
  public NewDatabasePanel() {
    initComponents();
    
    DocumentListener docListener = new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        changeSupport.fireChange();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        changeSupport.fireChange();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        changeSupport.fireChange();
      }
    };
    
    dbNameTextField.getDocument().addDocumentListener(docListener);
    dbFolderTextField.getDocument().addDocumentListener(docListener);
  }
  
  public void addChangeListener(ChangeListener l) {
    changeSupport.addChangeListener(l);
  }
  
  public void removeChangeListener(ChangeListener l) {
    changeSupport.removeChangeListener(l);
  }
  
  public boolean validateValues() {
    if (dbNameTextField.getText().isEmpty()) {
      return false;
    }
    
    if (dbFolderTextField.getText().isEmpty()) {
      return false;
    }
    
    File dbFolder = new File(dbFolderTextField.getText());
    if (!dbFolder.exists()) {
      return false;
    }
    return dbFolder.isDirectory();
  }
  
  public String getDbName() {
    return dbNameTextField.getText();
  }
  
  public File getDbFolder() {
    return new File(dbFolderTextField.getText());
  }
  
  public char[] getPassword() {
    return passwordField.getPassword();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
   * Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    dbNameLabel = new javax.swing.JLabel();
    dbNameTextField = new javax.swing.JTextField();
    dbFolderLabel = new javax.swing.JLabel();
    dbFolderChooseButton = new javax.swing.JButton();
    dbFolderTextField = new javax.swing.JTextField();
    passwordLabel = new javax.swing.JLabel();
    passwordField = new javax.swing.JPasswordField();

    dbNameLabel.setLabelFor(dbNameTextField);
    org.openide.awt.Mnemonics.setLocalizedText(dbNameLabel, org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.dbNameLabel.text", new Object[] {})); // NOI18N

    dbNameTextField.setText(org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.dbNameTextField.text", new Object[] {})); // NOI18N

    dbFolderLabel.setLabelFor(dbFolderTextField);
    org.openide.awt.Mnemonics.setLocalizedText(dbFolderLabel, org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.dbFolderLabel.text", new Object[] {})); // NOI18N

    org.openide.awt.Mnemonics.setLocalizedText(dbFolderChooseButton, org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.dbFolderChooseButton.text", new Object[] {})); // NOI18N
    dbFolderChooseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        dbFolderChooseButtonActionPerformed(evt);
      }
    });

    dbFolderTextField.setText(org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.dbFolderTextField.text", new Object[] {})); // NOI18N

    passwordLabel.setLabelFor(passwordField);
    org.openide.awt.Mnemonics.setLocalizedText(passwordLabel, org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.passwordLabel.text", new Object[] {})); // NOI18N

    passwordField.setText(org.openide.util.NbBundle.getMessage(NewDatabasePanel.class, "NewDatabasePanel.passwordField.text", new Object[] {})); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(dbFolderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(dbNameLabel)
          .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(dbFolderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
          .addComponent(dbNameTextField)
          .addComponent(passwordField))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(dbFolderChooseButton)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(dbNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(dbNameLabel))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(dbFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(dbFolderChooseButton)
          .addComponent(dbFolderLabel))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(passwordLabel)
          .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  @NbBundle.Messages({
    "NewDatabasePanel.fileChoose.title=New Database Folder",
    "NewDatabasePanel.fileChoose.approve=Select"
  })
  private void dbFolderChooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbFolderChooseButtonActionPerformed
    File f = new FileChooserBuilder(NewDatabasePanel.class)
            .setTitle(Bundle.NewDatabasePanel_fileChoose_title())
            .setApproveText(Bundle.NewDatabasePanel_fileChoose_approve())
            .setDefaultWorkingDirectory(new File(System.getProperty("user.home")))
            .setDirectoriesOnly(true)
            .showOpenDialog();
    
    if (f != null) {
      try {
        dbFolderTextField.setText(f.getCanonicalPath());
      } catch (IOException ex) {
        Exceptions.printStackTrace(ex);
      }
    }
  }//GEN-LAST:event_dbFolderChooseButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton dbFolderChooseButton;
  private javax.swing.JLabel dbFolderLabel;
  private javax.swing.JTextField dbFolderTextField;
  private javax.swing.JLabel dbNameLabel;
  private javax.swing.JTextField dbNameTextField;
  private javax.swing.JPasswordField passwordField;
  private javax.swing.JLabel passwordLabel;
  // End of variables declaration//GEN-END:variables
}
