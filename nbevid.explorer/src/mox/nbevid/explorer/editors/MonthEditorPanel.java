/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.editors;


import java.awt.Image;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import mox.nbevid.explorer.nodes.DbInfo;
import mox.nbevid.model.Item;
import mox.nbevid.model.YearMonth;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.swing.etable.ETable;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.UndoRedo;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;


/**
 *
 * @author martin
 */
public class MonthEditorPanel extends javax.swing.JPanel implements MultiViewElement {
  private static final long serialVersionUID = 1L;
  
  private MultiViewElementCallback callback;

  private final JToolBar toolbar = new JToolBar();
  
  private final YearMonth month;
  private final DbInfo dbInfo;
  private final Lookup lookup;
  
  private final DefaultComboBoxModel<Item> itemsComboBoxModel = new DefaultComboBoxModel<>();
  private final DefaultFormatterFactory fmtFactory;
  
  private final DataTable table;
  private final MonthItemsOverviewTableModel tableModel;

  /**
   * Creates new form MonthEditorPanel
   */
  public MonthEditorPanel(YearMonth month, DbInfo dbInfo) {
    this.month = month;
    this.dbInfo = dbInfo;
    this.lookup = Lookups.fixed(month, dbInfo);
    
    itemsComboBoxModel.removeAllElements();
    month.getYear().getYearItems().forEach((item) -> itemsComboBoxModel.addElement(item));
    
    DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("###0.00"));
    fmt.setCommitsOnValidEdit(true);
    fmt.setAllowsInvalid(false);
    //DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("#.0###############"));
    fmt.setValueClass(BigDecimal.class);
    this.fmtFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
    
    initComponents();
    
