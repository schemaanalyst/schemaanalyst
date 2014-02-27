package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionClauseChecker extends Checker {

    private ExpressionClause expressionClause;
    private Data data;
    private List<Cell> nonComplyingCells;

    public ExpressionClauseChecker(ExpressionClause expressionClause, Data data) {
        this.expressionClause = expressionClause;
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
        nonComplyingCells = new ArrayList<>();

        // do the check

        return (nonComplyingCells.size() == 0);
    }
}
