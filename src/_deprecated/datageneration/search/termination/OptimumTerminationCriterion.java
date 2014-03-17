package _deprecated.datageneration.search.termination;

import _deprecated.datageneration.search.Search;
import _deprecated.datageneration.search.objective.ObjectiveValue;

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
