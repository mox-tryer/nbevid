/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mox.nbevid.explorer.editors;


import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.netbeans.swing.etable.ETable;


/**
 *
 * @author martin
 */
public class DataTable extends ETable {
  private static final long serialVersionUID = 1L;
  private final int spacing = 6;
  private final boolean isOnlyAdjustLarger = true;

  public DataTable() {
    initUI();
  }

  public DataTable(TableModel dm) {
    super(dm);
    initUI();
  }

  public DataTable(TableModel dm, TableColumnModel cm) {
    super(dm, cm);
    initUI();
  }

  public DataTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, cm, sm);
    initUI();
  }

  public DataTable(int numRows, int numColumns) {
    super(numRows, numColumns);
    initUI();
  }

  public DataTable(Vector<?> rowData, Vector<?> columnNames) {
    super(rowData, columnNames);
    initUI();
  }

  public DataTable(Object[][] rowData, Object[] columnNames) {
    super(rowData, columnNames);
    initUI();
  }

  private void initUI() {
    UIManager.put("Table.background1", new Color(0.92f, 0.99f, 0.95f));
    UIManager.put("Table.background2", Color.white);
  }

  public void adjustColumns() {
    TableColumnModel tcm = getColumnModel();

    for (int i = 0; i < tcm.getColumnCount(); i++) {
      adjustColumn(tcm, i);
    }
  }

  private void adjustColumn(final TableColumnModel tcm, final int column) {
    TableColumn tableColumn = tcm.getColumn(column);

    if (!tableColumn.getResizable()) {
      return;
    }

    int columnHeaderWidth = getColumnHeaderWidth(tableColumn, column);
    int columnDataWidth = getColumnDataWidth(tableColumn, column);
    int preferredWidth = Math.max(columnHeaderWidth, columnDataWidth);

    updateTableColumn(tableColumn, preferredWidth);
  }

  /*
   *  Calculated the width based on the column name
   */
  private int getColumnHeaderWidth(final TableColumn tableColumn, int column) {
    Object value = tableColumn.getHeaderValue();
    TableCellRenderer renderer = tableColumn.getHeaderRenderer();

    if (renderer == null) {
      renderer = getTableHeader().getDefaultRenderer();
    }

    Component c = renderer.getTableCellRendererComponent(this, value, false, false, -1, column);
    return c.getPreferredSize().width;
  }

  /*
   *  Calculate the width based on the widest cell renderer for the
   *  given column.
   */
  private int getColumnDataWidth(final TableColumn tableColumn, int column) {
    int preferredWidth = 0;
    int maxWidth = tableColumn.getMaxWidth();

    int rows = getModel().getRowCount();

    for (int row = 0; row < rows; row++) {
      preferredWidth = Math.max(preferredWidth, getCellDataWidth(row, column));

      //  We've exceeded the maximum width, no need to check other rows
      if (preferredWidth >= maxWidth) {
        break;
      }
    }

    return preferredWidth;
  }

  /*
   *  Get the preferred width for the specified cell
   */
  private int getCellDataWidth(int row, int column) {
    //  Inovke the renderer for the cell to calculate the preferred width

    TableCellRenderer cellRenderer = getCellRenderer(row, column);
    Component c = prepareRenderer(cellRenderer, row, column);
    int width = c.getPreferredSize().width + getIntercellSpacing().width;

    return width;
  }

  /*
   *  Update the TableColumn with the newly calculated width
   */
  private void updateTableColumn(final TableColumn tableColumn, int width) {
    if (!tableColumn.getResizable()) {
      return;
    }

    width += spacing;

    //  Don't shrink the column width
    if (isOnlyAdjustLarger) {
      width = Math.max(width, tableColumn.getPreferredWidth());
    }

    getTableHeader().setResizingColumn(tableColumn);
    tableColumn.setWidth(width);
  }

  @Override
  public Object transformValue(Object value) {
    return super.transformValue(value);
  }

  @Override
  public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    Component c = super.prepareRenderer(renderer, row, column);
    setCellBackground(c, isCellSelected(row, column), row, column);
    return c;
  }
}
