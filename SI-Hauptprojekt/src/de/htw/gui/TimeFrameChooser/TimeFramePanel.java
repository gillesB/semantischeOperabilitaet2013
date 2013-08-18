package de.htw.gui.TimeFrameChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class TimeFramePanel extends JPanel {
    private static final Color UNSELECTED = new Color(242, 242, 242);
    private static final Color SELECTED   = new Color(46, 149, 22);
    private Boolean selected;
    private Date    startTime;
    private Date    endTime;
    private Day     day;

    public TimeFramePanel(Date startTime, Date endTime, Day day) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        selected = false;
        setBackground(chooseBGColor());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selected = !selected;
                setBackground(chooseBGColor());
            }
        });
    }

    private Color chooseBGColor() {
        return selected ? SELECTED : UNSELECTED;
    }

    public Boolean getSelected() {
        return selected;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Day getDay() {
        return day;
    }

    public void select() {
        selected = true;
        setBackground(chooseBGColor());
    }

    public void deselect() {
        selected = false;
        setBackground(chooseBGColor());
    }
}
