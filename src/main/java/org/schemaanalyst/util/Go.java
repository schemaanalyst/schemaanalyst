/**
 * SchemaAnalyst Command-line Front-end
 * @author Cody Kinneer
 */

package org.schemaanalyst.util;

import com.beust.jcommander.JCommander;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.configuration.*;
import org.schemaanalyst.data.generation.*;
import org.schemaanalyst.dbms.*;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.reduction.ReductionFactory;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.*;
import org.schemaanalyst.mutation.analysis.executor.MutationAnalysis;

import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;

import java.io.*;
import java.util.*;

public class Go {

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

		String reduction = jcp.reduce;

		long randomseed = jcp.randomseed;

		boolean fullreduce = jcp.fullreduce;

		boolean printTR = jcp.printTR;
		
		String reducewith = jcp.reducewith;

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
					"--useTransactions=" + mc.transactions, "--reduce=" + mc.reduce,
					"--reducePredicates=" + mc.reducePredicates,
					"--fullreduce=" + mc.fullreduce,
					"--reducewith=" + mc.reducewith};

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
			DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, randomseed, 100000,
					schemaObject);

			// filter and reduce test requirements
			testRequirements.filterInfeasible();
			testRequirements.reduce();

			// generate the test suite
			TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(schemaObject, testRequirements,
					dbmsObject.getValueFactory(), dataGeneratorObject, fullreduce || reduction.equals("reduceTC"));
			TestSuite testSuite = testSuiteGenerator.generate();

			// if desired, write the INSERTs to a file for inspection
			if (sql) {

				File file = new File(packagename + "/" + schema + ".sql");

				writeInserts(testSuite, dbmsObject, file, schemaObject);
				
			}

			// print some stats
			TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();
			System.out.println("Test requirements covered: " + report.getNumTestRequirementsCovered() + "/"
					+ report.getNumTestRequirementsAttempted());
			System.out.println("Coverage: " + report.coverage() + "%");
			System.out.println("Num Evaluations (test cases only): " + report.getNumDataEvaluations(true));
			System.out.println("Num Evaluations (all): " + report.getNumEvaluations(false));

			if (fullreduce || reduction.equals("reduceTC")) {
				System.out.println("Original Number of INSERTs: " + testSuite.getGeneratedInserts());
				System.out.println("Reduced Number of INSERTs: " + testSuite.getReducedInsertsCount());
			}
			int numberOfTestCases = testSuite.getTestCases().size();
			if (reduction.equals("eqltc") || reduction.equals("eqltr") || fullreduce) {
				System.out.println("=================================Pre-Reduction=================================");
				System.out.println("Number of Test Cases: " + testSuite.getTestCases().size());
				System.out.println("=================================Post-Reduction================================");
				
				if (fullreduce) {
					int totalFulfilledRequirements = testRequirements.size() - report.getNumTestRequirementsFailed();
					ReductionFactory reduce = new ReductionFactory();
					testSuite = reduce.reduceTestSuite(testSuite, testRequirements, schemaObject, report.getFailedTestRequirements(), 
							totalFulfilledRequirements, randomseed, reducewith);			
				}

				int numberOfTCsAfterReduction = testSuite.getTestCases().size();
				double ratio = (double) numberOfTCsAfterReduction / numberOfTestCases;
				double reductionPrecentage = (1 - ratio) * 100;
				System.out.println("Reduction Precentage = " + String.format("%.2f", reductionPrecentage) + "%");
				System.out.println("==================================================================================");
				System.out.println("Final Number of INSERTs After test suite reduction: " + testSuite.countNumberOfInserts());
			}
			
			int nullCounter = 0;
			for (TestCase tc : testSuite.getTestCases()) {
				for (Cell c : tc.getState().getCells()) {
					if (c.isNull())
						nullCounter++;
				}
				for (Cell c : tc.getData().getCells()) {
					if (c.isNull())
						nullCounter++;
				}
			}
			System.out.println("Number of Nulls = " + nullCounter);

			if (printTR) {
				printTRs(testRequirements);
			}

			// failed initial table data generation attempts
			if (report.getInitialTableDataGenerationAttemptsFailed() > 0) {
				System.out.println("Initial table data generation attempts that failed:");
				int i = 1;
				for (Table table : report.getFailedInitialTableDataGenerationAttempts()) {
					System.out.println(i + ") " + table);
					i++;
				}
			}

			if (report.getNumTestRequirementsFailed() > 0) {
				System.out.println("Failed test requirements:");
				int i = 1;
				for (TestRequirement testRequirement : report.getFailedTestRequirements()) {
					System.out.println(i + ") " + testRequirement);
					System.out.println((report.getDataGenerationResult(testRequirement).getData()));
					i++;
				}
			}

			// execute each test case to see what the DBMS result is for each
			// row generated
			// (accept / row)
			TestCaseExecutor executor = new TestCaseExecutor(schemaObject, dbmsObject, new DatabaseConfiguration(),
					new LocationsConfiguration());
			executor.execute(testSuite);

			// check the results
			for (TestCase testCase : testSuite.getTestCases()) {
				Boolean result = testCase.getTestRequirement().getResult();
				Boolean dbmsResult = testCase.getLastDBMSResult();
				if (result != null && result != dbmsResult) {
					TestRequirement testRequirement = testCase.getTestRequirement();
					System.out.println("WARNING--test requirement result (" + result + ") differs from DBMS result ("
							+ dbmsResult + "):");
					System.out.println(testRequirement);
				}

				// check the real test case executor against the virtual one
				VirtualTestCaseExecutor virtualTestCaseExecutor = new VirtualTestCaseExecutor(schemaObject, dbmsObject);
				List<Boolean> virtualResults = virtualTestCaseExecutor.executeTestCaseBoolean(testCase);
				List<Boolean> realResults = new ArrayList<>();
				for (int i = 0; i < testCase.getState().getNumRows(); i++) {
					realResults.add(true);
				}
				realResults.addAll(testCase.getDBMSResults());

				if (virtualResults.size() != realResults.size()) {
					System.out.println("WARNING--Number of virtual results (" + virtualResults.size()
							+ ") does not match number of real results (" + realResults.size() + ")");
				}

				for (int i = 0; i < Math.min(virtualResults.size(), realResults.size()); i++) {
					if (virtualResults.get(i) != realResults.get(i)) {
						System.out.println("WARNING--real result differs from virtual result at INSERT statement " + i);
					}
				}
			}

			// write JUnit test suite to file
			if (classname.equals("")) {
				classname = "Test" + schemaObject.getName();
			}
			classname = classname.replace(".", "");

			String javaCode = new TestSuiteJavaWriter(schemaObject, dbmsObject, testSuite, true)
					.writeTestSuite(packagename, classname);

			File javaFile = new File(packagename + "/" + classname + ".java");

			// make the package directory if it does not exist
			javaFile.getParentFile().mkdirs();

			try (PrintWriter fileOut = new PrintWriter(javaFile)) {
				fileOut.println(javaCode);
				System.out.println("JUnit test suite written to " + javaFile);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			
			//writeTestSuiteSQLfile(packagename, testSuite, dbmsObject, schemaObject, fullreduce);

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

	private static void writeInserts(TestSuite testSuite, DBMS dbmsObject, File target, Schema schemaObject) {
		SQLWriter writer = dbmsObject.getSQLWriter();
		List<TestCase> testCases = testSuite.getTestCases();
		try {
			BufferedWriter bufFile = new BufferedWriter(new FileWriter(target));
			for (int i = 0; i < testCases.size(); i++) {
				TestCase testCase = testCases.get(i);
				printInserts(writer, schemaObject, testCase.getState(), bufFile);
				printInserts(writer, schemaObject, testCase.getData(), bufFile);
			}
			bufFile.flush();
			bufFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void printTRs(TestRequirements testRequirements) {
		int total = testRequirements.size();

		testRequirements.filterInfeasible();

		int totalMinusInfeasible = testRequirements.size();

		testRequirements.reduce();

		int totalMinusDuplicatesMinusInfeasible = testRequirements.size();

		for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {
			boolean infeasible = testRequirement.getPredicate().reduce().isTriviallyInfeasible();
			System.out.println(testRequirement.toString(true) + "\n" + (infeasible ? "(Infeasible)\n" : ""));
		}

		System.out.println("Total number of test requirements: " + total);
		System.out.println("Minus infeasible:                  " + totalMinusInfeasible);
		System.out.println("Minus duplicates and infeasible:   " + totalMinusDuplicatesMinusInfeasible);
	}

	private static void printInserts(SQLWriter writer, Schema schema, Data data, BufferedWriter bufWriter)
			throws IOException {
		List<String> stmts = writer.writeInsertStatements(schema, data);
		for (String stmt : stmts) {
			System.out.println(stmt);
			bufWriter.write(stmt);
		}
	}
	
	private static void writeTestSuiteSQLfile(String packageDir, TestSuite testSuite, DBMS dbmsObject, Schema schemaObject, boolean fullreduce) {
		String dir;
		if (fullreduce) {
			dir = packageDir + "/TestSuite-" + schemaObject.getName() + "-" + dbmsObject.getName() + "-Reduced";
		} else {
			dir = packageDir + "/TestSuite-" + schemaObject.getName() + "-" + dbmsObject.getName() + "-Orginial";
		}
		File theDir = new File(dir);
		
		if (theDir.exists()) {
			deleteDirectory(theDir);
		}
		
		if (!theDir.exists()) {
		    System.err.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.err.println(dir + " created");  
		    }
		}
		
		SQLWriter writer = dbmsObject.getSQLWriter();
		List<TestCase> testCases = testSuite.getTestCases();
		try {
			
			// Creating Schema SQL
			File schemafile = new File(dir + "/schema-" + schemaObject + ".sql");
			BufferedWriter bufFile = new BufferedWriter(new FileWriter(schemafile));
			for (String createTable : writer.writeCreateTableStatements(schemaObject)) {
				bufFile.write(createTable + ";" + System.lineSeparator());
			}
			bufFile.flush();
			bufFile.close();
			// Write Test Case
			for (int i = 0; i < testCases.size(); i++) {
				bufFile = new BufferedWriter(new FileWriter(dir + "/tc" + i + ".sql"));
				TestCase testCase = testCases.get(i);
				String assertion = "";
				if (testCase.getTestRequirement().getResult()) {
					assertion = "TRUE";
				} else {
					assertion = "FALSE";
				}
				bufFile.write(assertion + System.lineSeparator());
				//printInserts(writer, schemaObject, testCase.getState(), bufFile);
				// State
				List<String> stmts = writer.writeInsertStatements(schemaObject, testCase.getState());
				for (String stmt : stmts) {
					bufFile.write(stmt);
				}
				
				//printInserts(writer, schemaObject, testCase.getData(), bufFile);
				// Data
				stmts = writer.writeInsertStatements(schemaObject, testCase.getData());
				for (String stmt : stmts) {
					bufFile.write(stmt);
				}
				
				bufFile.flush();
				bufFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	private static boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}

}