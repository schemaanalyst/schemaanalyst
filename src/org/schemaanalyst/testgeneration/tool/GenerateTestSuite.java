package org.schemaanalyst.testgeneration.tool;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

/**
 * Created by phil on 21/01/2014.
 */

@RequiredParameters("schema dbms criterion datagenerator")
public class GenerateTestSuite extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The name of the coverage criterion to use.")
    protected String criterion;

    @Parameter("The name of the data generator to use.")
    protected String datagenerator;

    @Parameter("The name of the DBMS to use.")
    protected String dbms;

    @Parameter("The class name of the generated test suite (if not specified class name will be 'TestSchema' where schema is the name of the schema under test")
    protected String classname = "";

    @Parameter("The package into which the built test suite is placed")
    protected String packagename = "generatedtest";

    @Override
    protected void task() {

        // instantiate objects for parameters
        Schema schemaObject = instantiateSchema();
        DBMS dbmsObject = DBMSFactory.instantiate(dbms);
        TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schemaObject, dbmsObject).generateRequirements();
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, -0L, 100000, schemaObject);

        // filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                testRequirements,
                dbmsObject.getValueFactory(),
                dataGeneratorObject);
        TestSuite testSuite = testSuiteGenerator.generate();

        // print some stats
        TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();
        System.out.println("Test requirements covered: " + report.getNumTestRequirementsCovered() + "/" + report.getNumTestRequirementsAttempted());
        System.out.println("Coverage: " + report.coverage() + "%");
        System.out.println("Num Evaluations (test cases only): " + report.getNumDataEvaluations(true));
        System.out.println("Num Evaluations (all): " + report.getNumEvaluations(false));

        // failed initial table data generation attempts
        if (report.getInitialTableDataGenerationAttemptsFailed() > 0) {
            System.out.println("Initial table data generation attempts that failed:");
            int i = 1;
            for (Table table : report.getFailedInitialTableDataGenerationAttempts()) {
                System.out.println(i + ") " + table);
                i ++;
            }
        }

        if (report.getNumTestRequirementsFailed() > 0) {
            System.out.println("Failed test requirements:");
            int i = 1;
            for (TestRequirement testRequirement : report.getFailedTestRequirements()) {
                System.out.println(i + ") " + testRequirement);
                System.out.println((report.getDataGenerationResult(testRequirement).getData()));
                i ++;
            }
        }

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schemaObject,
                dbmsObject,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(testSuite);

        // check the results
        for (TestCase testCase : testSuite.getTestCases()) {
            Boolean result = testCase.getTestRequirement().getResult();
            Boolean dbmsResult = testCase.getLastDBMSResult();
            if (result != null && result != dbmsResult) {
                TestRequirement testRequirement = testCase.getTestRequirement();
                System.out.println("WARNING--test requirement result (" + result + ") differs from DBMS result (" + dbmsResult + "):");
                System.out.println(testRequirement);
            }

            // check the real test case executor against the virtual one
            VirtualTestCaseExecutor virtualTestCaseExecutor = new VirtualTestCaseExecutor(schemaObject, dbmsObject);
            List<Boolean> virtualResults = virtualTestCaseExecutor.executeTestCaseBoolean(testCase);
            List<Boolean> realResults = new ArrayList<>();
            for (int i=0; i < testCase.getState().getNumRows(); i++) {
                realResults.add(true);
            }
            realResults.addAll(testCase.getDBMSResults());

            if (virtualResults.size() != realResults.size()) {
                System.out.println("WARNING--Number of virtual results (" + virtualResults.size() +
                        ") does not match number of real results (" + realResults.size() + ")");
            }

            for (int i=0; i < Math.min(virtualResults.size(), realResults.size()); i++) {
                if (virtualResults.get(i) != realResults.get(i)) {
                    System.out.println("WARNING--real result differs from virtual result at INSERT statement " + i);
                }
            }
        }

        // write JUnit test suite to file
        if (classname.equals("")) {
            classname = "Test" + schemaObject.getName();
        }

        String javaCode = new TestSuiteJavaWriter(schemaObject, dbmsObject, testSuite, true)
                .writeTestSuite(packagename, classname);

        File javaFile = new File(locationsConfiguration.getSrcDir()
                + "/" + packagename + "/" + classname + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
            System.out.println("JUnit test suite written to " + javaFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Schema instantiateSchema() {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new GenerateTestSuite().run(args);
    }
}
