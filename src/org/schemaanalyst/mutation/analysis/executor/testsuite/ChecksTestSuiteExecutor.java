
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

/**
 *
 * @author Chris J. Wright
 */
public class ChecksTestSuiteExecutor extends TestSuiteExecutor {

    @Override
    public TestSuiteResult executeTestSuite(TestCaseExecutor executor, TestSuite suite, TestSuiteResult expectedResult) {
        throw new UnsupportedOperationException("Transactions cannot be used with ChecksTechnique");
    }

    @Override
    public TestSuiteResult executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        TestSuiteResult result = new TestSuiteResult();
        for (TestCase testCase : suite.getTestCases()) {
            result.add(testCase, executor.executeTestCase(testCase));
        }
        return result;
    }
    
}
