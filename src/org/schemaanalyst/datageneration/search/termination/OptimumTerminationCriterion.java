package org.schemaanalyst.datageneration.search.termination;

import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.util.Duplicable;

public class OptimumTerminationCriterion<T extends Duplicable<T>> implements TerminationCriterion {

    protected Search<T> search;

    public OptimumTerminationCriterion(Search<T> search) {
        this.search = search;
    }

    @Override
    public boolean satisfied() {
        ObjectiveValue objectiveValue = search.getBestObjectiveValue();
        return objectiveValue != null && search.getBestObjectiveValue().isOptimal();
    }
}
