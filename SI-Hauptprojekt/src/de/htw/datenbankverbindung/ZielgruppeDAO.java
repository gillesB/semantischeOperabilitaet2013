package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.model.Zielgruppe;

public class ZielgruppeDAO extends DAO<Zielgruppe> {

	@Override
	public Zielgruppe findById(int id) {
		Zielgruppe gruppe = new Zielgruppe();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Zielgruppe WHERE idZielgruppe = " + id);
            if(result.first())
            	gruppe.setIdZielgruppe(result.getInt("idZielgruppe"));
            	gruppe.setName(result.getString("name"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return gruppe;	
	}

}
