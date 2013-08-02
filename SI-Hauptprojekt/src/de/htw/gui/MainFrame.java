package de.htw.gui;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Ziele;
import de.htw.queries.QueryBuilder;
import de.htw.queries.QueryBuilder.ArtVonSport;
import de.htw.queries.QueryBuilder.InnenAussen;

import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFormattedTextField;

import java.text.Format;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JCheckBox;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btngArtVonSport = new ButtonGroup();
	private JList<Sportangebot> lstSportarten;
	private JFormattedTextField txtKosten;
	private JCheckBox chckbxArmeinschraenkung;
	private JCheckBox chckbxBeineinschrnkung;
	private JCheckBox chckbxHhenangst;
	private JButton btnSearch;
	private JCheckBox chckbxIgnorieren;
	private DecimalFormat priceFormat = new DecimalFormat( "####,##" );
	private JRadioButton rdbtnInnen;
	private JRadioButton rdbtnAussen;
	private JRadioButton rdbtnEgal_Innen;
	private final ButtonGroup btngInnenAussen = new ButtonGroup();
	public static final String EINZELSPORT = "Einzelsport";
	public static final String TEAMSPORT = "Teamsport";
	public static final String EGAL = "Egal";
	public static final String ARMEINSCHRAENKUNG = "Armeinschränkung";
	public static final String BEINEINSCHRAENKUNG = "Beineinschränkung";
	public static final String HOEHENANGST = "Höhenangst";
	public static final String INNEN = "INNEN";
	public static final String AUSSEN = "Außen";	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("Hochschulsport");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 550);
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
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_4.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{180, 0};
		gbl_panel.rowHeights = new int[]{23, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Art des Sportes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(2, 2, 5, 2);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{149, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JRadioButton rdbtnEinzelsport = new JRadioButton(EINZELSPORT);
		GridBagConstraints gbc_rdbtnEinzelsport = new GridBagConstraints();
		gbc_rdbtnEinzelsport.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnEinzelsport.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnEinzelsport.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnEinzelsport.gridx = 0;
		gbc_rdbtnEinzelsport.gridy = 0;
		panel_1.add(rdbtnEinzelsport, gbc_rdbtnEinzelsport);
		btngArtVonSport.add(rdbtnEinzelsport);
		
		JRadioButton rdbtnTeamsport = new JRadioButton(TEAMSPORT);
		GridBagConstraints gbc_rdbtnTeamsport = new GridBagConstraints();
		gbc_rdbtnTeamsport.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnTeamsport.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnTeamsport.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnTeamsport.gridx = 0;
		gbc_rdbtnTeamsport.gridy = 1;
		panel_1.add(rdbtnTeamsport, gbc_rdbtnTeamsport);
		btngArtVonSport.add(rdbtnTeamsport);
		
		JRadioButton rdbtnEgal_Art = new JRadioButton(EGAL);
		GridBagConstraints gbc_rdbtnEgal_Art = new GridBagConstraints();
		gbc_rdbtnEgal_Art.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnEgal_Art.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnEgal_Art.gridx = 0;
		gbc_rdbtnEgal_Art.gridy = 2;
		panel_1.add(rdbtnEgal_Art, gbc_rdbtnEgal_Art);
		rdbtnEgal_Art.setSelected(true);
		btngArtVonSport.add(rdbtnEgal_Art);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "max. Kosten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(2, 2, 5, 2);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panel.add(panel_6, gbc_panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{195, 0};
		gbl_panel_6.rowHeights = new int[]{38, 0, 0};
		gbl_panel_6.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		txtKosten = new JFormattedTextField(priceFormat);
		txtKosten.setEnabled(false);
		txtKosten.setValue(0.0);
		GridBagConstraints gbc_txtKosten = new GridBagConstraints();
		gbc_txtKosten.insets = new Insets(0, 0, 5, 0);
		gbc_txtKosten.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKosten.gridx = 0;
		gbc_txtKosten.gridy = 0;
		panel_6.add(txtKosten, gbc_txtKosten);
		
		chckbxIgnorieren = new JCheckBox("ignorieren");
		chckbxIgnorieren.setSelected(true);
		chckbxIgnorieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtKosten.setEnabled(!chckbxIgnorieren.isSelected());
			}
		});
		GridBagConstraints gbc_chckbxIgnorieren = new GridBagConstraints();
		gbc_chckbxIgnorieren.anchor = GridBagConstraints.WEST;
		gbc_chckbxIgnorieren.gridx = 0;
		gbc_chckbxIgnorieren.gridy = 1;
		panel_6.add(chckbxIgnorieren, gbc_chckbxIgnorieren);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "geeignet bei", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		panel.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{195, 0};
		gbl_panel_7.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_7.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		chckbxArmeinschraenkung = new JCheckBox(ARMEINSCHRAENKUNG);
		GridBagConstraints gbc_chckbxArmeinschraenkung = new GridBagConstraints();
		gbc_chckbxArmeinschraenkung.anchor = GridBagConstraints.WEST;
		gbc_chckbxArmeinschraenkung.gridx = 0;
		gbc_chckbxArmeinschraenkung.gridy = 0;
		panel_7.add(chckbxArmeinschraenkung, gbc_chckbxArmeinschraenkung);
		
		chckbxBeineinschrnkung = new JCheckBox(BEINEINSCHRAENKUNG);
		GridBagConstraints gbc_chckbxBeineinschrnkung = new GridBagConstraints();
		gbc_chckbxBeineinschrnkung.insets = new Insets(5, 0, 0, 0);
		gbc_chckbxBeineinschrnkung.anchor = GridBagConstraints.WEST;
		gbc_chckbxBeineinschrnkung.gridx = 0;
		gbc_chckbxBeineinschrnkung.gridy = 1;
		panel_7.add(chckbxBeineinschrnkung, gbc_chckbxBeineinschrnkung);
		
		chckbxHhenangst = new JCheckBox(HOEHENANGST);
		GridBagConstraints gbc_chckbxHhenangst = new GridBagConstraints();
		gbc_chckbxHhenangst.insets = new Insets(5, 0, 0, 0);
		gbc_chckbxHhenangst.anchor = GridBagConstraints.WEST;
		gbc_chckbxHhenangst.gridx = 0;
		gbc_chckbxHhenangst.gridy = 2;
		panel_7.add(chckbxHhenangst, gbc_chckbxHhenangst);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Innen/Au\u00DFen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 3;
		panel.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[]{149, 0};
		gbl_panel_8.rowHeights = new int[]{23, 0, 0, 0};
		gbl_panel_8.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_8.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_8.setLayout(gbl_panel_8);
		
		rdbtnInnen = new JRadioButton(INNEN);
		btngInnenAussen.add(rdbtnInnen);
		GridBagConstraints gbc_rdbtnInnen = new GridBagConstraints();
		gbc_rdbtnInnen.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnInnen.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnInnen.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnInnen.gridx = 0;
		gbc_rdbtnInnen.gridy = 0;
		panel_8.add(rdbtnInnen, gbc_rdbtnInnen);
		
		rdbtnAussen = new JRadioButton(AUSSEN);
		btngInnenAussen.add(rdbtnAussen);
		GridBagConstraints gbc_rdbtnAussen = new GridBagConstraints();
		gbc_rdbtnAussen.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnAussen.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnAussen.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnAussen.gridx = 0;
		gbc_rdbtnAussen.gridy = 1;
		panel_8.add(rdbtnAussen, gbc_rdbtnAussen);
		
		rdbtnEgal_Innen = new JRadioButton(EGAL);
		btngInnenAussen.add(rdbtnEgal_Innen);
		rdbtnEgal_Innen.setSelected(true);
		GridBagConstraints gbc_rdbtnEgal_Innen = new GridBagConstraints();
		gbc_rdbtnEgal_Innen.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnEgal_Innen.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnEgal_Innen.gridx = 0;
		gbc_rdbtnEgal_Innen.gridy = 2;
		panel_8.add(rdbtnEgal_Innen, gbc_rdbtnEgal_Innen);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(2, 2, 2, 2);
		gbc_panel_5.anchor = GridBagConstraints.NORTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{159, 0};
		gbl_panel_5.rowHeights = new int[]{25, 0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		btnSearch = new JButton("Suche Sportarten");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				new ExecuteQuery().execute();			
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 0;
		panel_5.add(btnSearch, gbc_btnSearch);
		
		JButton btnZeit = new JButton("Zur Zeitangabe");
		btnZeit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//@Tobias: hier Code einfügen für die Zeitangabe
			}
		});
		GridBagConstraints gbc_btnZeit = new GridBagConstraints();
		gbc_btnZeit.fill = GridBagConstraints.BOTH;
		gbc_btnZeit.gridx = 0;
		gbc_btnZeit.gridy = 1;
		panel_5.add(btnZeit, gbc_btnZeit);
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
    
    /**
     * Die Klasse ExecuteQuery setzt die richtigen Attribute in der Klasse QueryBuilder und führt diesen dann aus.
     * Die Resultate die ExecuteQuery vom QueryBuilder erhält, werden in der Liste angezeigt.
     * @author Gilles
     *
     */
    private class ExecuteQuery extends SwingWorker<Void, Void>{

    	private DefaultListModel<Sportangebot> model = new DefaultListModel<Sportangebot>();
    	private QueryBuilder builder;
		@Override
		protected Void doInBackground() throws Exception {
			builder = new QueryBuilder();
			
			configArtVonSport();
			configPrice();
			configKoerperlicheEinschraenkung();
			configInnenAussen();		
					 	
			Map<String, Sportangebot> result = builder.execute();
			for(Sportangebot sport : result.values()){
				model.addElement(sport);
			}

			return null;	
		}

		@Override
		protected void done() {			
			btnSearch.setCursor(Cursor.getDefaultCursor());			
			lstSportarten.setModel(model);
		}
		
		private void configArtVonSport(){
			String selectedArtVonSportButton = getSelectedButtonText(btngArtVonSport);
			ArtVonSport selectedArtVonSport = ArtVonSport.EGAL;
			if(selectedArtVonSportButton.equals(EINZELSPORT)){
				selectedArtVonSport = ArtVonSport.EINZELSPORT;
			} else if(selectedArtVonSportButton.equals(TEAMSPORT)){
				selectedArtVonSport = ArtVonSport.TEAMSPORT;
			}				
			builder.setSelectedArtVonSport(selectedArtVonSport);
		}
		
		private void configPrice() {
		 	if(txtKosten.isEditValid()){
		 		Number maxPrice = ( Number ) txtKosten.getValue();
		 		builder.setMaximalPrice(maxPrice.doubleValue(), chckbxIgnorieren.isSelected());
		 	}
		}
		
		private void configKoerperlicheEinschraenkung() {
		 	ArrayList<KoerperlicheEinschraenkungen> einschraenkungen = new ArrayList<KoerperlicheEinschraenkungen>();
			if(chckbxArmeinschraenkung.isSelected()){
				einschraenkungen.add(KoerperlicheEinschraenkungen.ARMBEREICH);
			}
			if(chckbxBeineinschrnkung.isSelected()){
				einschraenkungen.add(KoerperlicheEinschraenkungen.BEINBEREICH);
			}
			if(chckbxHhenangst.isSelected()){
				einschraenkungen.add(KoerperlicheEinschraenkungen.HOEHENANGST);
			}
			builder.setEinschraenkungen(einschraenkungen.toArray(new KoerperlicheEinschraenkungen[0]));
		}
		
		private void configInnenAussen() {
			String selectedInnenAussenButton = getSelectedButtonText(btngInnenAussen);
			InnenAussen selectedInnenAussen = InnenAussen.EGAL;
			if(selectedInnenAussenButton.equals(INNEN)){
				selectedInnenAussen = InnenAussen.INNEN;
			} else if(selectedInnenAussenButton.equals(AUSSEN)){
				selectedInnenAussen = InnenAussen.AUSSEN;
			}				
			builder.setSelectedInnenAussen(selectedInnenAussen);
		}
    }
}
