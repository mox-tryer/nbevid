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
import org.openide.util.ChangeSupport;
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

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(dbFolderLabel)
          .addComponent(dbNameLabel))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(dbFolderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
          .addComponent(dbNameTextField))
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
  private javax.swing.JLabel dbNameLabel;
  private javax.swing.JTextField dbNameTextField;
  // End of variables declaration//GEN-END:variables
}