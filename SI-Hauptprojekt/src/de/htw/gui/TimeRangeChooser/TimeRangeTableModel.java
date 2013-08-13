package de.htw.gui.TimeRangeChooser;

import de.htw.logging.LoggerNames;
import de.htw.logging.RootLogger;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 13.08.13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class TimeRangeTableModel extends AbstractTableModel {
    public static final  String[]   COLUMN_NAMES   = {"", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag",
                                                      "Samstag", "Sonntag"};
    private static final long       THIRTY_MINUTES = 30 * 60 * 1000;
    private              Object[][] data           = new Object[48][8];

    public TimeRangeTableModel() {
        super();
        initTableData();
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    public int getRowCount() {
        return data != null ? data.length : 0;
    }

    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    public Object getValueAt(int row, int col) {
        return data == null ? null : data[row][col];
    }

    public Class getColumnClass(int c) {
        if (data == null) {
            return Object.class;
        } else {
            Object object = getValueAt(0, c);
            return object != null ? object.getClass() : Object.class;
        }
    }

    public boolean isCellEditable(int x, int y) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        if (data != null) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

    private void initTableData() {
        data = new Object[48][8];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date;
        try {
            date = sdf.parse("00:00");
        }
        catch (Exception e) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Error creating Table Data", Level.SEVERE, e);
            return;
        }

        for (int i = 0; i < 48; ++i) {
            Object[] temp = new Object[8];
            for (int j = 1; j < 8; ++j) {
                temp[j] = new Boolean(false);
            }
            String timeFrame = sdf.format(date);
            date.setTime(date.getTime() + THIRTY_MINUTES);
            timeFrame += " - " + sdf.format(date);
            temp[0] = timeFrame;
            data[i] = temp;
        }
    }
}
