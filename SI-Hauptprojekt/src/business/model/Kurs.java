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
	public void setIdKurs(int idKurs) {
		this.idKurs = idKurs;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIdSportangebot(int idSportangebot) {
		this.idSportangebot = idSportangebot;
	}
	public void setIdOrt(int idOrt) {
		this.idOrt = idOrt;
	}
	public void setIdZielgruppe(int idZielgruppe) {
		this.idZielgruppe = idZielgruppe;
	}
	public void setKosten(int kosten) {
		this.kosten = kosten;
	}
	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}
	public void setIdCampus(int idCampus) {
		this.idCampus = idCampus;
	}
	
	@Override
	public String toString() {
		return "Kurs [idKurs=" + idKurs + ", name=" + name
				+ ", idSportangebot=" + idSportangebot + ", idOrt=" + idOrt
				+ ", idZielgruppe=" + idZielgruppe + ", kosten=" + kosten
				+ ", idNiveau=" + idNiveau + ", idCampus=" + idCampus + "]";
	}
	

}
