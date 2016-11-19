/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer;


import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mox.nbevid.persistence.SpendingsDbPersister;
import org.openide.util.ChangeSupport;


/**
 *
 * @author martin
 */
public class OpenDatabasePanel extends javax.swing.JPanel {
  private static final long serialVersionUID = 1L;
  private final ChangeSupport changeSupport = new ChangeSupport(this);
  
  /**
   * Creates new form OpenDatabasePanel
   */
  public OpenDatabasePanel() {
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
    
    dbFolderTextField.getDocument().addDocumentListener(docListener);
  }
  
  public void addChangeListener(ChangeListener l) {
    changeSupport.addChangeListener(l);
  }
  
  public void removeChangeListener(ChangeListener l) {
    changeSupport.removeChangeListener(l);
  }
  
  public boolean validateValues() {
    if (dbFolderTextField.getText().isEmpty()) {
      return false;
    }
    
    File dbFolder = new File(dbFolderTextField.getText());
    if (!dbFolder.exists()) {
      return false;
    }
    if (!dbFolder.isDirectory()) {
      return false;
    }
    
    return SpendingsDbPersister.getDefault().mainDbFile(dbFolder).exists();
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

    dbFolderLabel = new javax.swing.JLabel();
    dbFolderTextField = new javax.swing.JTextField();
    dbFolderChooseButton = new javax.swing.JButton();
    passwordLabel = new javax.swing.JLabel();
    passwordField = new javax.swing.JPasswordField();

    org.openide.awt.Mnemonics.setLocalizedText(dbFolderLabel, org.openide.util.NbBundle.getMessage(OpenDatabasePanel.class, "OpenDatabasePanel.dbFolderLabel.text", new Object[] {})); // NOI18N

    dbFolderTextField.setText(org.openide.util.NbBundle.getMessage(OpenDatabasePanel.class, "OpenDatabasePanel.dbFolderTextField.text", new Object[] {})); // NOI18N

    org.openide.awt.Mnemonics.setLocalizedText(dbFolderChooseButton, org.openide.util.NbBundle.getMessage(OpenDatabasePanel.class, "OpenDatabasePanel.dbFolderChooseButton.text", new Object[] {})); // NOI18N
    dbFolderChooseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        dbFolderChooseButtonActionPerformed(evt);
      }
    });

    passwordLabel.setLabelFor(passwordField);
    org.openide.awt.Mnemonics.setLocalizedText(passwordLabel, org.openide.util.NbBundle.getMessage(OpenDatabasePanel.class, "OpenDatabasePanel.passwordLabel.text", new Object[] {})); // NOI18N

    passwordField.setText(org.openide.util.NbBundle.getMessage(OpenDatabasePanel.class, "OpenDatabasePanel.passwordField.text", new Object[] {})); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(dbFolderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
          .addComponent(dbFolderTextField))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(dbFolderChooseButton)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(dbFolderLabel)
          .addComponent(dbFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(dbFolderChooseButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(passwordLabel)
          .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void dbFolderChooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dbFolderChooseButtonActionPerformed
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int result = chooser.showDialog(this, Bundle.CTL_SelectFolder());
    if (result == JFileChooser.APPROVE_OPTION) {
      dbFolderTextField.setText(chooser.getSelectedFile().getAbsolutePath());
    }
  }//GEN-LAST:event_dbFolderChooseButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton dbFolderChooseButton;
  private javax.swing.JLabel dbFolderLabel;
  private javax.swing.JTextField dbFolderTextField;
  private javax.swing.JPasswordField passwordField;
  private javax.swing.JLabel passwordLabel;
  // End of variables declaration//GEN-END:variables
}
