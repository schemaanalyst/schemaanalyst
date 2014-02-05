package org.schemaanalyst.coverage;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.MultiCriterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.types.ConstraintRACCoverage;
import org.schemaanalyst.coverage.criterion.types.NullColumnCoverage;
import org.schemaanalyst.coverage.criterion.types.UniqueColumnCoverage;
import org.schemaanalyst.coverage.search.AVSTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestCaseExecutor;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
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

        Criterion criterion = new MultiCriterion(
                new ConstraintRACCoverage(),
                new NullColumnCoverage(),
                new UniqueColumnCoverage()
        );


        AVSTestCaseGenerationAlgorithm testCaseGenerator = new AVSTestCaseGenerationAlgorithm();

        TestSuiteGenerator dg = new TestSuiteGenerator(
                flights,
                criterion,
                new PostgresDBMS(),
                testCaseGenerator,
                true);

        TestSuite testSuite = dg.generate();

        TestCaseExecutor executor = new TestCaseExecutor(flights, new SQLiteDBMS(), new DatabaseConfiguration(), new LocationsConfiguration());

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
        System.out.println("Coverage: " + testCaseGenerator.computeCoverage(testSuite, criterion.generateRequirements(flights)));
    }

    @Override
    protected void validateParameters() {

    }

    public static void main(String... args) {
        new Test().run(args);
    }
}
