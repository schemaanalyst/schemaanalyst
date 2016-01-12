
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

/**
 *
 * @author Chris J. Wright
 */
public class VirtualTestSuiteExecutor {
    
    public VirtualTestSuiteResult executeTestSuite(VirtualTestCaseExecutor executor, TestSuite suite) {
        VirtualTestSuiteResult result = new VirtualTestSuiteResult();
        for (TestCase testCase : suite.getTestCases()) {
            result.add(testCase, executor.executeTestCase(testCase));
        }
        return result;
    }
    
}
