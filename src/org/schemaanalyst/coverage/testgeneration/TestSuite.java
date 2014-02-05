package org.schemaanalyst.coverage.testgeneration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuite {

    private List<TestCase> testCases;

    public TestSuite() {
        testCases = new ArrayList<>();
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public List<TestCase> getAllTestCases() {
        return new ArrayList<>(testCases);
    }

    public List<TestCase> getUsefulTestCases() {
        List<TestCase> usefulTestCases = new ArrayList<>();
        for (TestCase testCase : testCases) {
            if (testCase.isUseful()) {
                usefulTestCases.add(testCase);
            }
        }
        return usefulTestCases;
    }

    public double getCoverage() {
        int numPredicates = 0;
        int numSatisfiedPredicates = 0;

        for (TestCase testCase : testCases) {
            int numTestCasePredicates = testCase.getPredicates().size();

            numPredicates += numTestCasePredicates;
            numSatisfiedPredicates += numTestCasePredicates;

            if (numTestCasePredicates > 0 && !testCase.satisfiesOriginalPredicate()) {
                numSatisfiedPredicates --;
            }
        }

        return numSatisfiedPredicates / (double) numPredicates;
    }
}
