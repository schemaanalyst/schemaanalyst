
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import java.util.List;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;


public class DeletingTestSuiteExecutor extends TestSuiteExecutor {

    @Override
    public TestSuiteResult executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        TestSuiteResult result = new TestSuiteResult();
        List<TestCase> testCases = suite.getTestCases();
        if (!testCases.isEmpty()) {
            executor.executeDrops();
            executor.executeCreates();
            for (TestCase testCase : testCases) {
                result.add(testCase, executor.executeTestCase(testCase));
            }
            executor.executeDrops();
        }
        return result;
    }
    
}
