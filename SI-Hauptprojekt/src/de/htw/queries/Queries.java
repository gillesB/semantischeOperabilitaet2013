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
		//TODO also check if Sportart is also in DB
		
		Set<OWLClass> fetchedClasses = ontolgy.doQuery("Sport");
		String query = "SELECT DISTINCT name FROM  Sportangebot";
		ResultSet results = null;
		Set<OWLClass> sportClasses = new HashSet<OWLClass>();
		try {
			results = database.createStatement().executeQuery(query);
			while (results.next()) {
				String sportName = results
						.getString("Sportangebot.name");
				for (OWLClass sportclass : fetchedClasses) {
					if (shortFormProvider.getShortForm(sportclass).equals(
							sportName)) {
						sportClasses.add(sportclass);
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

	public static Set<OWLClass> queryEinzelsport(Set<OWLClass> inputClasses) {
		String query = "Einzelsport "
				+ createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

		return fetchedClasses;
	}

	public static Set<OWLClass> queryTeamsport(Set<OWLClass> inputClasses) {
		String query = "Teamsport " + createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

		return fetchedClasses;
	}

	public static Set<OWLClass> queryPrice(Set<OWLClass> inputClasses, double d) {
		String query = "SELECT DISTINCT Sportangebot.name FROM "
				+ " Sportangebot, Kurs WHERE "
				+ " Sportangebot.idSportangebot = Kurs.idSportangebot "
				+ "AND kosten <= " + d
				+ createAccepableClassesCondition_DB(inputClasses);

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
	 * Iteriert über sportClasses und acceptableClasses und wirft alle
	 * Sportarten aus sportClasses raus, die sich nicht in acceptableClasses
	 * befinden
	 * 
	 * @param sportClasses
	 * @param acceptableClasses
	 * @return gefiltertes sportClasses
	 */
	private static Set<OWLClass> removeUnacceptableClasses(
			Set<OWLClass> sportClasses, ResultSet acceptableClasses) {
		if (acceptableClasses == null) {
			return sportClasses;
		}
		
		Set<OWLClass> acceptableClassesSet = new HashSet<>();
		try {
			while (acceptableClasses.next()) {
				String sportName = acceptableClasses
						.getString("Sportangebot.name");
				for (OWLClass sportclass : sportClasses) {
					if (shortFormProvider.getShortForm(sportclass).equals(
							sportName)) {
						acceptableClassesSet.add(sportclass);
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acceptableClassesSet;
	}

	private static String createAccepableClassesCondition_DB(
			Set<OWLClass> inputClasses) {
		StringBuilder condition = new StringBuilder();
		boolean first = true;
		for (OWLClass owlClass : inputClasses) {
			if(first){
				condition.append(" AND ");
				first = false;
			} else {
				condition.append(" OR ");
			}
			condition.append(" Sportangebot.name = '");
			condition.append(shortFormProvider.getShortForm(owlClass));
			condition.append("'"+System.lineSeparator());
		}

		return condition.toString();
	}

	private static String createAccepableClassesCondition_Ontology(
			Set<OWLClass> inputClasses) {
		StringBuilder condition = new StringBuilder("and (");
		int i = 0;
		for (OWLClass owlClass : inputClasses) {
			condition.append("" + shortFormProvider.getShortForm(owlClass));
			if (i < inputClasses.size() - 1) {
				condition.append(" or ");
			}
			i++;
		}
		condition.append(")");
		return condition.toString();
	}

}
