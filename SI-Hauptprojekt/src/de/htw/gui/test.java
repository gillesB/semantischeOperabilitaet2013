package de.htw.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import business.model.Sportangebot;
import de.htw.queries.QueryBuilder;
import de.htw.queries.QueryBuilder.ArtVonSport;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

public class test extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btngArtVonSport = new ButtonGroup();
	private JList<Sportangebot> lstSportarten;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setLeftComponent(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		lstSportarten = new JList();
		scrollPane.setViewportView(lstSportarten);
		lstSportarten.setModel(new AbstractListModel() {
			String[] values = new String[] {"foo", "foo", "foo", "foo", "foo", "foo", "foo"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JPanel panel_3 = new JPanel();
		splitPane_1.setRightComponent(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextPane textPane = new JTextPane();
		panel_3.add(textPane);
		
		JPanel panel_4 = new JPanel();
		splitPane.setLeftComponent(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{220, 98, 0};
		gbl_panel_4.rowHeights = new int[]{398, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_4.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{149, 0};
		gbl_panel.rowHeights = new int[]{23, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JRadioButton rdbtnEinzelsport = new JRadioButton("Einzelsport");
		btngArtVonSport.add(rdbtnEinzelsport);
		GridBagConstraints gbc_rdbtnEinzelsport = new GridBagConstraints();
		gbc_rdbtnEinzelsport.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnEinzelsport.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnEinzelsport.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnEinzelsport.gridx = 0;
		gbc_rdbtnEinzelsport.gridy = 0;
		panel.add(rdbtnEinzelsport, gbc_rdbtnEinzelsport);
		
		JRadioButton rdbtnTeamsport = new JRadioButton("Teamsport");
		btngArtVonSport.add(rdbtnTeamsport);
		GridBagConstraints gbc_rdbtnTeamsport = new GridBagConstraints();
		gbc_rdbtnTeamsport.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnTeamsport.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnTeamsport.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnTeamsport.gridx = 0;
		gbc_rdbtnTeamsport.gridy = 1;
		panel.add(rdbtnTeamsport, gbc_rdbtnTeamsport);
		
		JRadioButton rdbtnEgal = new JRadioButton("Egal");
		rdbtnEgal.setSelected(true);
		btngArtVonSport.add(rdbtnEgal);
		GridBagConstraints gbc_rdbtnEgal = new GridBagConstraints();
		gbc_rdbtnEgal.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnEgal.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnEgal.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnEgal.gridx = 0;
		gbc_rdbtnEgal.gridy = 2;
		panel.add(rdbtnEgal, gbc_rdbtnEgal);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.NORTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);
		
		JButton btnSearch = new JButton("Suche Sportarten");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryBuilder builder = new QueryBuilder();
				
				String selectedArtVonSportButton = getSelectedButtonText(btngArtVonSport);
				ArtVonSport selectedArtVonSport = ArtVonSport.EGAL;
				if(selectedArtVonSportButton.equals("Einzelsport")){
					selectedArtVonSport = ArtVonSport.EINZELSPORT;
				} else if(selectedArtVonSportButton.equals("Teamsport")){
					selectedArtVonSport = ArtVonSport.TEAMSPORT;
				}				
				builder.setSelectedArtVonSport(selectedArtVonSport);
				
				Map<String, Sportangebot> result = builder.execute();
				DefaultListModel<Sportangebot> model = new DefaultListModel<Sportangebot>();
				for(Sportangebot sport : result.values()){
					model.addElement(sport);
				}
				lstSportarten.setModel(model);					
			}
		});
		panel_5.add(btnSearch);
	}
	
    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
