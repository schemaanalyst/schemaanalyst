package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuite extends StatisticStore {

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
}
