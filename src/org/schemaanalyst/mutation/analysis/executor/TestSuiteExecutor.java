package org.schemaanalyst.mutation.analysis.executor;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 *
 * @author Chris J. Wright
 */
public class TestSuiteExecutor {

    public List<MixedPair<TestCase, TestCaseResult>> executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        List<MixedPair<TestCase, TestCaseResult>> result = new ArrayList<>();
        for (TestCase testCase : suite.getUsefulTestCases()) {
            result.add(new MixedPair<>(testCase, executor.executeTestCase(testCase)));
        }
        return result;
    }

}
