package business.model;

import java.sql.Time;

public class Termin {

	public static final String TABLE = "Termine";
	public static final String ID_KURS = "idKurs";
	public static final String ID_WOCHENTAG = "idWochentag";
	public static final String ANFANGSZEIT = "anfangszeit";
	public static final String ENDZEIT = "endzeit";
	public static final String ID_TERMIN = "idTermin";
	
	private int idTermin;
	private int idKurs;
	private int idWochentag;
	private Time anfangszeit;
	private Time endzeit;

	public int getIdTermin() {
		return idTermin;
	}

	public void setIdTermin(int idTermin) {
		this.idTermin = idTermin;
	}

	public int getIdKurs() {
		return idKurs;
	}

	public void setIdKurs(int idKurs) {
		this.idKurs = idKurs;
	}

	public int getIdWochentag() {
		return idWochentag;
	}

	public void setIdWochentag(int idWochentag) {
		this.idWochentag = idWochentag;
	}

	public Time getAnfangszeit() {
		return anfangszeit;
	}

	public void setAnfangszeit(Time anfangszeit) {
		this.anfangszeit = anfangszeit;
	}

	public Time getEndzeit() {
		return endzeit;
	}

	public void setEndzeit(Time endzeit) {
		this.endzeit = endzeit;
	}

}
