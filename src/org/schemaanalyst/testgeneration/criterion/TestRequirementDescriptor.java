package org.schemaanalyst.testgeneration.criterion;

/**
 * Created by phil on 18/07/2014.
 */
public class TestRequirementDescriptor {
    private String id;
    private String description;

    public TestRequirementDescriptor(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + ": " + description;
    }
}
