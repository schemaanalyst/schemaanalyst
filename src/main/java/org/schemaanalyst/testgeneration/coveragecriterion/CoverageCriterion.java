package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class CoverageCriterion {

    public abstract String getName();

    public abstract TestRequirements generateRequirements();
}
