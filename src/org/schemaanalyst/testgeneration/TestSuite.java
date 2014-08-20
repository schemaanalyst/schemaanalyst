package org.schemaanalyst.testgeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/07/2014.
 */
public class TestSuite implements Serializable {

    private static final long serialVersionUID = -7221865547415541154L;

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
