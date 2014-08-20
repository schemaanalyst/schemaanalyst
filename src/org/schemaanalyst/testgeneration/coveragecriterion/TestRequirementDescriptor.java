package org.schemaanalyst.testgeneration.coveragecriterion;

import java.io.Serializable;

/**
 * Created by phil on 18/07/2014.
 */
public class TestRequirementDescriptor implements Comparable<TestRequirementDescriptor>, Serializable {

    private static final long serialVersionUID = -5913936021929831691L;

    private TestRequirementID id;
    private String msg;

    public TestRequirementDescriptor(TestRequirementID id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public TestRequirementID getID() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return id + ": " + msg;
    }

    @Override
    public int compareTo(TestRequirementDescriptor other) {
        return id.compareTo(other.id);
    }
}
