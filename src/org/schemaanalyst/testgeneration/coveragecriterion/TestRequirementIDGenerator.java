package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public interface TestRequirementIDGenerator {

    public TestRequirementID nextID();

    public void reset(String id, String type);
}
