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

	/**
	 * Gibt die Menge der Sportarten zurück, die sowohl in der Onto als auch in
	 * der DB sind zurück. </br> Warum? Onto gibt wegen OpenWorld Konzept nicht
	 * nur Sportarten zurück sondern auch verschiedene Äquivalenzklassen etc...
	 * DB hingegen enthält wesentlich mehr Sportarten als die Ontology. Es ist
	 * also auch ein kleiner Test was wir überhaupt abdecken.
	 * 
	 * @return
	 */
	public static Set<OWLClass> querySport() {
		Set<OWLClass> fetchedClasses = ontolgy.doQuery("Sport");
		String query = "SELECT DISTINCT name FROM  Sportangebot";
		ResultSet results = null;
		Set<OWLClass> sportClasses = new HashSet<OWLClass>();
		try {
			results = database.createStatement().executeQuery(query);
			while (results.next()) {
				String sportName = results.getString("Sportangebot.name");
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

	/**
	 * Gibt die Einzelsportarten zurück die sowohl in der Onto als auch in
	 * inputClasses enthalten sind.
	 * 
	 * @param inputClasses
	 * @return
	 */
	public static Set<OWLClass> queryEinzelsport(Set<OWLClass> inputClasses) {
		String query = "Einzelsport "
				+ createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

		return fetchedClasses;
	}

	/**
	 * Gibt die Teamsportarten zurück die sowohl in der Onto als auch in
	 * inputClasses enthalten sind.
	 * 
	 * @param inputClasses
	 * @return
	 */
	public static Set<OWLClass> queryTeamsport(Set<OWLClass> inputClasses) {
		String query = "Teamsport "
				+ createAccepableClassesCondition_Ontology(inputClasses);
		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

		return fetchedClasses;
	}

	/**
	 * Gibt die Sportarten zurück die in inputClasses stehen und höchsten
	 * maxPrice kosten.
	 * 
	 * @param inputClasses
	 * @param maxPrice
	 * @return
	 */
	public static Set<OWLClass> queryPrice(Set<OWLClass> inputClasses,
			double maxPrice) {
		String query = "SELECT DISTINCT Sportangebot.name FROM "
				+ " Sportangebot, Kurs WHERE "
				+ " Sportangebot.idSportangebot = Kurs.idSportangebot "
				+ "AND kosten <= " + maxPrice
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
	 * Filtert die Sportarten aus sportClasses raus, die sich nicht in
	 * acceptableClasses befinden. </br> Diese Methode wird für Queries auf der
	 * Datenbank benutzt um eine Menge aus OWLClass zu erstellen.
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

		Set<OWLClass> acceptableClassesSet = new HashSet<OWLClass>();
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

	/**
	 * Condition Anhang an die DB-Query </br> Select * from xy where ... </br>
	 * AND Sportangebot.name = 'Basketball'</br> OR Sportangebot.name =
	 * 'Volleyball'</br> ...
	 * 
	 * @param inputClasses
	 * @return
	 */
	private static String createAccepableClassesCondition_DB(
			Set<OWLClass> inputClasses) {
		StringBuilder condition = new StringBuilder();
		boolean first = true;
		for (OWLClass owlClass : inputClasses) {
			if (first) {
				condition.append(" AND ");
				first = false;
			} else {
				condition.append(" OR ");
			}
			condition.append(" Sportangebot.name = '");
			condition.append(shortFormProvider.getShortForm(owlClass));
			condition.append("'" + System.lineSeparator());
		}

		return condition.toString();
	}

	/**
	 * Condition Anhang an die Onto-Query </br> 
	 * bla and (Basketball or Volleyball or ... )
	 * 
	 * @param inputClasses
	 * @return
	 */
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
