/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.nodes;


import org.openide.util.NbBundle;


/**
 *
 * @author martin
 */
@NbBundle.Messages({"UnlockDatabasePanel.title=Unlock Database", "UnlockDatabasePanel.message.error=Error unlocking database"})
public class UnlockDatabasePanel extends javax.swing.JPanel {
  private static final long serialVersionUID = -3297443481217287855L;

  /**
   * Creates new form UnlockDatabasePanel
   */
  public UnlockDatabasePanel() {
    initComponents();
  }
  
  public char[] getPassword() {
    char[] password = passwordField.getPassword();
    if (password == null) {
      return new char[0];
    }
    
    char[] copy = new char[password.length];
    System.arraycopy(password, 0, copy, 0, password.length);
    return copy;
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
   * Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    passwordLabel = new javax.swing.JLabel();
    passwordField = new javax.swing.JPasswordField();

    org.openide.awt.Mnemonics.setLocalizedText(passwordLabel, org.openide.util.NbBundle.getMessage(UnlockDatabasePanel.class, "UnlockDatabasePanel.passwordLabel.text", new Object[] {})); // NOI18N

    passwordField.setText(org.openide.util.NbBundle.getMessage(UnlockDatabasePanel.class, "UnlockDatabasePanel.passwordField.text", new Object[] {})); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(passwordLabel)
          .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPasswordField passwordField;
  private javax.swing.JLabel passwordLabel;
  // End of variables declaration//GEN-END:variables
}