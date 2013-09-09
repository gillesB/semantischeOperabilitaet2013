package de.htw.queries;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Personen;
import de.htw.ontologieverbindung.OntoUtil;
import de.htw.ontologieverbindung.OntolgyConnection;

public class ModifyGUIQueries {

    private static OntolgyConnection ontolgy   = OntolgyConnection.getInstance();
	
    public static Set<Personen> queryPrice(
    		Set<Personen> personen) {
        String query = "Person and will_zahlen only True";
        Set<OWLClass> fetchedClasses = ontolgy.doQuery(query);

        return mapOWLClassesToPersons(personen, fetchedClasses);
    }
    
    public static Set<Personen> queryKoerperlicheEinschraenkungen(
    		Set<Personen> personen) {
        Set<OWLClass> fetchedClasses = ontolgy.doQuery("Person and hat_Einschraenkung some KoerperlicheEinschraenkung");
        return mapOWLClassesToPersons(personen, fetchedClasses);
    }
    
    private static Set<Personen> mapOWLClassesToPersons(Set<Personen> personen, Set<OWLClass> owlClasses){
    
    	for(OWLClass owlClass : owlClasses){
    		String shortForm = OntoUtil.getShortForm(owlClass);
    		if(shortForm.equals(Personen.EINGESCHRAENKT.getName())){
    			personen.add(Personen.EINGESCHRAENKT);
    		} else if(shortForm.equals(Personen.ZAHLEND.getName())){
    			personen.add(Personen.ZAHLEND);
    		}
    	}
    	
    	return personen;
    	
    }
	
}
