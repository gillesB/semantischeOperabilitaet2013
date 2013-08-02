package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.semanticweb.HermiT.model.Term;

import business.model.Kurs;
import business.model.Termin;

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
	

}
