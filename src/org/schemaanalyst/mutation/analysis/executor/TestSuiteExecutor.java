package org.schemaanalyst.mutation.analysis.executor;

import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * <p>Executes each {@link TestCase} of a {@link TestSuite} using a {@link TestCaseExecutor}.</p>
 * 
 * @author Chris J. Wright
 */
public class TestSuiteExecutor {

    public List<MixedPair<TestCase, TestCaseResult>> executeTestSuite(TestCaseExecutor executor, TestSuite suite) {
        List<MixedPair<TestCase, TestCaseResult>> result = new ArrayList<>();
        for (TestCase testCase : suite.getTestCases()) {
            result.add(new MixedPair<>(testCase, executor.executeTestCase(testCase)));
        }
        return result;
    }

}
