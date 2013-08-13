package de.htw.gui.TimeRangeChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 13.08.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class TableHeaderCellRenderer extends DefaultTableCellRenderer {

    public TableHeaderCellRenderer() {
        super();
        setBackground(UIManager.getColor("TableHeader.background"));
    }
}
