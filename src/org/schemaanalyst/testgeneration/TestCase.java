package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase implements Serializable {

    private static final long serialVersionUID = 7252118737699128109L;

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

    public TestRequirement getTestRequirement() {
        return testReqiurement;
    }

    public void setDBMSResults(List<Boolean> dbmsResults) {
        this.dbmsResults = new ArrayList<>(dbmsResults);
    }

    public List<Boolean> getDBMSResults() {
        return new ArrayList<>(dbmsResults);
    }

    public Boolean getLastDBMSResult() {
        return dbmsResults.get(dbmsResults.size()-1);
    }
}
