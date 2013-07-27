package business.model;

public class Kurs {
	
	private int idKurs;
	private String name;
	private int idSportangebot;
	private int idOrt;
	private int idZielgruppe;
	private int kosten;
	private int idNiveau;
	private int idCampus;
	
	public int getIdKurs() {
		return idKurs;
	}
	public String getName() {
		return name;
	}
	public int getIdSportangebot() {
		return idSportangebot;
	}
	public int getIdOrt() {
		return idOrt;
	}
	public int getIdZielgruppe() {
		return idZielgruppe;
	}
	public int getKosten() {
		return kosten;
	}
	public int getIdNiveau() {
		return idNiveau;
	}
	public int getIdCampus() {
		return idCampus;
	}

}
