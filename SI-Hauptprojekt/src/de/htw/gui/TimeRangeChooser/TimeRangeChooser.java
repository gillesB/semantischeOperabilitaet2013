package de.htw.gui.TimeRangeChooser;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 13.08.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class TimeRangeChooser extends JFrame {
    private ITimeRangeChooserListener listener;
    private JTable                    table;

    public TimeRangeChooser(String title) {
        this(title, null);

        table = new JTable(new TimeRangeTableModel());
        TableColumnModel columnModel = table.getColumnModel();
        TableHeaderCellRenderer tableHeaderCellRenderer = new TableHeaderCellRenderer();
        columnModel.getColumn(0).setCellRenderer(tableHeaderCellRenderer);

//        TimeRangeCellRenderer renderer = new TimeRangeCellRenderer();
//        renderer.setCellWidt(50);
//        renderer.setCellHeigth(25);
//        columnModel.getColumn(0).setCellRenderer(renderer);
//        columnModel.getColumn(1).setCellRenderer(renderer);
//        columnModel.getColumn(2).setCellRenderer(renderer);
//        columnModel.getColumn(3).setCellRenderer(renderer);
//        columnModel.getColumn(4).setCellRenderer(renderer);
//        columnModel.getColumn(5).setCellRenderer(renderer);
//        columnModel.getColumn(6).setCellRenderer(renderer);


        //Scrollpane
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (listener != null) {
                    listener.receiveData(((TimeRangeTableModel) table.getModel()).getData());
                }
            }
        });
    }

    public TimeRangeChooser(String title, ITimeRangeChooserListener listener) {
        setTitle(title);
        this.listener = listener;
    }

    public void setListener(ITimeRangeChooserListener listener) {
        this.listener = listener;
    }


}
