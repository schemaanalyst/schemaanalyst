
package org.schemaanalyst.mutation.analysis.executor.testcase;

import java.util.List;
import java.util.Objects;

/**
 * <p> The result of executing a test case virtually - either indicating success
 * or failure.</p>
 * 
 * @author Chris J. Wright
 */
public class VirtualTestCaseResult {
    
    final private List<Boolean> successful;
    
    public VirtualTestCaseResult(List<Boolean> successful) {
        this.successful = successful;
    }
    
    public List<Boolean> getSuccessful() {
        return successful;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.successful);
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
        final VirtualTestCaseResult other = (VirtualTestCaseResult) obj;
        return this.successful.equals(other.successful);
    }

    @Override
    public String toString() {
        return "VirtualTestCaseResult{" + "successful=" + successful + '}';
    }
    
}
