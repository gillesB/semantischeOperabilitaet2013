package de.htw.gui.TimeFrameChooser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 13.08.13
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public interface ITimeFrameChooserListener {
    public void receiveData(List<TimeFrame> timeFrames);
}
