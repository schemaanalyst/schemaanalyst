package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;

public class OptimumTerminationCriterionMini<T> implements TerminationCriterion {

	protected SearchMini<T> search;

	public OptimumTerminationCriterionMini(SearchMini<T> search) {
		this.search = search;
	}

	@Override
	public boolean satisfied() {
		ObjectiveValue objectiveValue = search.getBestObjectiveValue();
		return objectiveValue != null && search.getBestObjectiveValue().isOptimal();
	}
}
