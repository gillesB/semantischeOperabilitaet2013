package de.htw.ontologieverbindung;

/*
 * Parts of this file were taken from the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class OntolgyConnection {

	private static final String ONTOLOGY_PATHNAME = "/resources/de/htw/owl/sport.owl";

	private DLQueryEngine queryEngine;

	private static OntolgyConnection instance;

	private OntolgyConnection() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		InputStream ontologyStream = getClass().getResourceAsStream(
				ONTOLOGY_PATHNAME);
		OWLOntology ontology = manager
				.loadOntologyFromOntologyDocument(ontologyStream);
		try {
			ontologyStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OWLReasoner reasoner = createReasoner(ontology);
		ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
		queryEngine = new DLQueryEngine(reasoner, shortFormProvider);

	}

	public static OntolgyConnection getInstance() {
		if (instance == null) {
			synchronized (OntolgyConnection.class) {
				if (instance == null) {
					try {
						instance = new OntolgyConnection();
					} catch (OWLOntologyCreationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		}
		return instance;
	}

	public Set<OWLClass> doQuery(String query){
		query = query.trim();
		Set<OWLClass> subClasses = new HashSet<OWLClass>();
		try {
			//gets also descendant classes
			subClasses = queryEngine.getSubClasses(query, false);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return subClasses;
	}

	public Set<OWLClass> doQuery_superClasses_equivalentClasses_subClasses(
			String query) throws ParserException {
		Set<OWLClass> superClasses = queryEngine.getSuperClasses(query, true);
		Set<OWLClass> equivalentClasses = queryEngine
				.getEquivalentClasses(query);
		Set<OWLClass> subClasses = queryEngine.getSubClasses(query, true);

		Set<OWLClass> resultClasses = superClasses;
		resultClasses.addAll(subClasses);
		resultClasses.addAll(equivalentClasses);

		return resultClasses;
	}

	private static OWLReasoner createReasoner(OWLOntology rootOntology) {
		// We need to create an instance of OWLReasoner. An OWLReasoner provides
		// the basic query functionality that we need, for example the ability
		// obtain the subclasses of a class etc. To do this we use a reasoner
		// factory.
		// Create a reasoner factory.

		// old code
		// OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		// return reasonerFactory.createReasoner(rootOntology);

		OWLReasoner reasoner = new Reasoner.ReasonerFactory()
				.createReasoner(rootOntology);
		return reasoner;
	}

	private class DLQueryEngine {
		private OWLReasoner reasoner;
		private DLQueryParser parser;

		/**
		 * Constructs a DLQueryEngine. This will answer "DL queries" using the
		 * specified reasoner. A short form provider specifies how entities are
		 * rendered.
		 * 
		 * @param reasoner
		 *            The reasoner to be used for answering the queries.
		 * @param shortFormProvider
		 *            A short form provider.
		 */
		public DLQueryEngine(OWLReasoner reasoner,
				ShortFormProvider shortFormProvider) {
			this.reasoner = reasoner;
			OWLOntology rootOntology = reasoner.getRootOntology();
			parser = new DLQueryParser(rootOntology, shortFormProvider);
		}

		/**
		 * Gets the superclasses of a class expression parsed from a string.
		 * 
		 * @param classExpressionString
		 *            The string from which the class expression will be parsed.
		 * @param direct
		 *            Specifies whether direct superclasses should be returned
		 *            or not.
		 * @return The superclasses of the specified class expression
		 * @throws ParserException
		 *             If there was a problem parsing the class expression.
		 */
		public Set<OWLClass> getSuperClasses(String classExpressionString,
				boolean direct) throws ParserException {
			if (classExpressionString.trim().length() == 0) {
				return Collections.emptySet();
			}
			OWLClassExpression classExpression = parser
					.parseClassExpression(classExpressionString);
			NodeSet<OWLClass> superClasses = reasoner.getSuperClasses(
					classExpression, direct);
			return superClasses.getFlattened();
		}

		/**
		 * Gets the equivalent classes of a class expression parsed from a
		 * string.
		 * 
		 * @param classExpressionString
		 *            The string from which the class expression will be parsed.
		 * @return The equivalent classes of the specified class expression
		 * @throws ParserException
		 *             If there was a problem parsing the class expression.
		 */
		public Set<OWLClass> getEquivalentClasses(String classExpressionString)
				throws ParserException {
			if (classExpressionString.trim().length() == 0) {
				return Collections.emptySet();
			}
			OWLClassExpression classExpression = parser
					.parseClassExpression(classExpressionString);
			Node<OWLClass> equivalentClasses = reasoner
					.getEquivalentClasses(classExpression);
			Set<OWLClass> result;
			if (classExpression.isAnonymous()) {
				result = equivalentClasses.getEntities();
			} else {
				result = equivalentClasses.getEntitiesMinus(classExpression
						.asOWLClass());
			}
			return result;
		}

		/**
		 * Gets the subclasses of a class expression parsed from a string.
		 * 
		 * @param classExpressionString
		 *            The string from which the class expression will be parsed.
		 * @param direct
		 *            Specifies whether direct subclasses should be returned or
		 *            not.
		 * @return The subclasses of the specified class expression
		 * @throws ParserException
		 *             If there was a problem parsing the class expression.
		 */
		public Set<OWLClass> getSubClasses(String classExpressionString,
				boolean direct) throws ParserException {
			if (classExpressionString.trim().length() == 0) {
				return Collections.emptySet();
			}
			OWLClassExpression classExpression = parser
					.parseClassExpression(classExpressionString);
			NodeSet<OWLClass> subClasses = reasoner.getSubClasses(
					classExpression, direct);
			return subClasses.getFlattened();
		}

		/**
		 * Gets the instances of a class expression parsed from a string.
		 * 
		 * @param classExpressionString
		 *            The string from which the class expression will be parsed.
		 * @param direct
		 *            Specifies whether direct instances should be returned or
		 *            not.
		 * @return The instances of the specified class expression
		 * @throws ParserException
		 *             If there was a problem parsing the class expression.
		 */
		public Set<OWLNamedIndividual> getInstances(
				String classExpressionString, boolean direct)
				throws ParserException {
			if (classExpressionString.trim().length() == 0) {
				return Collections.emptySet();
			}
			OWLClassExpression classExpression = parser
					.parseClassExpression(classExpressionString);
			NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(
					classExpression, direct);
			return individuals.getFlattened();
		}
	}

	private class DLQueryParser {
		private OWLOntology rootOntology;
		private BidirectionalShortFormProvider bidiShortFormProvider;

		/**
		 * Constructs a DLQueryParser using the specified ontology and short
		 * form provider to map entity IRIs to short names.
		 * 
		 * @param rootOntology
		 *            The root ontology. This essentially provides the domain
		 *            vocabulary for the query.
		 * @param shortFormProvider
		 *            A short form provider to be used for mapping back and
		 *            forth between entities and their short names (renderings).
		 */
		public DLQueryParser(OWLOntology rootOntology,
				ShortFormProvider shortFormProvider) {
			this.rootOntology = rootOntology;
			OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
			Set<OWLOntology> importsClosure = rootOntology.getImportsClosure();
			// Create a bidirectional short form provider to do the actual
			// mapping.
			// It will generate names using the input
			// short form provider.
			bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(
					manager, importsClosure, shortFormProvider);
		}

		/**
		 * Parses a class expression string to obtain a class expression.
		 * 
		 * @param classExpressionString
		 *            The class expression string
		 * @return The corresponding class expression
		 * @throws ParserException
		 *             if the class expression string is malformed or contains
		 *             unknown entity names.
		 */
		public OWLClassExpression parseClassExpression(
				String classExpressionString) throws ParserException {
			OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager()
					.getOWLDataFactory();
			// Set up the real parser
			ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
					dataFactory, classExpressionString);
			parser.setDefaultOntology(rootOntology);
			// Specify an entity checker that wil be used to check a class
			// expression contains the correct names.
			OWLEntityChecker entityChecker = new ShortFormEntityChecker(
					bidiShortFormProvider);
			parser.setOWLEntityChecker(entityChecker);
			// Do the actual parsing
			return parser.parseClassExpression();
		}
	}

}
