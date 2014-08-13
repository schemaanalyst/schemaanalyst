package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public interface CoverageCriterion {

    public String getName();

    public TestRequirements generateRequirements();
}
