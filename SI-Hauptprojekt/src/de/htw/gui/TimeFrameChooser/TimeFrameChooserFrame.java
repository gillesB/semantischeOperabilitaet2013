package de.htw.gui.TimeFrameChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public class TimeFrameChooserFrame extends JDialog {
    private TimeFrameChooser timeFrameChooser;

    private TimeFrameChooserFrame(Frame owner, ModalityType modal) {
        super(owner, modal);
    }

    public TimeFrameChooserFrame(Frame owner, ModalityType modal, ITimeFrameChooserListener listener) {
        this(owner, modal);
        //default size
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        timeFrameChooser = new TimeFrameChooser(listener);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 999;
        constraints.weighty = 999;

        add(timeFrameChooser, constraints);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timeFrameChooser.tellListener();
            }
        });
    }
}
