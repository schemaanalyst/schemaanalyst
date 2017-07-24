package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.objective.predicate.ExpressionPredicateObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.MatchPredicateObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionPredicateFixer extends PredicateFixer {

    private ExpressionPredicateChecker expressionPredicateChecker;
    private RandomCellValueGenerator cellValueGenerator;
    private SearchMini<Data> search;
    private Data state;

    public ExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker, RandomCellValueGenerator cellValueGenerator, SearchMini search, Data state) {
        this.expressionPredicateChecker = expressionPredicateChecker;
        this.cellValueGenerator = cellValueGenerator;
        this.search = search;
        this.state = state;
    }

    @Override
    public void attemptFix() {
        //search.setObjectiveFunction(PredicateObjectiveFunctionFactory.createObjectiveFunction(expressionPredicateChecker.getPredicate(), state));
    	search.setObjectiveFunction(new ExpressionPredicateObjectiveFunction(expressionPredicateChecker.getPredicate()));
        search.initializeInner();

    	/*
        for (Cell cell : expressionPredicateChecker.getNonComplyingCells()) {
            //cellValueGenerator.generateCellValue(cell);
        	//System.out.println("ExpressionPredicateFixer");
        	search.search(cell, expressionPredicateChecker);
        }
    	*/
    	if (expressionPredicateChecker.getNonComplyingCells().size() > 0) {
            //search.search(expressionPredicateChecker.getNonComplyingData(), expressionPredicateChecker, expressionPredicateChecker.getNonComplyingCells());
            search.search(expressionPredicateChecker.getNonComplyingCells(), expressionPredicateChecker);
    	}
        //System.out.println("ExpressionPredicateFixer");

    }
}
