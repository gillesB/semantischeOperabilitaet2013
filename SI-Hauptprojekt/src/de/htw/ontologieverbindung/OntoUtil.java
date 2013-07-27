package de.htw.ontologieverbindung;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class OntoUtil {

	private static ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	
	public static String getShortForm(OWLClass owlClass){
		return shortFormProvider.getShortForm(owlClass);
	}
}
