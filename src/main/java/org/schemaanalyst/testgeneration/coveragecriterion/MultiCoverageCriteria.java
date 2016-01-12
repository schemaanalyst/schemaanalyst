package org.schemaanalyst.testgeneration.coveragecriterion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 19/08/2014.
 */
public class MultiCoverageCriteria extends CoverageCriterion {

    protected List<CoverageCriterion> coverageCriteria;

    public MultiCoverageCriteria(List<CoverageCriterion> coverageCriteria) {
        this.coverageCriteria = new ArrayList<>(coverageCriteria);
    }

    @Override
    public String getName() {
        String name = "";
        for (CoverageCriterion coverageCriterion : coverageCriteria) {
            if (name != "") {
                name += "+";
            } else {
                name += coverageCriterion.getName();
            }
        }
        return name;
    }

    @Override
    public TestRequirements generateRequirements() {
        TestRequirements testRequirements = new TestRequirements();
        for (CoverageCriterion coverageCriterion : coverageCriteria) {
            TestRequirements criterionRequirements = coverageCriterion.generateRequirements();
            testRequirements.addTestRequirements(criterionRequirements);
        }
        return testRequirements;
    }
}
