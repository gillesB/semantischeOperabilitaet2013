package de.htw.datenbankverbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import business.model.Kurs;

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
            	
    			SportangebotDAO daoSport = new SportangebotDAO();
    			OrtDAO daoOrt = new OrtDAO();
    			ZielgruppeDAO daoZiel = new ZielgruppeDAO();
    			NiveauDAO daoNiveau = new NiveauDAO();
    			
    			String SportangebotName = daoSport.findById(results.getInt("idSportangebot")).getName();
    			String ort = daoOrt.findById(results.getInt("idOrt")).getName();
    			String zielgruppe = daoZiel.findById(results.getInt("idZielgruppe")).getName();
    			String niveau = daoNiveau.findById(results.getInt("idNiveau")).getName();
    			
    			//print Sportangebote
    			System.out.println("idKurs = " + results.getInt("idKurs") + ", Kursname =" + results.getString("name") + 
				", Sportangebotsname= " + SportangebotName + ", Ort =" + ort + " Zielgruppe =" + zielgruppe +
				", Niveau =" + niveau + " Kosten =" + results.getInt("kosten") 
				);
    			System.out.println("-----------------------------------------------------");
    			
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
	
	private String commonSQLStatment(){
		return "SELECT *" +
				" FROM Kurs " +
	    		"INNER JOIN Sportangebot as spa ON  Kurs.idSportangebot = spa.idSportangebot " +
	    		"INNER JOIN Ort as o ON  Kurs.idOrt= o.idOrt " +
	            "INNER JOIN Zielgruppe ON  Kurs.idZielgruppe= Zielgruppe.idZielgruppe " +
	    		"INNER JOIN Niveau ON  Kurs.idNiveau= Niveau.idNiveau " +
	            "INNER JOIN Campus ON  Kurs.idCampus= Campus.idCampus ";
	}
	
	
	private void printAllSportangebote(Kurs kurs){

	}
}
