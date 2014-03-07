package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.CoverageReport;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.data.Data;

/**
 * Created by phil on 03/02/2014.
 */
public abstract class TestCaseGenerationAlgorithm {

    public abstract TestCase generateTestCase(Data data, Data state, Predicate predicate);

    public abstract boolean testCaseSatisfiesPredicate(TestCase testCase, Predicate predicate);

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
