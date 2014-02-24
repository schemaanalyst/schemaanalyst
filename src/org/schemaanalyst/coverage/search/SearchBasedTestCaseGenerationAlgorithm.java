package org.schemaanalyst.coverage.search;

import org.schemaanalyst.coverage.CoverageReport;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.search.objectivefunction.PredicateObjectiveFunction;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

import java.util.List;

/**
 * Created by phil on 05/02/2014.
 */
public class SearchBasedTestCaseGenerationAlgorithm extends TestCaseGenerationAlgorithm {

    private Search<Data> search;

    public SearchBasedTestCaseGenerationAlgorithm(Search<Data> search) {
        this.search = search;
    }

    @Override
    public TestCase generateTestCase(Data data, Data state, Predicate predicate) {

        search.setObjectiveFunction(new PredicateObjectiveFunction(predicate, state));
        search.initialize();
        search.search(data);

        ObjectiveValue bestObjectiveValue = search.getBestObjectiveValue();
        boolean success = bestObjectiveValue.isOptimal();

        if (!success) {
            data = search.getBestCandidateSolution();
        }

        TestCase testCase = new TestCase(data, state, predicate, success);
        testCase.addInfo("objval", bestObjectiveValue);
        testCase.addInfo("evaluations", search.getNumEvaluations());
        testCase.addInfo("restarts", search.getNumRestarts());

        return testCase;
    }

    @Override
    public TestCase testCaseThatSatisfiesPredicate(Predicate predicate, TestSuite testSuite) {
        for (TestCase testCase : testSuite.getTestCases()) {
            if (testCaseSatisfiesPredicate(testCase, predicate)) {
                return testCase;
            }
        }
        return null;
    }

    @Override
    public boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate) {
        PredicateObjectiveFunction objFun = new PredicateObjectiveFunction(predicate, testCase.getState());
        ObjectiveValue objVal = objFun.evaluate(testCase.getData());
        return objVal.isOptimal();
    }

    @Override
    public CoverageReport computeCoverage(TestSuite testSuite, List<Predicate> requirements) {
        CoverageReport coverageReport = new CoverageReport();

        for (Predicate predicate : requirements) {
            boolean covered = false;
            for (TestCase testCase : testSuite.getTestCases()) {
                if (testCaseSatisfiesPredicate(testCase, predicate)) {
                    covered = true;
                    coverageReport.addCovered(predicate);
                    break;
                }
            }
            if (!covered) {
                coverageReport.addUncovered(predicate);
            }
        }
        return coverageReport;
    }
}
