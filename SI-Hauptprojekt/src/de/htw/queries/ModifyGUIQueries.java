package de.htw.queries;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;

import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Personen;
import de.htw.ontologieverbindung.OntoUtil;
import de.htw.ontologieverbindung.OntolgyConnection;

public class ModifyGUIQueries {
    
    public static String queryPrice() {
        return " will_zahlen only True ";
    }
    
    public static String queryKoerperlicheEinschraenkungen() {
        return " hat_Einschraenkung some KoerperlicheEinschraenkung ";
    }	
}