    tableModel = new MonthItemsOverviewTableModel(month);
    table = new DataTable(tableModel);
    initTable();
    itemsOverviewScrollPane.setViewportView(table);
  }

  private void initTable() {
    // TODO: velkost fontu v tabulke nacitat z Options
    table.setFont(table.getFont().deriveFont(15f));
    
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setAutoResizeMode(DataTable.AUTO_RESIZE_OFF);
    table.setCellSelectionEnabled(false);
    table.setColumnSelectionOn(MouseEvent.BUTTON3, ETable.ColumnSelection.NO_SELECTION);

    table.adjustColumns();
  }
  
  @NbBundle.Messages({"# {0} - value", "MSG_InvalidValue=Invalid number {0}"})
  private void addValue() {
    if (!itemValueFormattedTextField.isEditValid()) {
      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Bundle.MSG_InvalidValue(itemValueFormattedTextField.getText()), NotifyDescriptor.ERROR_MESSAGE));
      return;
    }
    
    try {
      itemValueFormattedTextField.commitEdit();
    } catch (ParseException ex) {
      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Bundle.MSG_InvalidValue(itemValueFormattedTextField.getText()), NotifyDescriptor.ERROR_MESSAGE));
      return;
    }
    
    Item selectedItem = itemsComboBox.getItemAt(itemsComboBox.getSelectedIndex());
    BigDecimal value = (BigDecimal) itemValueFormattedTextField.getValue();
    month.addValue(selectedItem, value);
    tableModel.fireItemChanged(selectedItem);
    dbInfo.dbChanged();
  }
  
  private void resetValue() {
    if (!itemValueFormattedTextField.isEditValid()) {
      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Bundle.MSG_InvalidValue(itemValueFormattedTextField.getText()), NotifyDescriptor.ERROR_MESSAGE));
      return;
    }
    
    try {
      itemValueFormattedTextField.commitEdit();
    } catch (ParseException ex) {
      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Bundle.MSG_InvalidValue(itemValueFormattedTextField.getText()), NotifyDescriptor.ERROR_MESSAGE));
      return;
    }
    
    Item selectedItem = itemsComboBox.getItemAt(itemsComboBox.getSelectedIndex());
    BigDecimal value = (BigDecimal) itemValueFormattedTextField.getValue();
    month.setValue(selectedItem, value);
    tableModel.fireItemChanged(selectedItem);
    dbInfo.dbChanged();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
   * Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    splitPane = new javax.swing.JSplitPane();
    itemsOverviewScrollPane = new javax.swing.JScrollPane();
    itemEditPanel = new javax.swing.JPanel();
    itemLabel = new javax.swing.JLabel();
    itemsComboBox = new javax.swing.JComboBox<>();
    itemValueFormattedTextField = new javax.swing.JFormattedTextField(BigDecimal.ZERO.setScale(2));
    addValueButton = new javax.swing.JButton();
    resetValueButton = new javax.swing.JButton();

    splitPane.setDividerLocation(200);
    splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    splitPane.setContinuousLayout(true);
    splitPane.setOneTouchExpandable(true);
    splitPane.setTopComponent(itemsOverviewScrollPane);

    org.openide.awt.Mnemonics.setLocalizedText(itemLabel, org.openide.util.NbBundle.getMessage(MonthEditorPanel.class, "MonthEditorPanel.itemLabel.text", new Object[] {})); // NOI18N

    itemsComboBox.setModel(this.itemsComboBoxModel);

    itemValueFormattedTextField.setFormatterFactory(this.fmtFactory);
    itemValueFormattedTextField.setText(org.openide.util.NbBundle.getMessage(MonthEditorPanel.class, "MonthEditorPanel.itemValueFormattedTextField.text", new Object[] {})); // NOI18N
    itemValueFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        itemValueFormattedTextFieldActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(addValueButton, org.openide.util.NbBundle.getMessage(MonthEditorPanel.class, "MonthEditorPanel.addValueButton.text", new Object[] {})); // NOI18N
    addValueButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addValueButtonActionPerformed(evt);
      }
    });

    org.openide.awt.Mnemonics.setLocalizedText(resetValueButton, org.openide.util.NbBundle.getMessage(MonthEditorPanel.class, "MonthEditorPanel.resetValueButton.text", new Object[] {})); // NOI18N
    resetValueButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetValueButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout itemEditPanelLayout = new javax.swing.GroupLayout(itemEditPanel);
    itemEditPanel.setLayout(itemEditPanelLayout);
    itemEditPanelLayout.setHorizontalGroup(
      itemEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(itemEditPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(itemLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(itemsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(itemValueFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(addValueButton)
        .addGap(26, 26, 26)
        .addComponent(resetValueButton)
        .addContainerGap(134, Short.MAX_VALUE))
    );
    itemEditPanelLayout.setVerticalGroup(
      itemEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(itemEditPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(itemEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(itemLabel)
          .addComponent(itemsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(itemValueFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(addValueButton)
          .addComponent(resetValueButton))
        .addContainerGap(201, Short.MAX_VALUE))
    );

    splitPane.setRightComponent(itemEditPanel);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(splitPane)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

  private void addValueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addValueButtonActionPerformed
    addValue();
  }//GEN-LAST:event_addValueButtonActionPerformed

  private void resetValueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetValueButtonActionPerformed
    resetValue();
  }//GEN-LAST:event_resetValueButtonActionPerformed

  private void itemValueFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemValueFormattedTextFieldActionPerformed
    addValue();
  }//GEN-LAST:event_itemValueFormattedTextFieldActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addValueButton;
  private javax.swing.JPanel itemEditPanel;
  private javax.swing.JLabel itemLabel;
  private javax.swing.JFormattedTextField itemValueFormattedTextField;
  private javax.swing.JComboBox<Item> itemsComboBox;
  private javax.swing.JScrollPane itemsOverviewScrollPane;
  private javax.swing.JButton resetValueButton;
  private javax.swing.JSplitPane splitPane;
  // End of variables declaration//GEN-END:variables

  @Override
  public JComponent getVisualRepresentation() {
    return this;
  }

  @Override
  public JComponent getToolbarRepresentation() {
    return toolbar;
  }

  @Override
  public Action[] getActions() {
    if (callback != null) {
      return callback.createDefaultActions();
    }
    return new Action[0];
  }

  @Override
  public Lookup getLookup() {
    return lookup;
  }

  @Override
  public void componentOpened() {
  }

  @Override
  public void componentClosed() {
  }

  @Override
  public void componentShowing() {
  }

  @Override
  public void componentHidden() {
  }

  @Override
  public void componentActivated() {
  }

  @Override
  public void componentDeactivated() {
  }

  @Override
  public UndoRedo getUndoRedo() {
    return UndoRedo.NONE;
  }

  @Override
  public void setMultiViewCallback(MultiViewElementCallback callback) {
    this.callback = callback;
  }

  @Override
  public CloseOperationState canCloseElement() {
    return CloseOperationState.STATE_OK;
  }
  
  public static final class Description implements MultiViewDescription, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final Image icon;
    private final YearMonth month;
    private final DbInfo dbInfo;

    public Description(Image icon, YearMonth month, DbInfo dbInfo) {
      this.icon = icon;
      this.month = month;
      this.dbInfo = dbInfo;
    }

    @Override
    public int getPersistenceType() {
      return TopComponent.PERSISTENCE_NEVER;
    }

    @NbBundle.Messages("MonthEditorPanel.displayName=Editor")
    @Override
    public String getDisplayName() {
      return Bundle.MonthEditorPanel_displayName();
    }

    @Override
    public Image getIcon() {
      return icon;
    }

    @Override
    public HelpCtx getHelpCtx() {
      return null;
    }

    @Override
    public String preferredID() {
      return "MonthItemsEditor";
    }

    @Override
    public MultiViewElement createElement() {
      return new MonthEditorPanel(month, dbInfo);
    }
  }
  
  @NbBundle.Messages({"COL_ItemValue=Value"})
  private static class MonthItemsOverviewTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final YearMonth yearMonth;

    public MonthItemsOverviewTableModel(YearMonth yearMonth) {
      this.yearMonth = yearMonth;
    }

    @Override
    public int getRowCount() {
      return yearMonth.getYear().getYearItems().size();
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
      switch (columnIndex) {
        case 0:
          return Bundle.COL_ItemName();
        case 1:
          return Bundle.COL_ItemValue();
        default:
          return "???";
      }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch (columnIndex) {
        case 1:
          return BigDecimal.class;
        default:
          return String.class;
      }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Item item = getItem(rowIndex);

      switch (columnIndex) {
        case 0:
          return item.getItemName();
        case 1:
          return yearMonth.getValues().get(item);
        default:
          return "???";
      }
    }

    public Item getItem(int rowIndex) {
      return yearMonth.getYear().getYearItems().get(rowIndex);
    }
    
    private int getItemIndex(Item item) {
      return yearMonth.getYear().getYearItems().indexOf(item);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }
    
    private void fireItemChanged(Item item) {
      int rowIndex = getItemIndex(item);
      fireTableCellUpdated(rowIndex, 1);
    }
  }
}
