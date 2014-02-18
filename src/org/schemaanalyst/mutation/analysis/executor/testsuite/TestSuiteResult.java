
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * <p>The results of executing a {@link TestSuite}.</p>
 * 
 * @author Chris J. Wright
 */
public class TestSuiteResult {
    private final List<MixedPair<TestCase, TestCaseResult>> result;

    public TestSuiteResult() {
        result = new ArrayList<>();
    }
    
    public void add(TestCase testCase, TestCaseResult testCaseResult) {
        result.add(new MixedPair<>(testCase, testCaseResult));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestSuiteResult other = (TestSuiteResult) obj;
        return Objects.equals(this.result, other.result);
    }

    
    
}
