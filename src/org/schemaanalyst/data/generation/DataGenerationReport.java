package org.schemaanalyst.data.generation;

/**
 * Created by phil on 14/03/2014.
 */
public class DataGenerationReport {

    private boolean success;
    private int numEvaluations;

    public DataGenerationReport(boolean success, int numEvaluations) {
        this.success = success;
        this.numEvaluations = numEvaluations;
    }

    public boolean getSuccess() {
        return success;
    }

    public int getNumEvaluations() {
        return numEvaluations;
    }
}
