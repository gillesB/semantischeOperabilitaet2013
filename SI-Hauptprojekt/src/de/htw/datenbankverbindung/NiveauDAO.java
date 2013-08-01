package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.Niveau;

public class NiveauDAO extends DAO<Niveau> {

	@Override
	public Niveau findById(int id) {
		Niveau niveau = new Niveau();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Niveau WHERE idNiveau = " + id);
            if(result.first())
            	niveau.setIdNiveau(result.getInt("idNiveau"));
            	niveau.setName(result.getString("name"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return niveau;	
	}

}
