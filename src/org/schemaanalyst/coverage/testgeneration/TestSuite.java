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
            if (testCase.satisfiesOriginalPredicate()) {
                usefulTestCases.add(testCase);
            }
        }
        return usefulTestCases;
    }
}
