package business.model;

import java.sql.Time;
import java.text.SimpleDateFormat;

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
		return wochentag + ", " + parseTime(getAnfangszeit()) + "-" + parseTime(getEndzeit());
	}
	
	private String parseTime(Time time){
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
		return formater.format(time);
	}
	
}
