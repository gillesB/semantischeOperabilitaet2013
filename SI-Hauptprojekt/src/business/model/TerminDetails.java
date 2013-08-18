package business.model;

public class TerminDetails extends Termin {
	private String wochentag;

	public String getWochentag() {
		return wochentag;
	}

	public void setWochentag(String wochentag) {
		this.wochentag = wochentag;
	}

	@Override
	public String toString() {
		return wochentag + ", " + getAnfangszeit() + "-" + getEndzeit();
	}
	
}
