package de.htw.queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import de.htw.datenbankverbindung.DAOFactory;
import de.htw.ontologieverbindung.OntolgyConnection;

public class Queries {

	private static OntolgyConnection ontolgy = OntolgyConnection.getInstance();
	private static Connection database = DAOFactory.getInstance()
			.getConnection();
	private static ShortFormProvider shortFormProvider = new SimpleShortFormProvider();

	public static Set<OWLClass> querySport() {
		Set<OWLClass> fetchedClasses = ontolgy.doQuery("Sport");

		return fetchedClasses;
	} 
	
	
	public static Set<OWLClass> queryEinzelsport(Set<OWLClass> inputClasses) {
		Set<OWLClass> fetchedClasses = ontolgy.doQuery("Einzelsport");

		return intersection(inputClasses, fetchedClasses);
	}

	public static Set<OWLClass> queryTeamsport(Set<OWLClass> inputClasses) {
		Set<OWLClass> fetchedClasses = ontolgy.doQuery("Teamsport");	

		return intersection(inputClasses, fetchedClasses);
	}

	public static Set<OWLClass> queryPrice(Set<OWLClass> inputClasses,
			double d) {
		String query = "SELECT DISTINCT Sportangebot.name FROM "
				+ " Sportangebot, Kurs WHERE "
				+ " Sportangebot.idSportangebot = Kurs.idSportangebot "
				+ "AND kosten = " + d
				+ createAccepableClassesCondition(inputClasses);

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
	 * Iteriert über sportClasses und acceptableClasses und werft alle Sportarten aus sportClasses raus, die sich nicht in 
	 * acceptableClasses befinden
	 * @param sportClasses
	 * @param acceptableClasses
	 * @return gefiltertes sportClasses
	 */
	private static Set<OWLClass> removeUnacceptableClasses(
			Set<OWLClass> sportClasses, ResultSet acceptableClasses) {
		if(acceptableClasses == null){
			return sportClasses;
		}
		
		try {
			while (acceptableClasses.next()) {
			    String sportName = acceptableClasses.getString("Sportangebot.name");
			    for(OWLClass sportclass : sportClasses){
			    	if(shortFormProvider.getShortForm(sportclass).equals(sportName)){
			    		break;
			    	}
			    	sportClasses.remove(sportclass);
			    }			    
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	private static String createAccepableClassesCondition(
			Set<OWLClass> inputClasses) {
		StringBuilder condition = new StringBuilder();
		for (OWLClass owlClass : inputClasses) {
			condition.append(" AND '"
					+ shortFormProvider.getShortForm(owlClass) + "'"
					+ System.lineSeparator());
		}

		return condition.toString();
	}
	
	private static Set<OWLClass> intersection(Set<OWLClass> set1, Set<OWLClass> set2){
		Set<OWLClass> intersection = new HashSet<OWLClass>(set1);
		intersection.retainAll(set2);
		return intersection;
	}

}
