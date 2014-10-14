package org.schemaanalyst.data.generation.directedrandom;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionPredicateFixer extends PredicateFixer {

    private ExpressionPredicateChecker expressionPredicateChecker;
    private RandomCellValueGenerator cellValueGenerator;

    public ExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker, RandomCellValueGenerator cellValueGenerator) {
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
