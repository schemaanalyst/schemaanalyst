package org.schemaanalyst.coverage.testgeneration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuite extends Statistics {

    private List<TestCase> testCases;

    public TestSuite() {
        testCases = new ArrayList<>();
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(testCases);
    }
}
