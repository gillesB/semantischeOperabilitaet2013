package de.htw.gui.TimeFrameChooser;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class TimeFrame {
    private Date startTime;
    private Date endTime;
    private Day  day;

    public TimeFrame(Date startTime, Date endTime, Day day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public Day getDay() {
        return day;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
