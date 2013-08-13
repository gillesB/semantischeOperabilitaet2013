package de.htw.gui.TimeRangeChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 13.08.13
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class TimeRangeCellRenderer extends DefaultTreeCellRenderer implements TableCellRenderer {
    private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();
    private int       cellWidth;
    private int       cellHeigth;
    private int       columns;
    private int       rows;
    private JCheckBox checkBox;

    public TimeRangeCellRenderer() {
        checkBox = new JCheckBox();
        cellWidth = 100;
        cellHeigth = 75;
        checkBox.setSelected(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // set the colours, etc. using the standard for that platform
        adaptee.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        checkBox.setForeground(adaptee.getForeground());
        checkBox.setBackground(adaptee.getBackground());
        checkBox.setBorder(adaptee.getBorder());
        checkBox.setFont(adaptee.getFont());
        checkBox.setSelected((Boolean) value);
        checkBox.setToolTipText(adaptee.getText());

        // set size
        int width;
        int height;
        // if cellWidth is set
        if (cellWidth > 0) {
            width = cellWidth;
        } else {
            setColumns(columns);
            width = getWidth();
        }
        // if cellHeight is set
        if (cellHeigth > 0) {
            height = cellHeigth;
        } else {
            setRows(rows);
            height = getHeight();
        }
        Dimension size = new Dimension(width, height);
        checkBox.setSize(size);
        checkBox.setPreferredSize(size);
        checkBox.setMinimumSize(size);
        if (table.getRowHeight(row) < height) {
            table.setRowHeight(row, height);
        }
        TableColumnModel columnModel = table.getColumnModel();
        if (columnModel.getColumn(column).getWidth() < width) {
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        return checkBox;
    }

    /**
     * @return the cellWidt
     */
    public int getCellWidt() {
        return cellWidth;
    }

    /**
     * @param cellWidt the cellWidt to set
     */
    public void setCellWidt(int cellWidt) {
        this.cellWidth = cellWidt;
        this.columns = 0;
    }

    /**
     * @return the cellHeigth
     */
    public int getCellHeigth() {
        return cellHeigth;
    }

    /**
     * @param cellHeigth the cellHeigth to set
     */
    public void setCellHeigth(int cellHeigth) {
        this.cellHeigth = cellHeigth;
        this.rows = 0;
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(int columns) {
        this.columns = columns;
        this.cellWidth = 0;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.cellHeigth = 0;
    }
}
