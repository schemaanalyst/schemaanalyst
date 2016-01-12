package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;

/**
 * Created by phil on 24/07/2014.
 */
public class DataGenerationResult {

    private Data data, state;
    private DataGenerationReport report;

    public DataGenerationResult(Data data, Data state, DataGenerationReport report) {
        this.data = data;
        this.state = state;
        this.report = report;
    }

    public Data getData() {
        return data;
    }

    public Data getState() {
        return state;
    }

    public DataGenerationReport getReport() {
        return report;
    }
}
