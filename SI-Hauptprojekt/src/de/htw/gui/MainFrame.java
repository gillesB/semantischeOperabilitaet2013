package de.htw.gui;

import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Ziele;
import de.htw.gui.TimeFrameChooser.ITimeFrameChooserListener;
import de.htw.gui.TimeFrameChooser.TimeFrame;
import de.htw.gui.TimeFrameChooser.TimeFrameChooserFrame;
import de.htw.logging.LoggerNames;
import de.htw.logging.RootLogger;
import de.htw.queries.Queries;
import de.htw.queries.QueryBuilder;
import de.htw.queries.QueryBuilder.ArtVonSport;
import de.htw.queries.QueryBuilder.InnenAussen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class MainFrame extends JFrame implements ITimeFrameChooserListener {

    // String Konstanten in der GUI, sollten nur die sein, die später für ein
    // Vergleich benutzt werden
    public static final String      EINZELSPORT        = "Einzelsport";
    public static final String      TEAMSPORT          = "Teamsport";
    public static final String      EGAL               = "Egal";
    public static final String      ARMEINSCHRAENKUNG  = "Armeinschränkung";
    public static final String      BEINEINSCHRAENKUNG = "Beineinschränkung";
    public static final String      HOEHENANGST        = "Höhenangst";
    public static final String      INNEN              = "Innen";
    public static final String      AUSSEN             = "Außen";
    private final       ButtonGroup btngArtVonSport    = new ButtonGroup();
    private final       ButtonGroup btngInnenAussen    = new ButtonGroup();
    private JPanel              contentPane;
    private JList<Sportangebot> lstSportarten;
    private JFormattedTextField txtKosten;
    private JCheckBox           chckbxArmeinschraenkung;
    private JCheckBox           chckbxBeineinschrnkung;
    private JCheckBox           chckbxHhenangst;
    private JButton             btnSearch;
    private JCheckBox           chckbxIgnorieren;
    private DecimalFormat priceFormat = new DecimalFormat("####,##");
    private JRadioButton    rdbtnInnen;
    private JRadioButton    rdbtnAussen;
    private JRadioButton    rdbtnEgal_Innen;
    private JCheckBox       chckbxFitness;
    private JCheckBox       chckbxSelfDefense;
    private JCheckBox       chckbxFreizeitvergnuegen;
    private JCheckBox       chckbxWettbewerb;
    private JCheckBox       chckbxSozialkontakte;
    private JComboBox       cboExotisch;
    private JComboBox       cboKampfsport;
    private JComboBox       cboKoerperkontakt;
    private JComboBox       cboWassersport;
    private List<TimeFrame> timeFrames;
    private JTextPane txtDetail;

    /**
     * Create the frame.
     */
    public MainFrame() {
        timeFrames = new ArrayList<TimeFrame>();
        setTitle("Hochschulsport");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 614, 970);
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
            String[] values = new String[]{"foo", "foo", "foo", "foo", "foo",
                                           "foo", "foo"};

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }
        });
        
        lstSportarten.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	if (!lstSportarten.getValueIsAdjusting()) {
            		Sportangebot sport_angebot = (Sportangebot) lstSportarten.getSelectedValue();
            		String sport_with_details = Queries.getDetailString(sport_angebot);
            		txtDetail.setText(sport_with_details);
            	}
            }
        });

        JPanel panel_3 = new JPanel();
        splitPane_1.setRightComponent(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 0));

        txtDetail = new JTextPane();
        panel_3.add(txtDetail);

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
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
                                        null));
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{180, 0};
        gbl_panel.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                            Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Art des Sportes",
                                           TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        panel_6.setBorder(new TitledBorder(null, "max. Kosten",
                                           TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        panel_7.setBorder(new TitledBorder(new LineBorder(new Color(184, 207,
                                                                    229)), "geeignet bei", TitledBorder.LEADING,
                                           TitledBorder.TOP,
                                           null, null));
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
        panel_8.setBorder(new TitledBorder(new LineBorder(new Color(184, 207,
                                                                    229)), "Innen/Au\u00DFen", TitledBorder.LEADING,
                                           TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_8 = new GridBagConstraints();
        gbc_panel_8.insets = new Insets(0, 0, 5, 0);
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

        JPanel panel_9 = new JPanel();
        panel_9.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Ziele", TitledBorder.LEADING,
                                           TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_9 = new GridBagConstraints();
        gbc_panel_9.insets = new Insets(0, 0, 5, 0);
        gbc_panel_9.fill = GridBagConstraints.BOTH;
        gbc_panel_9.gridx = 0;
        gbc_panel_9.gridy = 4;
        panel.add(panel_9, gbc_panel_9);
        GridBagLayout gbl_panel_9 = new GridBagLayout();
        gbl_panel_9.columnWidths = new int[]{195, 0};
        gbl_panel_9.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel_9.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_9.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                                              Double.MIN_VALUE};
        panel_9.setLayout(gbl_panel_9);

        chckbxFitness = new JCheckBox("Fitness");
        GridBagConstraints gbc_chckbxFitness = new GridBagConstraints();
        gbc_chckbxFitness.anchor = GridBagConstraints.WEST;
        gbc_chckbxFitness.insets = new Insets(0, 0, 5, 0);
        gbc_chckbxFitness.gridx = 0;
        gbc_chckbxFitness.gridy = 0;
        panel_9.add(chckbxFitness, gbc_chckbxFitness);

        chckbxSelfDefense = new JCheckBox("Self Defense");
        GridBagConstraints gbc_chckbxSelfDefense = new GridBagConstraints();
        gbc_chckbxSelfDefense.anchor = GridBagConstraints.WEST;
        gbc_chckbxSelfDefense.insets = new Insets(5, 0, 5, 0);
        gbc_chckbxSelfDefense.gridx = 0;
        gbc_chckbxSelfDefense.gridy = 1;
        panel_9.add(chckbxSelfDefense, gbc_chckbxSelfDefense);

        chckbxFreizeitvergnuegen = new JCheckBox("Freizeitvergnügen");
        GridBagConstraints gbc_chckbxFreizeitvergnuegen = new GridBagConstraints();
        gbc_chckbxFreizeitvergnuegen.insets = new Insets(5, 0, 5, 0);
        gbc_chckbxFreizeitvergnuegen.anchor = GridBagConstraints.WEST;
        gbc_chckbxFreizeitvergnuegen.gridx = 0;
        gbc_chckbxFreizeitvergnuegen.gridy = 2;
        panel_9.add(chckbxFreizeitvergnuegen, gbc_chckbxFreizeitvergnuegen);

        chckbxWettbewerb = new JCheckBox("Wettbewerb");
        GridBagConstraints gbc_chckbxWettbewerb = new GridBagConstraints();
        gbc_chckbxWettbewerb.anchor = GridBagConstraints.WEST;
        gbc_chckbxWettbewerb.insets = new Insets(0, 0, 5, 0);
        gbc_chckbxWettbewerb.gridx = 0;
        gbc_chckbxWettbewerb.gridy = 3;
        panel_9.add(chckbxWettbewerb, gbc_chckbxWettbewerb);

        chckbxSozialkontakte = new JCheckBox("soziale Kontakte");
        GridBagConstraints gbc_chckbxSozialkontakte = new GridBagConstraints();
        gbc_chckbxSozialkontakte.anchor = GridBagConstraints.WEST;
        gbc_chckbxSozialkontakte.gridx = 0;
        gbc_chckbxSozialkontakte.gridy = 4;
        panel_9.add(chckbxSozialkontakte, gbc_chckbxSozialkontakte);

        JPanel panel_10 = new JPanel();
        panel_10.setBorder(new TitledBorder(null, "Eigenschaften", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_10 = new GridBagConstraints();
        gbc_panel_10.fill = GridBagConstraints.BOTH;
        gbc_panel_10.gridx = 0;
        gbc_panel_10.gridy = 5;
        panel.add(panel_10, gbc_panel_10);
        GridBagLayout gbl_panel_10 = new GridBagLayout();
        gbl_panel_10.columnWidths = new int[]{75, 59, 0};
        gbl_panel_10.rowHeights = new int[]{15, 0, 0, 0, 0};
        gbl_panel_10.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_10.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_10.setLayout(gbl_panel_10);

        JLabel lblExotisch = new JLabel("exotisch");
        GridBagConstraints gbc_lblExotisch = new GridBagConstraints();
        gbc_lblExotisch.insets = new Insets(0, 0, 5, 5);
        gbc_lblExotisch.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblExotisch.gridx = 0;
        gbc_lblExotisch.gridy = 0;
        panel_10.add(lblExotisch, gbc_lblExotisch);

        cboExotisch = new JComboBox();
        cboExotisch.setModel(new DefaultComboBoxModel(Choices.values()));
        cboExotisch.setSelectedIndex(2);
        GridBagConstraints gbc_cboExotisch = new GridBagConstraints();
        gbc_cboExotisch.insets = new Insets(0, 0, 5, 0);
        gbc_cboExotisch.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboExotisch.gridx = 1;
        gbc_cboExotisch.gridy = 0;
        panel_10.add(cboExotisch, gbc_cboExotisch);

        JLabel lblKampfsport = new JLabel("Kampfsport");
        GridBagConstraints gbc_lblKampfsport = new GridBagConstraints();
        gbc_lblKampfsport.anchor = GridBagConstraints.EAST;
        gbc_lblKampfsport.insets = new Insets(0, 0, 5, 5);
        gbc_lblKampfsport.gridx = 0;
        gbc_lblKampfsport.gridy = 1;
        panel_10.add(lblKampfsport, gbc_lblKampfsport);

        cboKampfsport = new JComboBox();
        cboKampfsport.setModel(new DefaultComboBoxModel(Choices.values()));
        cboKampfsport.setSelectedIndex(2);
        GridBagConstraints gbc_cboKampfsport = new GridBagConstraints();
        gbc_cboKampfsport.insets = new Insets(0, 0, 5, 0);
        gbc_cboKampfsport.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboKampfsport.gridx = 1;
        gbc_cboKampfsport.gridy = 1;
        panel_10.add(cboKampfsport, gbc_cboKampfsport);

        JLabel lblKrperkontakt = new JLabel("Körperkontakt");
        GridBagConstraints gbc_lblKrperkontakt = new GridBagConstraints();
        gbc_lblKrperkontakt.insets = new Insets(0, 0, 5, 5);
        gbc_lblKrperkontakt.anchor = GridBagConstraints.EAST;
        gbc_lblKrperkontakt.gridx = 0;
        gbc_lblKrperkontakt.gridy = 2;
        panel_10.add(lblKrperkontakt, gbc_lblKrperkontakt);

        cboKoerperkontakt = new JComboBox();
        cboKoerperkontakt.setModel(new DefaultComboBoxModel(Choices.values()));
        cboKoerperkontakt.setSelectedIndex(2);
        GridBagConstraints gbc_cboKoerperkontakt = new GridBagConstraints();
        gbc_cboKoerperkontakt.insets = new Insets(0, 0, 5, 0);
        gbc_cboKoerperkontakt.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboKoerperkontakt.gridx = 1;
        gbc_cboKoerperkontakt.gridy = 2;
        panel_10.add(cboKoerperkontakt, gbc_cboKoerperkontakt);

        JLabel lblWassersport = new JLabel("Wassersport");
        GridBagConstraints gbc_lblWassersport = new GridBagConstraints();
        gbc_lblWassersport.insets = new Insets(0, 0, 0, 5);
        gbc_lblWassersport.anchor = GridBagConstraints.EAST;
        gbc_lblWassersport.gridx = 0;
        gbc_lblWassersport.gridy = 3;
        panel_10.add(lblWassersport, gbc_lblWassersport);

        cboWassersport = new JComboBox();
        cboWassersport.setModel(new DefaultComboBoxModel(Choices.values()));
        cboWassersport.setSelectedIndex(2);
        GridBagConstraints gbc_cboWassersport = new GridBagConstraints();
        gbc_cboWassersport.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboWassersport.gridx = 1;
        gbc_cboWassersport.gridy = 3;
        panel_10.add(cboWassersport, gbc_cboWassersport);

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
        btnSearch.setMnemonic('S');
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearch.setCursor(Cursor
                                            .getPredefinedCursor(Cursor.WAIT_CURSOR));
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
        btnZeit.setMnemonic('Z');
        btnZeit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimeFrameChooserFrame timeFrameChooserFrame = new TimeFrameChooserFrame(MainFrame.this,
                                                                                        Dialog.ModalityType.APPLICATION_MODAL ,
                                                                                        MainFrame.this);
                timeFrameChooserFrame.setSize(600, 400);
                timeFrameChooserFrame.pack();
                timeFrameChooserFrame.setVisible(true);
            }
        });
        GridBagConstraints gbc_btnZeit = new GridBagConstraints();
        gbc_btnZeit.fill = GridBagConstraints.BOTH;
        gbc_btnZeit.gridx = 0;
        gbc_btnZeit.gridy = 1;
        panel_5.add(btnZeit, gbc_btnZeit);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
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
                try {
                    MainFrame frame = new MainFrame();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons
                .hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    @Override
    public void receiveData(List<TimeFrame> timeFrames) {
        RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log("Received TimeFrames", Level.INFO);
        this.timeFrames = timeFrames;
        for (TimeFrame tf : timeFrames) {
            RootLogger.getInstance(LoggerNames.MAIN_LOGGER).log(
                    "Frame: day-> " + tf.getDay().getName() + " start-> " + tf.getStartTime() + " end-> " + tf.getEndTime(),
                    Level.INFO);
        }
    }

    /**
     * Die Klasse ExecuteQuery setzt die richtigen Attribute in der Klasse
     * QueryBuilder und führt diesen dann aus. Die Resultate die ExecuteQuery
     * vom QueryBuilder erhält, werden in der Liste angezeigt.
     *
     * @author Gilles
     */
    private class ExecuteQuery extends SwingWorker<Void, Void> {

        private DefaultListModel<Sportangebot> model = new DefaultListModel<Sportangebot>();
        private QueryBuilder builder;

        @Override
        protected Void doInBackground() throws Exception {
            builder = new QueryBuilder();

            configArtVonSport();
            configPrice();
            configKoerperlicheEinschraenkung();
            configInnenAussen();
            configZiele();
            config4Attributes();
            configTimeFrames();

            Map<String, Sportangebot> result = builder.execute();
            for (Sportangebot sport : result.values()) {
                model.addElement(sport);
            }

            return null;
        }

        private void configTimeFrames() {
            builder.setSelectedTimeFames(timeFrames);
        }

        @Override
        protected void done() {
            btnSearch.setCursor(Cursor.getDefaultCursor());
            lstSportarten.setModel(model);
        }

        private void configArtVonSport() {
            String selectedArtVonSportButton = getSelectedButtonText(btngArtVonSport);
            ArtVonSport selectedArtVonSport = ArtVonSport.EGAL;
            if (selectedArtVonSportButton.equals(EINZELSPORT)) {
                selectedArtVonSport = ArtVonSport.EINZELSPORT;
            } else if (selectedArtVonSportButton.equals(TEAMSPORT)) {
                selectedArtVonSport = ArtVonSport.TEAMSPORT;
            }
            builder.setSelectedArtVonSport(selectedArtVonSport);
        }

        private void configPrice() {
            if (txtKosten.isEditValid()) {
                Number maxPrice = (Number) txtKosten.getValue();
                builder.setMaximalPrice(maxPrice.doubleValue(),
                                        chckbxIgnorieren.isSelected());
            }
        }

        private void configKoerperlicheEinschraenkung() {
            ArrayList<KoerperlicheEinschraenkungen> einschraenkungen = new ArrayList<KoerperlicheEinschraenkungen>();
            if (chckbxArmeinschraenkung.isSelected()) {
                einschraenkungen.add(KoerperlicheEinschraenkungen.ARMBEREICH);
            }
            if (chckbxBeineinschrnkung.isSelected()) {
                einschraenkungen.add(KoerperlicheEinschraenkungen.BEINBEREICH);
            }
            if (chckbxHhenangst.isSelected()) {
                einschraenkungen.add(KoerperlicheEinschraenkungen.HOEHENANGST);
            }
            builder.setEinschraenkungen(einschraenkungen
                                                .toArray(new KoerperlicheEinschraenkungen[0]));
        }

        private void configInnenAussen() {
            String selectedInnenAussenButton = getSelectedButtonText(btngInnenAussen);
            InnenAussen selectedInnenAussen = InnenAussen.EGAL;
            if (selectedInnenAussenButton.equals(INNEN)) {
                selectedInnenAussen = InnenAussen.INNEN;
            } else if (selectedInnenAussenButton.equals(AUSSEN)) {
                selectedInnenAussen = InnenAussen.AUSSEN;
            }
            builder.setSelectedInnenAussen(selectedInnenAussen);
        }

        private void configZiele() {
            ArrayList<Ziele> ziele = new ArrayList<Ziele>();
            if (chckbxFitness.isSelected()) {
                ziele.add(Ziele.FITNESS);
            }
            if (chckbxFreizeitvergnuegen.isSelected()) {
                ziele.add(Ziele.FREIZEITVERGUEGEN);
            }
            if (chckbxSozialkontakte.isSelected()) {
                ziele.add(Ziele.SOZIALKONTAKTE);
            }
            if (chckbxSelfDefense.isSelected()) {
                ziele.add(Ziele.SELF_DEFENSE);
            }
            if (chckbxWettbewerb.isSelected()) {
                ziele.add(Ziele.WETTBEWERB);
            }

            builder.setZiele(ziele.toArray(new Ziele[0]));
        }

        private void config4Attributes() {
            builder.setExotisch((Choices) cboExotisch.getSelectedItem());
            builder.setKampfsport((Choices) cboKampfsport.getSelectedItem());
            builder.setKoerperkontakt((Choices) cboKoerperkontakt.getSelectedItem());
            builder.setWassersport((Choices) cboWassersport.getSelectedItem());

        }
    }
}
