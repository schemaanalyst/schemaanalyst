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
	private RandomCellValueGenerator cellValueGenerator;
	private SearchMini<Data> search;
	private Data state;

	public ExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker,
			RandomCellValueGenerator cellValueGenerator, SearchMini search, Data state) {
		this.expressionPredicateChecker = expressionPredicateChecker;
		this.cellValueGenerator = cellValueGenerator;
		this.search = search;
		this.state = state;
	}

	@Override
	public void attemptFix(int eval) {
		int evaluations = 1000;

		if (eval > evaluations) {
			evaluations = eval;
		}
		
		// Set up termination
        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(), evaluations),
                new OptimumTerminationCriterionMini<>(search));

        search.setTerminationCriterion(terminationCriterion);
		
		search.setObjectiveFunction(
				new ExpressionPredicateObjectiveFunction(expressionPredicateChecker.getPredicate()));
		search.initialize();

		/*
		 * for (Cell cell : expressionPredicateChecker.getNonComplyingCells()) {
		 * //cellValueGenerator.generateCellValue(cell);
		 * //System.out.println("ExpressionPredicateFixer"); search.search(cell,
		 * expressionPredicateChecker); }
		 */
		if (expressionPredicateChecker.getNonComplyingCells().size() > 0) {
			// search.search(expressionPredicateChecker.getNonComplyingData(),
			// expressionPredicateChecker,
			// expressionPredicateChecker.getNonComplyingCells());
			search.search(expressionPredicateChecker.getNonComplyingCells(), expressionPredicateChecker);
		}
		// System.out.println("ExpressionPredicateFixer");

	}
}
