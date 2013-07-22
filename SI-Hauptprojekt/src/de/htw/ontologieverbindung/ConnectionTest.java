package de.htw.ontologieverbindung;

import java.util.Set;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class ConnectionTest {

	public static void main(String[] args) throws OWLOntologyCreationException, ParserException {
		OntolgyConnection ontolgyConnection = OntolgyConnection.getInstance();
		Set<OWLClass> classes = ontolgyConnection.doQuery("Einzelsport and not(ungeeignetBei some KoerperlicheEinschraenkung) ");
		
		//print shortForm of OWLClass
		ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
		for(OWLClass owlClass : classes){
			System.out.println(owlClass + " + short form: " +shortFormProvider.getShortForm(owlClass));
		}
	}

}
