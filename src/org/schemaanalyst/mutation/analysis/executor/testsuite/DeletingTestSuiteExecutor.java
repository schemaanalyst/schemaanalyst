
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.mutation.analysis.executor.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.util.tuple.MixedPair;


public class DeletingTestSuiteExecutor extends TestSuiteExecutor {

    @Override
    public List<MixedPair<TestCase, TestCaseResult>> executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        List<MixedPair<TestCase, TestCaseResult>> result = new ArrayList<>();
        List<TestCase> testCases = suite.getTestCases();
        if (!testCases.isEmpty()) {
            executor.executeDrops();
            executor.executeCreates();
            for (TestCase testCase : testCases) {
                result.add(new MixedPair<>(testCase, executor.executeTestCase(testCase)));
            }
            executor.executeDrops();
        }
        return result;
    }
    
}
