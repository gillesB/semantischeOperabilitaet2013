package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.Sportangebot;

public class SportangebotDAO extends DAO<Sportangebot> {

	@Override
	public Sportangebot findById(int id) {
		Sportangebot angebot = new Sportangebot();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Sportangebot WHERE idSportangebot = " + id);
            if(result.first())
            	angebot.setIdSportangebot(result.getInt("idSportangebot"));
            	angebot.setName(result.getString("name"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return angebot;	
	}

}
