package de.htw.queries;

import java.util.Map;

import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Ziele;
import de.htw.gui.Choices;

/**
 * QueryBuilder erhält einige Attribute die gesetzt werden können. Wird ein
 * Attribut nicht gesetzt so wird für die Query ein Defaultwert benutzt, oder
 * der Teil der Query wird übersprungen.
 * 
 * @author Gilles
 * 
 */
public class QueryBuilder {

	public enum ArtVonSport {
		EINZELSPORT, TEAMSPORT, EGAL;
	}
	
	public enum InnenAussen {
		INNEN, AUSSEN, EGAL;
	}

	private ArtVonSport selectedArtVonSport = ArtVonSport.EGAL;
	private InnenAussen selectedInnenAussen = InnenAussen.EGAL;
	private double maximalPrice = -1;
	private KoerperlicheEinschraenkungen[] einschraenkungen = new KoerperlicheEinschraenkungen[0];
	private Ziele[] ziele = new Ziele[0];
	private Choices exotisch, kampfsport, koerperbetont, wassersport;

	/**
	 * führt die Query, bzw. die einzelnen Teilqueries aus. Die Methode gibt die
	 * Resultate der Query als eine eine Map, die die Sportarten enthält.
	 * 
	 * @return
	 */
	public Map<String, Sportangebot> execute() {
		Map<String, Sportangebot> sport = Queries.querySport();

		if (selectedArtVonSport == ArtVonSport.EINZELSPORT) {
			sport = Queries.queryEinzelsport(sport);
		} else if (selectedArtVonSport == ArtVonSport.TEAMSPORT) {
			sport = Queries.queryTeamsport(sport);
		}

		if (maximalPrice >= 0) {
			sport = Queries.queryPrice(sport, maximalPrice);
		}

		if (einschraenkungen.length > 0) {
			sport = Queries.queryFilterKörperlicheEinschraenkungen(sport,
					einschraenkungen);
		}
		
		if(selectedInnenAussen != InnenAussen.EGAL){
			boolean indoor = selectedInnenAussen == InnenAussen.INNEN ? true : false;
			sport = Queries.queryIndoor(sport, indoor);
		}
		
		if(ziele.length > 0){
			sport = Queries.queryZiele(sport, ziele);
		}
		
		sport = Queries.query4Attributes(sport, kampfsport, exotisch, koerperbetont, wassersport);
		
		return sport;

	}

	public ArtVonSport getSelectedArtVonSport() {
		return selectedArtVonSport;
	}

	public void setSelectedArtVonSport(ArtVonSport selectedArtVonSport) {
		this.selectedArtVonSport = selectedArtVonSport;
	}

	public double getMaximalPrice() {
		return maximalPrice;
	}

	/**
	 * Setzt den maximalen Preis. Ist ignorieren true, dann wird der Preis bei
	 * den Queries ignoriert
	 * 
	 * @see QueryBuilder.setMaximalPrice()
	 * @param maximalPrice
	 * @param ignorieren
	 */
	public void setMaximalPrice(double maximalPrice, boolean ignorieren) {
		if (ignorieren) {
			this.maximalPrice = -1;
		} else {
			this.maximalPrice = maximalPrice;
		}

	}

	public void setMaximalPrice(double maximalPrice) {
		this.maximalPrice = maximalPrice;
	}

	public KoerperlicheEinschraenkungen[] getEinschraenkungen() {
		return einschraenkungen;
	}

	public void setEinschraenkungen(
			KoerperlicheEinschraenkungen[] einschraenkungen) {
		this.einschraenkungen = einschraenkungen;
	}

	public InnenAussen getSelectedInnenAussen() {
		return selectedInnenAussen;
	}

	public void setSelectedInnenAussen(InnenAussen selectedInnenAussen) {
		this.selectedInnenAussen = selectedInnenAussen;
	}

	public Ziele[] getZiele() {
		return ziele;
	}

	public void setZiele(Ziele[] ziele) {
		this.ziele = ziele;
	}

	public Choices getExotisch() {
		return exotisch;
	}

	public void setExotisch(Choices exotisch) {
		this.exotisch = exotisch;
	}

	public Choices getKampfsport() {
		return kampfsport;
	}

	public void setKampfsport(Choices kampfsport) {
		this.kampfsport = kampfsport;
	}

	public Choices getKoerperkontakt() {
		return koerperbetont;
	}

	public void setKoerperkontakt(Choices koerperkontakt) {
		this.koerperbetont = koerperkontakt;
	}

	public Choices getWassersport() {
		return wassersport;
	}

	public void setWassersport(Choices wassersport) {
		this.wassersport = wassersport;
	}
	
	

	
	

}
