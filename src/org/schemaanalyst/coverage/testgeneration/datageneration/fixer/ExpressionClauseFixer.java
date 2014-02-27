package org.schemaanalyst.coverage.testgeneration.datageneration.fixer;

import org.schemaanalyst.coverage.testgeneration.datageneration.checker.ExpressionClauseChecker;
import org.schemaanalyst.coverage.testgeneration.datageneration.valuegeneration.CellValueGenerator;
import org.schemaanalyst.data.Cell;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionClauseFixer extends Fixer {

    private ExpressionClauseChecker expressionClauseChecker;
    private CellValueGenerator cellValueGenerator;

    public ExpressionClauseFixer(ExpressionClauseChecker expressionClauseChecker, CellValueGenerator cellValueGenerator) {
        this.expressionClauseChecker = expressionClauseChecker;
        this.cellValueGenerator = cellValueGenerator;
    }

    @Override
    public void attemptFix() {
        for (Cell cell : expressionClauseChecker.getNonComplyingCells()) {
            cellValueGenerator.generateCellValue(cell);
        }
    }
}
