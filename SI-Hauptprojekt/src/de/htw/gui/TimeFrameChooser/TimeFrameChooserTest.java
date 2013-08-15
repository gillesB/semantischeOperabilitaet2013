package de.htw.gui.TimeFrameChooser;

import de.htw.logging.LoggerNames;
import de.htw.logging.RootLogger;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;
import java.util.List;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class TimeFrameChooserTest implements ITimeFrameChooserListener {

    public static void main(String[] args) {
        try {
            // Design Layout setzen
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Tablayout setzen
            UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(0, 0, 0, 0));
        }
        catch (Exception e) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Creation Error", Level.SEVERE, e);
        }
        TimeFrameChooserFrame frame = new TimeFrameChooserFrame(new TimeFrameChooserTest());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
        RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("TimeRangeChooserTest", Level.INFO);
    }

    @Override
    public void receiveData(List<TimeFrame> timeFrames) {
        RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Received TimeFrames", Level.INFO);
        for (TimeFrame tf : timeFrames) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log(
                    "Frame: day-> " + tf.getDay().getName() + " start-> " + tf.getStartTime() + " end-> " + tf.getEndTime(),
                    Level.INFO);
        }
    }
}
