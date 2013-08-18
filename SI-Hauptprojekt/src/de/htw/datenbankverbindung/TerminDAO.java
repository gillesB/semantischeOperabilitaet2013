package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import business.model.Kurs;
import business.model.Termin;
import business.model.TerminDetails;
import business.model.Wochentag;

public class TerminDAO extends DAO<Termin> {
	
	@Override
	public Termin findById(int id) {
		Termin termin = new Termin();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Termin WHERE idTermin = " + id);
            if(result.first())
            	termin.setIdTermin(result.getInt(Termin.ID_TERMIN));
            	termin.setIdKurs(result.getInt(Termin.ID_KURS));
            	termin.setIdWochentag(result.getInt(Termin.ID_WOCHENTAG));
            	termin.setAnfangszeit(result.getTime(Termin.ANFANGSZEIT));
            	termin.setEndzeit(result.getTime(Termin.ENDZEIT));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return termin;	
	}
	
	/*
	 * @param idKurs ist der uebergebene id eines Kurses
	 * @return list: Liste aller moegliche Termine eines Kurses
	 * Diese Methode liefert alle Termine eines gegebenen Kurses zurueck.
	 */
	
	public List<TerminDetails> findAllTerminByIdKurs(int idKurs){
		List<TerminDetails> list = new ArrayList<TerminDetails>();
		try {
			ResultSet results = this .connect
                                    .createStatement().executeQuery("SELECT * FROM " + Termin.TABLE +
																	" INNER JOIN " + Kurs.TABLE +
																	" ON Termine.idKurs = Kurs.idKurs " +
																	"INNER JOIN Wochentag as tag " +
																	"ON Termine.idWochentag = tag.idWochentag " +
																	"WHERE " + Termin.TABLE + "." + Termin.ID_KURS + " = " + idKurs
                                    								);

			while (results.next()){
            	TerminDetails termin = new TerminDetails();
            	
            	termin.setIdTermin(results.getInt(Termin.ID_TERMIN));
            	termin.setIdKurs(results.getInt(Termin.ID_KURS));
            	termin.setIdWochentag(results.getInt(Termin.ID_WOCHENTAG));
            	termin.setWochentag(results.getString(Wochentag.WOCHENTAG));
            	termin.setAnfangszeit(results.getTime(Termin.ANFANGSZEIT)); 
            	termin.setEndzeit(results.getTime(Termin.ENDZEIT));
            	
            	list.add(termin); 
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
		
	}
	

}
