package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.CoverageReport;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.coverage.testgeneration.datageneration.checker.PredicateChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerationAlgorithm {

    public abstract TestCase generateTestCase(Data data, Data state, Predicate predicate);

    public boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate) {

        PredicateChecker predicateChecker = new PredicateChecker(predicate, testCase.getData(), testCase.getState());
        boolean checkerResult = predicateChecker.check();

        PredicateObjectiveFunction objFun = new PredicateObjectiveFunction(predicate, testCase.getState());
        ObjectiveValue objVal = objFun.evaluate(testCase.getData());
        boolean objectiveFunctionResult = objVal.isOptimal();

        if (checkerResult != objectiveFunctionResult) {
            throw new TestGenerationException(
                    "Disagreement in result for predicate:\n" + predicate + "with test case:\n" + testCase +
                            "\nChecker: " + checkerResult + "\nObj Fun: " + objectiveFunctionResult +
                            "\nChecker info:\n " + predicateChecker.getInfo() +
                            "\nObj Fun info:\n " + objVal);
        }

        return checkerResult;
    }

    public TestCase testCaseThatSatisfiesPredicate(Predicate predicate, TestSuite testSuite) {
        for (TestCase testCase : testSuite.getTestCases()) {
            if (testCaseSatisfiesPredicate(testCase, predicate)) {
                return testCase;
            }
        }
        return null;
    }

    public CoverageReport computeCoverage(TestSuite testSuite, Requirements requirements) {
        CoverageReport coverageReport = new CoverageReport();

        for (Predicate predicate : requirements.getPredicates()) {
            boolean covered = false;
            for (TestCase testCase : testSuite.getTestCases()) {
                if (testCaseSatisfiesPredicate(testCase, predicate)) {
                    covered = true;
                    coverageReport.addCovered(predicate);

                    // More efficient to break, but am testing different evaluations over all test cases
                    // break;
                }
            }
            if (!covered) {
                coverageReport.addUncovered(predicate);
            }
        }
        return coverageReport;
    }
}
