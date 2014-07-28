package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase {

    private TestRequirement testReqiurement;
    private Data data, state;
    private List<Boolean> dbmsResults;

    public TestCase(TestRequirement testRequirement, Data data, Data state) {
        this.testReqiurement = testRequirement;
        this.data = data;
        this.state = state;
        dbmsResults = new ArrayList<>();
    }

    public Data getData() {
        return data;
    }

    public Data getState() {
        return state;
    }

    public TestRequirement getTestReqiurement () {
        return testReqiurement;
    }

    public void setDBMSResults(List<Boolean> dbmsResults) {
        this.dbmsResults = new ArrayList<>(dbmsResults);
    }

    public List<Boolean> getDBMSResults() {
        return new ArrayList<>(dbmsResults);
    }
}
