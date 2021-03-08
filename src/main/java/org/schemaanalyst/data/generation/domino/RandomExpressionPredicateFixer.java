package org.schemaanalyst.data.generation.domino;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;

/**
 * Created by phil on 27/02/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
 
public class RandomExpressionPredicateFixer extends PredicateFixer {

    private ExpressionPredicateChecker expressionPredicateChecker;
    private RandomCellValueGenerator cellValueGenerator;

    public RandomExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker, RandomCellValueGenerator cellValueGenerator) {
        this.expressionPredicateChecker = expressionPredicateChecker;
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void attemptFix() {
        for (Cell cell : expressionPredicateChecker.getNonComplyingCells()) {
            cellValueGenerator.generateCellValue(cell);
        }
    }
}
