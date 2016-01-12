
package org.schemaanalyst.mutation.analysis.executor.testsuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseResult;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * <p>The results of executing a {@link TestSuite} virtually.</p>
 * 
 * @author Chris J. Wright
 */
public class VirtualTestSuiteResult {
    
    private final List<MixedPair<TestCase, VirtualTestCaseResult>> result;
    private final Map<TestCase, VirtualTestCaseResult> map;
    
    public VirtualTestSuiteResult() {
        result = new ArrayList<>();
        map = new HashMap<>();
    }
    
    public void add(TestCase testCase, VirtualTestCaseResult testCaseResult) {
        result.add(new MixedPair<>(testCase, testCaseResult));
        map.put(testCase, testCaseResult);
    }
    
    public VirtualTestCaseResult getResult(TestCase testCase) {
        return map.get(testCase);
    }
    
    public List<MixedPair<TestCase, VirtualTestCaseResult>> getResults() {
        return result;
    }
    
    public int getResultCount() {
        return map.size();
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final VirtualTestSuiteResult other = (VirtualTestSuiteResult) obj;
        return(Objects.equals(this.result,other.result));
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("VirtualTestSuiteResult:\n");
        for (MixedPair<TestCase, VirtualTestCaseResult> pair : result) {
            b.append("\t");
            b.append(pair.getSecond());
            b.append("\n");
        }
        return b.toString();
    }
    
}
