package business.model;

public class KursMitDetails extends Kurs {
	
	private String kursname;
	private String ort;
	private String zielgruppe;
	private String niveau;
	private String campus;
	
	public KursMitDetails(){
		
	}

	public String getKursname() {
		return kursname;
	}

	public void setKursname(String kursname) {
		this.kursname = kursname;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getZielgruppe() {
		return zielgruppe;
	}

	public void setZielgruppe(String zielgruppe) {
		this.zielgruppe = zielgruppe;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	@Override
	public String toString() {
		return "Kursname:\t"			+ kursname +
				"\n Ort:\t"				+ ort +
				"\n Zielgruppe:\t"		+ zielgruppe + 
				"\n Niveau:\t"			+ niveau +
				"\n Campus:\t"			+ campus;
	}
	
	
}
