package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public class TestRequirementDescriptor {
    private String id;
    private String msg;

    public TestRequirementDescriptor(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getID() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return id + ": " + msg;
    }
}
