package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.objective.predicate.ExpressionPredicateObjectiveFunction;
import org.schemaanalyst.data.generation.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionPredicateFixer extends PredicateFixer {

	private ExpressionPredicateChecker expressionPredicateChecker;
	private SearchMini<Data> search;
	private Data state;

	public ExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker,
			RandomCellValueGenerator cellValueGenerator, SearchMini search, Data state) {
		this.expressionPredicateChecker = expressionPredicateChecker;
		this.search = search;
		this.state = state;
	}

	@Override
	public void attemptFix(int eval) {
		// AVMing if there are cells
		if (expressionPredicateChecker.getNonComplyingCells().size() > 0) {
			int evaluations = 1000;

			if (eval > evaluations) {
				evaluations = eval;
			}

			// Set up termination
			TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
					new CounterTerminationCriterion(search.getEvaluationsCounter(), evaluations),
					new OptimumTerminationCriterionMini<>(search));

			search.setTerminationCriterion(terminationCriterion);

			// Set objective Function
			search.setObjectiveFunction(
					new ExpressionPredicateObjectiveFunction(expressionPredicateChecker.getPredicate()));
			search.initialize();
			search.search(expressionPredicateChecker.getNonComplyingCells(), expressionPredicateChecker);
		}
	}
}
