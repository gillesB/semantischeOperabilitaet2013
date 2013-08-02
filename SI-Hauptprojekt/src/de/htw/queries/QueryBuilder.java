package de.htw.queries;

import java.util.Map;

import de.htw.queries.QueryBuilder.InnenAussen;
import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Ziele;

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

	
	

}
