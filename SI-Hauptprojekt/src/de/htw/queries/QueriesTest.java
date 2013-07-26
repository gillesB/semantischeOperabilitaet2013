package de.htw.queries;

import java.util.Map;

import business.model.Sportangebot;
import business.model.ontology.KoerperlicheEinschraenkungen;

public class QueriesTest {

	public static void main(String[] args) {
		// Die Idee ist dass man sich alle Sportarten holt und diese Menge wird
		// dann nach beliebigen Kriterien gefiltert
		System.out.println("Sport\n");
		Map<String, Sportangebot> sportClasses = Queries.querySport();
		printSportangebote(sportClasses);

		//Einzelsportarten werden rausgefiltert.
		//Es bleiben nur Teamsportarten übrig
		System.out.println("Teamsp.\n");
		sportClasses = Queries.queryTeamsport(sportClasses);
		printSportangebote(sportClasses);

		//Sportarten die mehr als 0 Euro Kosten werden rausgefiltert
		System.out.println("Kosten 0.\n");
		sportClasses = Queries.queryPrice(sportClasses, 0.0);
		printSportangebote(sportClasses);
		
		System.out.println("Keine Höhenangst\n");
		sportClasses = Queries.queryFilterKörperlicheEinschraenkungen(sportClasses, KoerperlicheEinschraenkungen.HOEHENANGST);
		printSportangebote(sportClasses);
		
		System.out.println("Innen\n");
		sportClasses = Queries.queryIndoor(sportClasses, true);
		printSportangebote(sportClasses);

	}

	private static void printSportangebote(Map<String, Sportangebot> sportangebote) {
		if (sportangebote.isEmpty()) {
			System.out.println("Nix mehr über :(");
		} else {
			for (String sportangebot : sportangebote.keySet()) {
				System.out.println(sportangebot);
			}
		}
		System.out.println();
		System.out.println();
	}

}
