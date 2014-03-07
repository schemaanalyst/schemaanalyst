package org.schemaanalyst.mutation.analysis.executor.testsuite;

import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;

/**
 * <p>Executes each {@link TestCase} of a {@link TestSuite} using a {@link TestCaseExecutor}.</p>
 * 
 * @author Chris J. Wright
 */
public class TestSuiteExecutor {

    public TestSuiteResult executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        TestSuiteResult result = new TestSuiteResult();
        for (TestCase testCase : suite.getTestCases()) {
            result.add(testCase, executor.executeTestCase(testCase));
        }
        return result;
    }

}
