package org.schemaanalyst.testgeneration.coveragecriterion;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class CoverageCriterion {

    protected TestRequirementIDGenerator testRequirementIDGenerator;

    public CoverageCriterion(TestRequirementIDGenerator testRequirementIDGenerator) {
        this.testRequirementIDGenerator = testRequirementIDGenerator;
    }

    public abstract String getName();

    public abstract TestRequirements generateRequirements();
}
