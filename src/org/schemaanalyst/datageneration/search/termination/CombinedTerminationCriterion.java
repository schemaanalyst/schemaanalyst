package org.schemaanalyst.datageneration.search.termination;

import java.util.ArrayList;
import java.util.List;

public class CombinedTerminationCriterion implements TerminationCriterion {

    protected List<TerminationCriterion> criteria;

    public CombinedTerminationCriterion(TerminationCriterion... criteria) {
        this.criteria = new ArrayList<>();
        for (TerminationCriterion criterion : criteria) {
            this.criteria.add(criterion);
        }
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
