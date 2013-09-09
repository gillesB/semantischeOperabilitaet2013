package de.htw.queries;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

import de.htw.ontologieverbindung.OntoUtil;
import de.htw.ontologieverbindung.OntolgyConnection;
import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Personen;

public class ModifyGUIQueryBuilder {

	private boolean wantsToPay = false;
	private KoerperlicheEinschraenkungen[] einschraenkungen = new KoerperlicheEinschraenkungen[0];

	public Set<Personen> execute() {
		OntolgyConnection ontolgy = OntolgyConnection.getInstance();
		
		if(!wantsToPay && !(einschraenkungen.length > 0)){
			return new HashSet<Personen>();
		}	
		
		StringBuilder query = new StringBuilder("Person and (");
		if (wantsToPay) {
			query.append(ModifyGUIQueries.queryPrice());
		}

		if(wantsToPay && einschraenkungen.length > 0){
			query.append(" or ");
		}		

		if (einschraenkungen.length > 0) {
			query.append(ModifyGUIQueries.queryKoerperlicheEinschraenkungen());
		}

		query.append(" ) ");

		Set<OWLClass> fetchedClasses = ontolgy.doQuery(query.toString());

		return mapOWLClassesToPersons(fetchedClasses);
	}

	private static Set<Personen> mapOWLClassesToPersons(Set<OWLClass> owlClasses) {
		Set<Personen> personen = new HashSet<Personen>();
		for (OWLClass owlClass : owlClasses) {
			String shortForm = OntoUtil.getShortForm(owlClass);
			if (shortForm.equals(Personen.EINGESCHRAENKT.getName())) {
				personen.add(Personen.EINGESCHRAENKT);
			} else if (shortForm.equals(Personen.ZAHLEND.getName())) {
				personen.add(Personen.ZAHLEND);
			}
		}

		return personen;

	}

	public boolean isWantsToPay() {
		return wantsToPay;
	}

	public void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}

	public KoerperlicheEinschraenkungen[] getEinschraenkungen() {
		return einschraenkungen;
	}

	public void setEinschraenkungen(
			KoerperlicheEinschraenkungen[] einschraenkungen) {
		this.einschraenkungen = einschraenkungen;
	}

}
