package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public class TestRequirementIDGeneratorUsingTable implements TestRequirementIDGenerator {

    private int counter;
    private String id;

    public void reset(String id, String type) {
        if (type.equals("table")) {
            this.id = id;
            counter = 0;
        }
    }

    public TestRequirementID nextID() {
        counter ++;
        return new TestRequirementIDUsingTable(counter, id);
    }
}
