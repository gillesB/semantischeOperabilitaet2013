package de.htw.queries;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 23.07.13
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class DLQueryParser {
    private OWLOntology                    rootOntology;
    private BidirectionalShortFormProvider shortFormProvider;

    public DLQueryParser(OWLOntology rootOntology, ShortFormProvider shortFormProvider) {
        this.rootOntology = rootOntology;
        OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
        Set<OWLOntology> importClosure = this.rootOntology.getImportsClosure();
        this.shortFormProvider = new BidirectionalShortFormProviderAdapter(manager, importClosure, shortFormProvider);
    }

    public OWLClassExpression parseClassExpression(String classExpressionString) throws ParserException {
        OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager().getOWLDataFactory();
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory,
                                                                                     classExpressionString);
        parser.setDefaultOntology(rootOntology);
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(entityChecker);
        return parser.parseClassExpression();
    }
}
