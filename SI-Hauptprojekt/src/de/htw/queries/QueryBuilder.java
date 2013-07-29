package de.htw.queries;

import java.util.Map;

import business.model.Sportangebot;

public class QueryBuilder {

	public enum ArtVonSport{
		EINZELSPORT, TEAMSPORT, EGAL;
	}
	
	private ArtVonSport selectedArtVonSport = ArtVonSport.EGAL;
	private double maximalPrice = 0.0;

	public Map<String, Sportangebot> execute() {
		Map<String, Sportangebot> sport = Queries.querySport();
		
		if(selectedArtVonSport == ArtVonSport.EINZELSPORT){
			sport = Queries.queryEinzelsport(sport);
		} else if(selectedArtVonSport == ArtVonSport.TEAMSPORT){
			sport = Queries.queryTeamsport(sport);
		}
		
		sport = Queries.queryPrice(sport, maximalPrice);
		
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

	public void setMaximalPrice(double maximalPrice) {
		this.maximalPrice = maximalPrice;
	}


	
	

}
