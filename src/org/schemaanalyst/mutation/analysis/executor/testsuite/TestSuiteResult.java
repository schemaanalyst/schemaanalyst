
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.util.tuple.MixedPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    
    public TestCaseResult getResult(TestCase testCase) {
        for (MixedPair<TestCase, TestCaseResult> mixedPair : result) {
            if (mixedPair.getFirst().equals(testCase)) {
                return mixedPair.getSecond();
            }
        }
        return null;
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

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("TestSuiteResult:\n");
        for (MixedPair<TestCase, TestCaseResult> pair : result) {
            b.append("\t");
            b.append(pair.getSecond());
            b.append("\n");
        }
        return b.toString();
    }    
    
    
}
