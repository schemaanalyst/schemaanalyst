package org.schemaanalyst.testgeneration.tool;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

/**
 * Created by phil on 21/01/2014.
 */

@RequiredParameters("schema criterion datagenerator dbms")
public class GenerateSchemaCoverage extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The name of the coverage criterion to use.")
    protected String criterion;

    @Parameter("The name of the data generator to use.")
    protected String datagenerator;

    @Parameter("The name of the DBMS to use.")
    protected String dbms;

    @Parameter("Whether a test suite should be written or not.")
    protected boolean buildTestSuite = false;

    @Override
    protected void task() {

        // instantiate objects for parameters
        Schema schemaObject = instantiateSchema();
        CoverageCriterion criterionObject = CoverageCriterionFactory.instantiate(criterion);
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, 0L, 10000, schemaObject);
        DBMS dbmsObject = DBMSFactory.instantiate(dbms);

        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                dbmsObject.getValueFactory(),
                dataGeneratorObject);
        TestSuite testSuite = testSuiteGenerator.generate();

        printReport(schemaObject, criterionObject, testSuite, testSuiteGenerator.getFailedTestCases());

        if (buildTestSuite) {
            // execute each test case to see what the DBMS result is for each row generated (accept / row)
            TestCaseExecutor executor = new TestCaseExecutor(
                    schemaObject,
                    dbmsObject,
                    new DatabaseConfiguration(),
                    new LocationsConfiguration());
            executor.execute(testSuite);

            // write JUnit test suite to file
            writeTestSuite(schemaObject, dbmsObject, testSuite, "generatedtest");
        }
    }

    private void printReport(Schema schemaObject,
                             CoverageCriterion criterionObject,
                             TestSuite testSuite,
                             List<TestCase> failedTestCases) {

        // print out each test suite test case
        //System.out.println("SUCCESSFUL TEST CASES:");
        //for (TestCase testCase : testSuite.getTestCases()) {
        //    System.out.println("\n" + testCase);
        //}

        //// print out each failed test case
        System.out.println("FAILED TEST CASES:");
        for (TestCase testCase : failedTestCases) {
            System.out.println("\n" + testCase);
        }

        System.out.println("\nTEST SUITE STATS:");
        System.out.println("Number of failed test cases: " + failedTestCases.size());
        System.out.println("Number of successful test cases: " + testSuite.getNumTestCases());
        System.out.println("Number of inserts: " + testSuite.getNumInserts());
        System.out.println("Number of evaluations: " + testSuite.getNumEvaluations());
        System.out.println("Average No. of evaluations: " + testSuite.getAvNumEvaluations());

        for (CoverageCriterion criterion : CoverageCriterionFactory.allCriteria()) {
            String name = criterion.getName();
            String starred = "";
            if (name.equals(criterionObject.getName())) {
                starred = " (*)";
            }

            CoverageReport coverageReport =
                    new CoverageReport(testSuite, criterion.generateRequirements(schemaObject));
            System.out.println(name + starred + ": " + coverageReport.getCoverage());

            //if (printUncoveredPredicates) {
                List<Predicate> uncovered = coverageReport.getUncoveredRequirements();
                //if (uncovered.size() > 0) {
                //    System.out.println("Uncovered predicates: ");
                //    for (Predicate predicate : uncovered) {
                //        System.out.println(predicate.getPurposes() + ": " + predicate);
                //    }
                //}
            //}
        }
    }

    private void writeTestSuite(Schema schema, DBMS dbms, TestSuite testSuite, String packageName) {
        String className = "Test" + schema.getName();

        String javaCode = new TestSuiteJavaWriter(schema, dbms, testSuite)
                .writeTestSuite(packageName, className);

        File javaFile = new File(locationsConfiguration.getSrcDir()
                + "/" + packageName + "/" + className + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
            System.out.println("\n[JUnit test suite written to " + javaFile + "]");
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
        new GenerateSchemaCoverage().run(args);
    }
}
