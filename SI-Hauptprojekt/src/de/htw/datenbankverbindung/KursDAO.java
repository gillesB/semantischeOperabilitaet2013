package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.model.Kurs;

public class KursDAO extends DAO<Kurs>{

	/** The list. */
	private List<Kurs> list = new ArrayList<Kurs>();
	
	public List<Kurs> findAllInnenKurse(boolean innen){
		try {
            ResultSet results = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Ort WHERE innen = " + innen);
            
            while (results.next()){
    			/*System.out.println("idOrt = " + results.getInt("idOrt") + " Name = " + results.getString("Name") + " Innen =" + results.getBoolean("innen"));
            	Ort ort = new Ort(results.getInt("idOrt"), results.getString("name"), results.getBoolean("innen"));
            	list.add(ort); */
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
	}

	@Override
	public Kurs findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Kurs> findAllKurseMitMaxPreisVon(int kosten){
		try {
            ResultSet results = this .connect
                                    .createStatement().executeQuery("SELECT idKurs, Campus.Name as Campus, Kurs.name as Kursname, spa.name as Sportangebotsname , o.name as Ort, Zielgruppe.name as Zielgruppe, Niveau.name as Niveau, Kosten" +
                                    								" FROM Kurs" +
														    		"INNER JOIN Sportangebot as spa ON  Kurs.idSportangebot = spa.idSportangebot " +
														    		"INNER JOIN Ort as o ON  Kurs.idOrt= o.idOrt " +
														            "INNER JOIN Zielgruppe ON  Kurs.idZielgruppe= Zielgruppe.idZielgruppe " +
														    		"INNER JOIN Niveau ON  Kurs.idNiveau= Niveau.idNiveau " +
														            "INNER JOIN Campus ON  Kurs.idCampus= Campus.idCampus " +
														    		"where kosten <= " + kosten
                                    								);
            
            while (results.next()){
    			/*System.out.println("idOrt = " + results.getInt("idOrt") + " Name = " + results.getString("Name") + " Innen =" + results.getBoolean("innen"));
            	Ort ort = new Ort(results.getInt("idOrt"), results.getString("name"), results.getBoolean("innen"));
            	list.add(ort); */
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
		
	}
}
