package org.schemaanalyst.data.generation.search.termination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinedTerminationCriterion implements TerminationCriterion {

    protected List<TerminationCriterion> criteria;

    public CombinedTerminationCriterion(TerminationCriterion... criteria) {
        this.criteria = new ArrayList<>();
        this.criteria.addAll(Arrays.asList(criteria));
    }

    @Override
    public boolean satisfied() {
        for (TerminationCriterion criterion : criteria) {
            if (criterion.satisfied()) {
                return true;
            }
        }
        return false;
    }
}
