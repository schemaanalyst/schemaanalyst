package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionClauseChecker extends Checker {

    private ExpressionClause expressionClause;
    private boolean allowNull;
    private Data data;
    private boolean compliant;
    private List<Cell> nonComplyingCells;

    public ExpressionClauseChecker(ExpressionClause expressionClause, boolean allowNull, Data data) {
        this.expressionClause = expressionClause;
        this.allowNull = allowNull;
        this.data = data;
    }

    public ExpressionClause getExpressionClause() {
        return expressionClause;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {
        compliant = true;
        nonComplyingCells = new ArrayList<>();

        // do the check
        List<Row> rows = data.getRows(expressionClause.getTable());
        for (Row row : rows) {
            ExpressionChecker expressionChecker = new ExpressionChecker(
                    expressionClause.getExpression(),
                    allowNull,
                    expressionClause.getSatisfy(),
                    row);

            if (!expressionChecker.check()) {
                compliant = false;
                nonComplyingCells.addAll(expressionChecker.getNonComplyingCells());
            }
        }

        return compliant;
    }
}
