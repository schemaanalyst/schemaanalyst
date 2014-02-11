package org.schemaanalyst.mutation.analysis.executor;

import java.util.Objects;
import org.schemaanalyst.mutation.analysis.executor.exceptions.TestCaseException;

/**
 * <p>The result of executing a test case - either indicating success or 
 * providing the resulting exception.</p>
 * 
 * @author Chris J. Wright
 */
public class TestCaseResult {

    public final static TestCaseResult SuccessfulTestCaseResult = new TestCaseResult();

    final boolean successful;
    final private TestCaseException exception;

    public TestCaseResult() {
        successful = true;
        exception = null;
    }

    public TestCaseResult(TestCaseException exception) {
        successful = false;
        this.exception = exception;
    }

    public TestCaseException getException() {
        return exception;
    }

    public boolean wasSuccessful() {
        return successful;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.exception.getMessage());
        hash = 89 * hash + (this.successful ? 1 : 0);
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
        final TestCaseResult other = (TestCaseResult) obj;
        if (this.successful != other.successful) {
            return false;
        }
        if (this.exception.getClass() != other.exception.getClass()) {
            return false;
        }
        return Objects.equals(this.exception.getMessage(), other.exception.getMessage());
    }

    @Override
    public String toString() {
        return "TestCaseResult{" + "successful=" + successful + ", exception=" + (exception == null ? "null" : exception.getMessage()) + '}';
    }

}
