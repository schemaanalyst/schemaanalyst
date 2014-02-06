package org.schemaanalyst.coverage;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.MultiCriterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.types.ConstraintCoverage;
import org.schemaanalyst.coverage.criterion.types.ConstraintRACCoverage;
import org.schemaanalyst.coverage.criterion.types.NullColumnCoverage;
import org.schemaanalyst.coverage.criterion.types.UniqueColumnCoverage;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestCaseExecutor;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.Flights;

/**
 * Created by phil on 21/01/2014.
 */
public class Test extends Runner {

    @Override
    protected void task() {

        Flights flights = new Flights();

        Criterion constraintCoverage = new MultiCriterion(
                new ConstraintCoverage(),
                new NullColumnCoverage(),    // these are supplementary (not part of ICST criterion we used) and can be commented out
                new UniqueColumnCoverage()   // these are supplementary (not part of ICST criterion we used) and can be commented out
        );

        Criterion constraintRACCoverage = new MultiCriterion(
                new ConstraintRACCoverage(),
                new NullColumnCoverage(),
                new UniqueColumnCoverage()
        );

        SearchBasedTestCaseGenerationAlgorithm testCaseGenerator =
                new SearchBasedTestCaseGenerationAlgorithm(
                        // can be changed to a different search, e.g. avsRandomStart
                        SearchFactory.avsDefaults(0L, 100000));

        // setting this to false re-uses test cases that cover multiple test requirements, i.e. "squashing" the test suite.
        // set to true to "unsquash".
        boolean oneTestPerRequirement = false;

        TestSuiteGenerator dg = new TestSuiteGenerator(
                flights,
                constraintCoverage,
                new SQLiteDBMS(),
                testCaseGenerator,
                oneTestPerRequirement);

        // generate the test suite
        TestSuite testSuite = dg.generate();

        // execute each test to see what the DBMS thinks... :-)
        TestCaseExecutor executor = new TestCaseExecutor(flights, new SQLiteDBMS(), new DatabaseConfiguration(), new LocationsConfiguration());

        // print out each test case
        boolean first = true;
        for (TestCase testCase : testSuite.getAllTestCases()) {
            if (first) {
                System.out.println();
            } else {
                first = false;
            }
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

        System.out.println("Number of test cases " + testSuite.getUsefulTestCases().size());

        System.out.println("Coverage: ");
        System.out.println("Constraint Coverage: " + testCaseGenerator.computeCoverage(testSuite, constraintCoverage.generateRequirements(flights)));
        System.out.println("Constraint RAC Coverage: " + testCaseGenerator.computeCoverage(testSuite, constraintRACCoverage.generateRequirements(flights)));
    }

    @Override
    protected void validateParameters() {
       // no params to validate
    }

    public static void main(String... args) {
        new Test().run(args);
    }
}
