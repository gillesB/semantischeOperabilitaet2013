package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            	termin.setIdTermin(result.getInt("idKurs"));
            	termin.setIdKurs(result.getInt("idKurs"));
            	termin.setIdWochentag(result.getInt("idWochentag"));
            	termin.setAnfangszeit(result.getTime("anfangszeit"));
            	termin.setAnfangszeit(result.getTime("endzeit"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return termin;	
	}
	

}
