package de.htw.queries;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class QueriesTest {

	public static void main(String[] args) {
		// Die Idee ist dass man sich alle Sportarten holt und diese Menge wird
		// dann nach beliebigen Kriterien gefiltert
		System.out.println("Sport\n");
		Set<OWLClass> sportClasses = Queries.querySport();
		printSportClasses(sportClasses);

		//Einzelsportarten werden rausgefiltert.
		//Es bleiben nur Teamsportarten übrig
		System.out.println("Teamsp.\n");
		sportClasses = Queries.queryTeamsport(sportClasses);
		printSportClasses(sportClasses);

		//Sportarten die mehr als 0 Euro Kosten werden rausgefiltert
		System.out.println("Kosten 0.\n");
		sportClasses = Queries.queryPrice(sportClasses, 0.0);
		printSportClasses(sportClasses);

	}

	private static void printSportClasses(Set<OWLClass> classes) {
		// print shortForm of OWLClass
		ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
		if (classes.isEmpty()) {
			System.out.println("Nix mehr über :(");
		} else {
			for (OWLClass owlClass : classes) {
				System.out.println(shortFormProvider.getShortForm(owlClass));
			}
		}
		System.out.println();
		System.out.println();
	}

}