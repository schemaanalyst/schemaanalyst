package org.schemaanalyst.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.mutation.analysis.executor.MutationAnalysis;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.StateGenerator;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestCaseExecutor;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerationReport;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.TestSuiteJavaWriter;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import com.beust.jcommander.JCommander;

public class ConstraintPrinter {

	public static void main(String[] args) {

		JCommanderParams jcp = new JCommanderParams();
		JCommander cmd = new JCommander(jcp);

		MutationCommand mc = new MutationCommand();
		GenerationCommand gc = new GenerationCommand();

		cmd.addCommand("generation", gc);
		cmd.addCommand("mutation", mc);
		cmd.parse(args);
		
		String command = cmd.getParsedCommand();

		// set arguments
		String schema = jcp.schema;
		String criterion = jcp.criterion;
		String datagenerator = jcp.generator;
		String dbms = jcp.dbms;
		String classname;

		if (jcp.help) {
			cmd.usage();
			return;
		}

		if (command != null && command.equals("mutation")) {

			/* ArrayList<String> pargs = new ArrayList<String>(); */

			String[] pargs = new String[] { schema, "--criterion=" + criterion, "--dataGenerator=" + datagenerator,
					"--maxevaluations=" + mc.maxEvaluations, "--randomseed=" + mc.seed,
					"--mutationPipeline=" + mc.pipeline, "--technique=" + mc.technique,
					"--useTransactions=" + mc.transactions };

			MutationAnalysis.main(pargs);

			return;

		} else if (command != null && command.equals("generation")) {

			if (gc.testSuite == "TestSchema")
				classname = "Test" + schema;
			else
				classname = gc.testSuite;

			String packagename = gc.testSuitePackage;

			boolean sql = gc.sql;

			// Copied code from GenerateTestSuite and PrintTestSuite

			// instantiate objects for parameters
			Schema schemaObject = instantiateSchema(schema);
			DBMS dbmsObject = DBMSFactory.instantiate(dbms);
			TestRequirements testRequirements = CoverageCriterionFactory
					.instantiateSchemaCriterion(criterion, schemaObject, dbmsObject).generateRequirements();
			
			// Generator initializing
			DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, -0L, 100000,
					schemaObject);

			// filter and reduce test requirements
			testRequirements.filterInfeasible();
			testRequirements.reduce();

			// generate the test suite
			StateGenerator testSuiteGenerator = new StateGenerator(schemaObject, testRequirements,
					dbmsObject.getValueFactory(), dataGeneratorObject);
			//TestSuite testSuite = testSuiteGenerator.generate();
			TestSuite testSuite = testSuiteGenerator.generateStateData();
			
			int counter = 0;
			for (TestCase tc : testSuite.getTestCases()) {
				System.out.println("Test Case Number:" + counter);
				System.out.println("Test Requirement: ");
				for (TestRequirementDescriptor tr : tc.getTestRequirement().getDescriptors()) {
					System.out.println("\t" + tr);
				}
				System.out.println("Predicates: " + tc.getTestRequirement().getPredicate());
				System.out.println("Generated State Data: " + tc.getState());
				System.out.println("===========================================================");
				System.out.println();
				
				counter++;
			}
			
			
			//String javaCode = new TestSuiteJavaWriter(schemaObject, dbmsObject, testSuite, true)
			//		.writeTestSuite(packagename, classname);

		} else {
			cmd.usage();
		}

	}

	private static Schema instantiateSchema(String schema) {
		try {
			return (Schema) Class.forName(schema).newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

}
