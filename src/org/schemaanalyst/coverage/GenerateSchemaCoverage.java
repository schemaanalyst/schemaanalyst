package org.schemaanalyst.coverage;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.MultiCriterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.types.AmplifiedConstraintCACCoverage;
import org.schemaanalyst.coverage.criterion.types.ConstraintCACCoverage;
import org.schemaanalyst.coverage.criterion.types.NullColumnCoverage;
import org.schemaanalyst.coverage.criterion.types.UniqueColumnCoverage;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.*;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.Flights;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

/**
 * Created by phil on 21/01/2014.
 */
public class GenerateSchemaCoverage extends Runner {

    @Override
    protected void task() {

        Schema schema = new Flights();

        Criterion constraintCoverage = new MultiCriterion(
                new ConstraintCACCoverage(),
                new NullColumnCoverage(),    // these are supplementary (not part of ICST criterion we used) and can be commented out
                new UniqueColumnCoverage()   // these are supplementary (not part of ICST criterion we used) and can be commented out
        );

        Criterion constraintRACCoverage = new MultiCriterion(
                new AmplifiedConstraintCACCoverage(),
                new NullColumnCoverage(),
                new UniqueColumnCoverage()
        );

        SearchBasedTestCaseGenerationAlgorithm testCaseGenerator =
                new SearchBasedTestCaseGenerationAlgorithm(
                        // can be changed to a different search, e.g. avsRandomStart
                        SearchFactory.avsDefaults(0L, 100000));

        // setting this to false re-uses test cases that cover multiple test requirements, i.e. "squashing" the test suite.
        // set to true to "unsquash".
        boolean reuseTestCases = true;

        DBMS dbms = new SQLiteDBMS();

        TestSuiteGenerator dg = new TestSuiteGenerator(
                schema,
                constraintCoverage,
                dbms,
                testCaseGenerator,
                reuseTestCases);

        // generate the test suite
        TestSuite testSuite = dg.generate();

        // execute each test to see what the DBMS thinks... :-)
        TestCaseExecutor executor = new TestCaseExecutor(schema, new SQLiteDBMS(), new DatabaseConfiguration(), new LocationsConfiguration());

        // print out each test case
        for (TestCase testCase : testSuite.getTestCases()) {
            System.out.println();

            for (Predicate predicate : testCase.getPredicates()) {
                System.out.println("PURPOSE:   " + predicate.getPurpose());
                System.out.println("PREDICATE: " + predicate);
            }
            Data state = testCase.getState();
            if (state.getCells().size() > 0) {
                System.out.println("STATE:     " + testCase.getState());
            }
            System.out.println("DATA:      " + testCase.getData());

            ObjectiveValue objVal = (ObjectiveValue) testCase.getInfo("objval");
            boolean optimal = objVal.isOptimal();
            System.out.println("SUCCESS:   " + optimal);

            if (optimal) {
                executor.execute(testCase);
                System.out.println("RESULTS:   " + testCase.getDBMSResults());
            } else {
                System.out.println("OBJ VAL:   " + testCase.getInfo("objval"));
            }
        }

        System.out.println("Number of test cases: " + testSuite.getNumTestCases());
        System.out.println("Number of inserts: " + testSuite.getNumInserts());
        System.out.println("Constraint Coverage: " + testCaseGenerator.computeCoverage(testSuite, constraintCoverage.generateRequirements(schema)));
        System.out.println("Constraint RAC Coverage: " + testCaseGenerator.computeCoverage(testSuite, constraintRACCoverage.generateRequirements(schema)));

        writeTestSuite(schema, dbms, testSuite, "generatedtest");
        System.out.println("JUnit test suite written");
    }

    private void writeTestSuite(Schema schema, DBMS dbms, TestSuite testSuite, String packageName) {
        String className = "Test" + schema.getName();

        String javaCode = new TestSuiteJavaWriter(schema, dbms, testSuite)
                .writeTestSuite(packageName, className);

        File javaFile = new File(locationsConfiguration.getSrcDir()
                + "/" + packageName + "/" + className + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
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
