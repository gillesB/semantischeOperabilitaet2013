package de.htw.queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;
import de.htw.datenbankverbindung.DAOFactory;
import de.htw.ontologieverbindung.OntoUtil;
import de.htw.ontologieverbindung.OntolgyConnection;

public class Queries {

	private static OntolgyConnection ontolgy = OntolgyConnection.getInstance();
	private static Connection database = DAOFactory.getInstance()
			.getConnection();

	/**
	 * Gibt die Menge der Sportarten zurück, die sowohl in der Onto als auch in
	 * der DB sind zurück. </br> Warum? Onto gibt wegen OpenWorld Konzept nicht
	 * nur Sportarten zurück sondern auch verschiedene Äquivalenzklassen etc...
	 * DB hingegen enthält wesentlich mehr Sportarten als die Ontology. Es ist
	 * also auch ein kleiner Test was wir überhaupt abdecken.
	 * 
	 * @return
	 */
	public static Map<String, Sportangebot> querySport() {
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(Sportangebot.ONTO_CLASS);
		String query = "SELECT " + Sportangebot.ID + ", " + Sportangebot.NAME + " FROM " + Sportangebot.TABLE;
		ResultSet results = null;
		Map<String, Sportangebot> sportClasses = new HashMap<String, Sportangebot>();
		try {
			results = database.createStatement().executeQuery(query);
			while (results.next()) {
				Integer sportID = results.getInt(Sportangebot.ID);
				String sportName = results.getString(Sportangebot.NAME);
				for (OWLClass sportclass : fetchedClasses) {
					if (OntoUtil.getShortForm(sportclass).equals(
							sportName)) {						
						sportClasses.put(sportName, new Sportangebot(sportID, sportName));
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sportClasses;
	}

	/**
	 * Gibt die Einzelsportarten zurück die sowohl in der Onto als auch in
	 * inputClasses enthalten sind.
	 * 
	 * @param inputClasses
	 * @return
	 */
	public static Map<String, Sportangebot> queryEinzelsport(Map<String, Sportangebot> inputClasses) {
		String query = "Einzelsport "
				+ createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);
		
		return removeUnacceptableClasses(inputClasses, fetchedClasses);
	}

	/**
	 * Gibt die Teamsportarten zurück die sowohl in der Onto als auch in
	 * inputClasses enthalten sind.
	 * 
	 * @param inputClasses
	 * @return
	 */
	public static Map<String, Sportangebot> queryTeamsport(Map<String, Sportangebot> inputClasses) {
		String query = "Teamsport "
				+ createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

		return removeUnacceptableClasses(inputClasses, fetchedClasses);
	}

	
	/**
	 * Filtert die Sportarten aus inputClasses raus, die man NICHT mit den angegebenen Einschränkungen machen kann.
	 * 
	 * @param inputClasses
	 * @param koerperlicheEinschraenkungen
	 * @return
	 */
	public static Map<String, Sportangebot> queryFilterKörperlicheEinschraenkungen(Map<String, Sportangebot> inputClasses, KoerperlicheEinschraenkungen ... koerperlicheEinschraenkungen ){
		StringBuilder query = new StringBuilder("Sport ");
		for(KoerperlicheEinschraenkungen k : koerperlicheEinschraenkungen){
			query.append("and not (ungeeignetBei some ");
			query.append(k.getName());
			query.append(") ");
		}
		query.append(createAccepableClassesCondition_Ontology(inputClasses));
		
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query.toString());
		
		return removeUnacceptableClasses(inputClasses, fetchedClasses);
		
	}
	
	
	
	/**
	 * Gibt die Sportarten zurück die in inputClasses stehen und höchsten
	 * maxPrice kosten.
	 * 
	 * @param inputClasses
	 * @param maxPrice
	 * @return
	 */
	public static Map<String, Sportangebot> queryPrice(Map<String, Sportangebot> inputClasses,
			double maxPrice) {
		String query = "SELECT DISTINCT Sportangebot.name FROM "
				+ " Sportangebot, Kurs WHERE "
				+ " Sportangebot.idSportangebot = Kurs.idSportangebot "
				+ "AND kosten <= " + maxPrice
				+ createAccepableClassesCondition_DB(inputClasses);

		return queryDB(inputClasses, query);
	}
	
	public static Map<String, Sportangebot> queryIndoor(Map<String, Sportangebot> inputClasses, boolean indoor){
		int indoor_db = indoor ? 1 : 0;
		String query = "SELECT DISTINCT Sportangebot.name FROM "
				+ " Sportangebot, Kurs, Ort WHERE "
				+ " Sportangebot.idSportangebot = Kurs.idSportangebot "
				+ " AND Kurs.idOrt = Ort.idOrt "
				+ " AND Ort.innen = " + indoor_db
				+ createAccepableClassesCondition_DB(inputClasses);
		
		return queryDB(inputClasses, query);
		
	}
	
	/**
	 * führt eine Query auf der DB aus und filtert die nicht akzeptierbaren Resultate aus inputClasses
	 * @param inputClasses
	 * @param query
	 * @return
	 */
	private static Map<String, Sportangebot> queryDB(Map<String, Sportangebot> inputClasses, String query){
		System.out.println("DB: " + query);
		ResultSet results = null;
		try {
			results = database.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return removeUnacceptableClasses(inputClasses, results);
	}
	
	/**
	 * Filtert die Sportarten aus originalClasses raus, die sich nicht in
	 * acceptableClasses befinden. </br> Diese Methode wird für Queries auf der
	 * Onotlogie benutzt um eine Menge aus Sportangeboten zu erstellen.
	 * 
	 * @param sportClasses
	 * @param acceptableClasses
	 * @return gefiltertes sportClasses
	 */
	private static  Map<String, Sportangebot> removeUnacceptableClasses(Map<String, Sportangebot> originalClasses, Set<OWLClass> acceptableClasses){
		Map<String, Sportangebot> sportangebote = new HashMap<String, Sportangebot>();
		for(OWLClass fetchedClass : acceptableClasses){
			String sportName = OntoUtil.getShortForm(fetchedClass);
			if(originalClasses.containsKey(sportName)){
				sportangebote.put(sportName, originalClasses.get(sportName));
			}			
		}
		return sportangebote;
	}
	

	/**
	 * Filtert die Sportarten aus originalClasses raus, die sich nicht in
	 * acceptableClasses befinden. </br> Diese Methode wird für Queries auf der
	 * Datenbank benutzt um eine Menge aus Sportangeboten zu erstellen.
	 * 
	 * @param sportClasses
	 * @param acceptableClasses
	 * @return gefiltertes sportClasses
	 */
	private static Map<String, Sportangebot> removeUnacceptableClasses(
			Map<String, Sportangebot> originalClasses, ResultSet acceptableClasses) {
		if (acceptableClasses == null) {
			return originalClasses;
		}
		
		Map<String, Sportangebot> sportangebote = new HashMap<String, Sportangebot>();
		try {
			while (acceptableClasses.next()) {
				String sportName = acceptableClasses
						.getString("Sportangebot.name");
				sportangebote.put(sportName, originalClasses.get(sportName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sportangebote;
	}

	/**
	 * Condition Anhang an die DB-Query </br> Select * from xy where ... </br>
	 * AND ( Sportangebot.name = 'Basketball'</br> OR Sportangebot.name =
	 * 'Volleyball'</br> ... )
	 * 
	 * @param inputClasses
	 * @return
	 */
	private static String createAccepableClassesCondition_DB(
			Map<String, Sportangebot> inputClasses) {
		StringBuilder condition = new StringBuilder();
		boolean first = true;
		for (String sportName : inputClasses.keySet()) {
			if (first) {
				condition.append(" AND ( ");
				first = false;
			} else {
				condition.append(" OR ");
			}
			condition.append(" Sportangebot.name = '");
			condition.append(sportName);
			condition.append("'" + System.lineSeparator());
		}
		condition.append(" )");
		
		return condition.toString();
	}

	/**
	 * Condition Anhang an die Onto-Query </br> bla and (Basketball or
	 * Volleyball or ... )
	 * 
	 * @param inputClasses
	 * @return
	 */
	private static String createAccepableClassesCondition_Ontology(
			Map<String, Sportangebot> inputClasses) {
		StringBuilder condition = new StringBuilder("and (");
		int i = 0;
		for (String sportangebotName : inputClasses.keySet()) {
			condition.append(" " + sportangebotName);
			if (i < inputClasses.size() - 1) {
				condition.append(" or ");
			}
			i++;
		}
		condition.append(")");
		return condition.toString();
	}

}
