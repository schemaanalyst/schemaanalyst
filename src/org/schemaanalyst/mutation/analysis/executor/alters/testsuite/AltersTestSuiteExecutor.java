
package org.schemaanalyst.mutation.analysis.executor.alters.testsuite;

import org.schemaanalyst.mutation.analysis.executor.alters.testcase.AltersTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

/**
 *
 * @author Chris J. Wright
 */
public class AltersTestSuiteExecutor {
    
    public TestSuiteResult executeTestSuite(AltersTestCaseExecutor executor, TestSuite suite) {
        TestSuiteResult result = new TestSuiteResult();

        // Add the tables
        executor.executeCreates();
        
        // Execute the tests
        for (TestCase testCase : suite.getTestCases()) {
            executor.executeTestCase(testCase, result);
        }
        
        // Drop the tables
        executor.executeDrops();
        
        return result;
    }
    
}
