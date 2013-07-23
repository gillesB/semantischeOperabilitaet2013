package de.htw.queries;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 23.07.13
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class DLQuery {
    private OWLOntologyManager manager;
    private OWLOntology        ontology;
    private OWLReasoner        reasoner;
    private DLQueryPrinter     dlQueryPrinter;

    public DLQuery(String path) {
        try {
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(new File(path));
            Reasoner.ReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
            reasoner = reasonerFactory.createReasoner(ontology);
            ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
            dlQueryPrinter = new DLQueryPrinter(new DLQueryEngine(reasoner, shortFormProvider),
                                                shortFormProvider);
        }
        catch (OWLOntologyCreationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            DLQuery dlQuery = new DLQuery("D:\\HTW\\Master\\Semantische " +
                                                  "Interoperabilität\\semantischeOperabilitaet2013\\SI-Hauptprojekt\\src\\resources\\de\\htw\\owl\\sport.owl");
            doQueryLoop(dlQuery.dlQueryPrinter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doQueryLoop(DLQueryPrinter dlQueryPrinter) throws IOException {
        while (true) {
            // Prompt the user to enter a class expression
            System.out
                    .println(
                            "Please type a class expression in Manchester Syntax and press Enter (or press x to exit):");
            System.out.println("");
            String classExpression = readInput();
            // Check for exit condition
            if (classExpression.equalsIgnoreCase("x")) {
                break;
            }
            dlQueryPrinter.askQuery(classExpression.trim());
            System.out.println();
            System.out.println();
        }
    }

    private static String readInput() throws IOException {
        InputStream is = System.in;
        InputStreamReader reader;
        reader = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(reader);
        return br.readLine();
    }

    public DLQueryPrinter getPrinter() {
        return dlQueryPrinter;
    }
}
