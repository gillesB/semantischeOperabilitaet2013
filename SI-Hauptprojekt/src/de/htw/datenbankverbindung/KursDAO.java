package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import business.model.Kurs;
import business.model.KursMitDetails;

public class KursDAO extends DAO<Kurs>{

	/** The list. */
	private List<Kurs> list = new ArrayList<Kurs>();
	
	public List<Kurs> findAllInnenKurse(boolean innen){
		try {
            ResultSet results = this .connect
                                    .createStatement().executeQuery(commonSQLStatment() + "WHERE innen = " + innen);
            
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
		Kurs kurs = new Kurs();
		try {
            ResultSet result = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Kurs WHERE idKurs = " + id);
            if(result.first())
            	kurs.setIdNiveau(result.getInt("idKurs"));
            	kurs.setName(result.getString("name"));
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return kurs;	
	}
	
	public List<Kurs> findAllKurseMitMaxPreisVon(int kosten){
		try {
            ResultSet results = this .connect
                                    .createStatement().executeQuery(commonSQLStatment() +
														    		"where kosten <= " + kosten
                                    								);
            
            while (results.next()){
            		
    			Kurs kurs = new Kurs();
    			kurs.setIdKurs(results.getInt("idKurs"));
    			kurs.setIdCampus(results.getInt("idCampus"));
    			kurs.setName(results.getString("name"));
    			kurs.setIdSportangebot(results.getInt("idSportangebot"));
    			
    			kurs.setIdOrt(results.getInt("idOrt"));
    			kurs.setIdZielgruppe(results.getInt("idZielgruppe"));
    			kurs.setIdNiveau(results.getInt("idNiveau"));
    			kurs.setKosten(results.getInt("kosten"));
    			
            	list.add(kurs); 
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
		
	}
	
	public List<Kurs> findAllKurseImZeitraum(Time von, Time bis){
		try {
            list = null;
			ResultSet results = this .connect
                                    .createStatement().executeQuery("SELECT * FROM Termine " +
																	"INNER JOIN Kurs " +
																	"ON  Termine.idKurs = Kurs.idKurs " +
																	"INNER JOIN Wochentag as tag " +
																	"ON  Termine.idWochentag = tag.idWochentag " +
																	"WHERE (anfangszeit >= " + von + " AND endzeit <= " + bis + ")"
                                    								);
            
            while (results.next()){
            	Kurs kurs = new Kurs();
    			    			
            	list.add(kurs); 
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
		
	}
	
	
	/*
	 * @param idSportangebot ist der uebergebene id des Sportangebotes
	 * @return list: Liste aller Kurse
	 * Diese Methode liefert alle Kurse eines gegebenen Sportangebotes zurueck.
	 */
	public List<KursMitDetails> findAllKurseByIdSportangebot(int idSportangebot){
		List<KursMitDetails> list = new ArrayList<KursMitDetails>();
		try {
			ResultSet results = this .connect
                                    .createStatement().executeQuery(commonDetailsSQLStatment() + "WHERE Kurs.idSportangebot = " + idSportangebot);
            
            while (results.next()){
            	KursMitDetails kurs = new KursMitDetails();
            	
            	kurs.setIdKurs(results.getInt("idKurs"));
    			kurs.setIdCampus(results.getInt("idCampus"));
    			kurs.setIdSportangebot(results.getInt("idSportangebot"));
    			kurs.setIdOrt(results.getInt("idOrt"));
    			kurs.setIdZielgruppe(results.getInt("idZielgruppe"));
    			kurs.setIdNiveau(results.getInt("idNiveau"));
    			
    			kurs.setKursname(results.getString("kursname"));
    			kurs.setOrt(results.getString("ort"));
    			kurs.setCampus(results.getString("campus"));
    			kurs.setZielgruppe(results.getString("zielgruppe"));
    			kurs.setNiveau(results.getString("niveau"));
    			kurs.setKosten(results.getInt("kosten"));
    			    			
            	list.add(kurs); 
    		}
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		return list;
		
	}
	
	private String commonSQLStatment(){
		return "SELECT *" +
				" FROM Kurs " +
	    		"INNER JOIN Sportangebot as spa ON  Kurs.idSportangebot = spa.idSportangebot " +
	    		"INNER JOIN Ort as o ON  Kurs.idOrt= o.idOrt " +
	            "INNER JOIN Zielgruppe ON Kurs.idZielgruppe= Zielgruppe.idZielgruppe " +
	    		"INNER JOIN Niveau ON  Kurs.idNiveau= Niveau.idNiveau " +
	            "INNER JOIN Campus ON  Kurs.idCampus= Campus.idCampus ";
	}
	
	private String commonDetailsSQLStatment(){
		return "SELECT Kurs.idKurs, Kurs.idSportangebot, Kurs.idCampus, Kurs.idOrt, Kurs.idZielgruppe, Kurs.idNiveau, Kurs.name as kursname, o.name as ort, Zielgruppe.name as zielgruppe, Niveau.name as niveau, Campus.name as campus, kosten" +
				" FROM Kurs " +
	    		"INNER JOIN Sportangebot as spa ON Kurs.idSportangebot = spa.idSportangebot " +
	    		"INNER JOIN Ort as o ON  Kurs.idOrt= o.idOrt " +
	            "INNER JOIN Zielgruppe ON  Kurs.idZielgruppe= Zielgruppe.idZielgruppe " +
	    		"INNER JOIN Niveau ON  Kurs.idNiveau= Niveau.idNiveau " +
	            "INNER JOIN Campus ON  Kurs.idCampus= Campus.idCampus ";
	}
	
}
