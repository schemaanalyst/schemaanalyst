package org.schemaanalyst.data.generation;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.DataGenerationResult;

/**
 * Created by phil on 23/07/2014.
 */
public class DataGenerationReport {

    private boolean success;
    private int numEvaluations;

    public DataGenerationReport(boolean success) {
        this.success = success;
        this.numEvaluations = -1;
    }

    public DataGenerationReport(boolean success, int numEvaluations) {
        this.success = success;
        this.numEvaluations = numEvaluations;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getNumEvaluations() {
        return numEvaluations;
    }
}
