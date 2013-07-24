package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Ort;

public class OrtDAO extends DAO<Ort> {
	
	/** The list. */
	private List<Ort> list = new ArrayList<Ort>();

	public Ort findById(int id){
		Ort ort = new Ort();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Ort WHERE idOrt = " + id);
            if(result.first())
            	ort = new Ort(id, result.getString("name"), result.getBoolean("innen"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return ort;	
	}
	
	public List<Ort> findAllInnenOrte(boolean innen){
		try {
            ResultSet results = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Ort WHERE innen = " + innen);
            
            while (results.next()){
    			System.out.println("idOrt = " + results.getInt("idOrt") + " Name = " + results.getString("Name") + " Innen =" + results.getBoolean("innen"));
            	Ort ort = new Ort(results.getInt("idOrt"), results.getString("name"), results.getBoolean("innen"));
            	list.add(ort);
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
	}
}
