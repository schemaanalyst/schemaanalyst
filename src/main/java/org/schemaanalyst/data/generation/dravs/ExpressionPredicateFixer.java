package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionPredicateFixer extends PredicateFixer {

    private ExpressionPredicateChecker expressionPredicateChecker;
    private RandomCellValueGenerator cellValueGenerator;
    private SearchMini search;

    public ExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker, RandomCellValueGenerator cellValueGenerator, SearchMini search) {
        this.expressionPredicateChecker = expressionPredicateChecker;
        this.cellValueGenerator = cellValueGenerator;
        this.search = search;
    }

    @Override
    public void attemptFix() {
    	/*
        for (Cell cell : expressionPredicateChecker.getNonComplyingCells()) {
            //cellValueGenerator.generateCellValue(cell);
        	//System.out.println("ExpressionPredicateFixer");
        	search.search(cell, expressionPredicateChecker);
        }
    	*/
    	if (expressionPredicateChecker.getNonComplyingCells().size() > 0) {
            System.out.println("ExpressionPredicateFixer");
            //search.search(expressionPredicateChecker.getNonComplyingData(), expressionPredicateChecker, expressionPredicateChecker.getNonComplyingCells());
            search.search(expressionPredicateChecker.getNonComplyingCells(), expressionPredicateChecker);
    	}
        //System.out.println("ExpressionPredicateFixer");

    }
}
