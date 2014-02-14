package org.schemaanalyst.coverage;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.types.CriterionFactory;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.*;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

/**
 * Created by phil on 21/01/2014.
 */
public class GenerateSchemaCoverage extends Runner {

    @Override
    protected void task() {

        // these are parameters of the task (TODO: formalize these as per Runner ...)
        Schema schema = new CustomerOrder();
        DBMS dbms = new SQLiteDBMS();
        Criterion criterion = CriterionFactory.instantiate("amplifiedConstraintCACCoverage");
        boolean reuseTestCases = true;
        Search<Data> search = SearchFactory.avsDefaults(0L, 100000);

        // instantiate the test case generation algorithm
        TestCaseGenerationAlgorithm testCaseGenerator =
                new SearchBasedTestCaseGenerationAlgorithm(search);

        // instantiate the test suite generator and generate the test suite
        TestSuiteGenerator dg = new TestSuiteGenerator(
                schema,
                criterion,
                dbms,
                testCaseGenerator,
                reuseTestCases);

        TestSuite testSuite = dg.generate();

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schema,
                dbms,
                new DatabaseConfiguration(),
                new LocationsConfiguration());

        executor.execute(testSuite);

        // write report to console
        printReport(schema, criterion, testSuite, dg.getFailedTestCases(), testCaseGenerator);

        // write JUnit test suite to file
        writeTestSuite(schema, dbms, testSuite, "generatedtest");
    }

    private void printReport(Schema schema,
                             Criterion criterionUsed,
                             TestSuite testSuite,
                             List<TestCase> failedTestCases,
                             TestCaseGenerationAlgorithm testCaseGenerator) {

        // print out each test suite test case
        for (TestCase testCase : testSuite.getTestCases()) {
            printTestCase(testCase, true);
        }

        // print out each failed test case
        for (TestCase testCase : failedTestCases) {
            printTestCase(testCase, true);
        }

        printTestSuiteStats(schema, criterionUsed, testSuite, testCaseGenerator);
    }

    private void printTestCase(TestCase testCase, boolean success) {
        System.out.println("\n" + testCase);

        if (!success) {
            // print details of the objective value computed by the search
            System.out.println("FAIL – OBJ VAL:\n" + testCase.getInfo("objval"));
        }
    }

    private void printTestSuiteStats(Schema schema, Criterion criterionUsed, TestSuite testSuite, TestCaseGenerationAlgorithm testCaseGenerator) {
        System.out.println("\nTEST SUITE STATS:");
        System.out.println("Number of test cases: " + testSuite.getNumTestCases());
        System.out.println("Number of inserts: " + testSuite.getNumInserts());

        for (Criterion criterion : CriterionFactory.allCriteria()) {
            String name = criterion.getName();
            String starred = "";
            if (name.equals(criterionUsed.getName())) {
                starred = " (*)";
            }

            System.out.println(name + starred + ": " + testCaseGenerator.computeCoverage(testSuite, criterion.generateRequirements(schema)));
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

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new GenerateSchemaCoverage().run(args);
    }
}
