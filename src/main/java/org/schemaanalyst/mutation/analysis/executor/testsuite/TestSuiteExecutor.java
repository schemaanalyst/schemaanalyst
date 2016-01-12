package org.schemaanalyst.mutation.analysis.executor.testsuite;

import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

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
    
    public TestSuiteResult executeTestSuite(TestCaseExecutor executor, TestSuite suite, TestSuiteResult expectedResult) {
        TestSuiteResult result = new TestSuiteResult();
        for (TestCase testCase : suite.getTestCases()) {
            result.add(testCase, executor.executeTestCase(testCase, expectedResult.getResult(testCase)));
        }
        return result;
    }

}
