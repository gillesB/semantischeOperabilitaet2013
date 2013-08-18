package de.htw.gui.TimeFrameChooser;

import de.htw.logging.LoggerNames;
import de.htw.logging.RootLogger;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class TimeFrameChooser extends JPanel {
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private List<TimeFramePanel>      timeFramePanels;
    private List<JLabel>              timeLabels;
    private ITimeFrameChooserListener listener;
    private JPanel                    calendarBaseLayer;

    public TimeFrameChooser() {
        this(null);
    }

    public TimeFrameChooser(ITimeFrameChooserListener listener) {
        this.listener = listener;
        timeFramePanels = new ArrayList<TimeFramePanel>();
        timeLabels = new ArrayList<JLabel>();
        initCalendarDisplay();

        //place Components
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(2, 5, 2, 5);
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 999;
        constraints.weighty = 999;
        add(calendarBaseLayer, constraints);
    }

    private void initCalendarDisplay() {
        calendarBaseLayer = new JPanel();
        calendarBaseLayer.setLayout(new GridBagLayout());
        calendarBaseLayer.setBackground(new Color(205, 205, 205));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.fill = GridBagConstraints.NONE;

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 999;
        constraints.weighty = 1;

        //add labels for weekdays
        for (int i = 0; i < 7; ++i) {
            JLabel temp = new JLabel(String.valueOf(Day.getByID(i + 1)));
            calendarBaseLayer.add(temp, constraints);
            constraints.gridx += 2;
        }

        //add time frames
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weighty = 999;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTH;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date;
        try {
            date = sdf.parse("07:00");
        }
        catch (Exception e) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Error creating Table Data", Level.SEVERE, e);
            return;
        }
        for (int i = 0; i < 16; ++i) {
            String timeFrame = sdf.format(date);
            date.setTime(date.getTime() + ONE_HOUR);
            timeFrame += " - " + sdf.format(date);
            JLabel temp = new JLabel(timeFrame);
            timeLabels.add(temp);
            calendarBaseLayer.add(temp, constraints);
            constraints.gridy += 2;
        }

        //create calendar like look
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.weighty = 999;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        try {
            for (int i = 0; i < 7; ++i) {
                constraints.gridy = 3;
                for (int j = 0; j < 16; ++j) {
                    String[] tArray = timeLabels.get(j).getText().split("-");
                    TimeFramePanel temp = new TimeFramePanel(sdf.parse(tArray[0]), sdf.parse(tArray[1]),
                                                             Day.getByID(i + 1));
                    timeFramePanels.add(temp);
                    calendarBaseLayer.add(temp, constraints);
                    constraints.gridy += 2;
                }
                constraints.gridx += 2;
            }
        }
        catch (ParseException e) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Error Parsing Times!", Level.SEVERE, e);
            System.exit(-1);
        }

    }

    public void setListener(ITimeFrameChooserListener listener) {
        this.listener = listener;
    }

    private List<TimeFrame> getTimeFramePanels() {
        //gather time frames
        List<TimeFrame> frames = new ArrayList<TimeFrame>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (TimeFramePanel tfp : timeFramePanels) {
            if (tfp.getSelected()) {
                frames.add(new TimeFrame(tfp.getStartTime(), tfp.getEndTime(), tfp.getDay()));
            }
        }
        return frames;
    }

    public void tellListener() {
        if (listener != null) {
            listener.receiveData(getTimeFramePanels());
        }
    }

    public void load(List<TimeFrame> timeFrames) {
        int panelPos = 0;
        for (TimeFrame tf : timeFrames) {
            while (panelPos < timeFramePanels.size()) {
                TimeFramePanel tempPanel = timeFramePanels.get(panelPos);
                if (tempPanel.getDay() == tf.getDay() && tempPanel.getStartTime().equals(tf.getStartTime()) && tempPanel
                        .getEndTime().equals(tf.getEndTime())) {
                    tempPanel.select();
                    break;
                } else {
                    ++panelPos;
                }
            }
        }
    }
}
