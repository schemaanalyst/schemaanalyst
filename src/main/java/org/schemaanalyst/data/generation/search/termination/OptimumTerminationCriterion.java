package org.schemaanalyst.data.generation.search.termination;

import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;

public class OptimumTerminationCriterion<T> implements TerminationCriterion {

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
