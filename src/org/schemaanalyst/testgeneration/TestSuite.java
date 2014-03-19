package org.schemaanalyst.testgeneration;

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

    public List<TestCase> getTestCases() {
        return new ArrayList<>(testCases);
    }

    public int getNumTestCases() {
        return testCases.size();
    }

    public int getNumInserts() {
        int total = 0;
        for (TestCase testCase : getTestCases()) {
            total += testCase.getNumInserts();
        }
        return total;
    }

    public int getNumEvaluations() {
        int total = 0;
        for (TestCase testCase : getTestCases()) {
            total += testCase.getDataGenerationReport().getNumEvaluations();
        }
        return total;
    }
}
