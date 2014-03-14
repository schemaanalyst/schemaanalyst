package org.schemaanalyst.data.generation.checker;

import org.schemaanalyst.testgeneration.coveragecriterion.clause.ExpressionClause;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionClauseChecker extends ClauseChecker<ExpressionClause> {

    private ExpressionClause expressionClause;
    private boolean allowNull;
    private Data data;
    private List<Cell> nonComplyingCells;

    public ExpressionClauseChecker(ExpressionClause expressionClause, boolean allowNull, Data data) {
        this.expressionClause = expressionClause;
        this.allowNull = allowNull;
        this.data = data;
    }

    public ExpressionClause getClause() {
        return expressionClause;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {

        nonComplyingCells = new ArrayList<>();

        List<Row> rows = data.getRows(expressionClause.getTable());
        if (rows.size() > 0) {

            for (Row row : rows) {
                ExpressionChecker expressionChecker = new ExpressionChecker(
                        expressionClause.getExpression(),
                        expressionClause.getSatisfy(),
                        allowNull,
                        row);

                if (!expressionChecker.check()) {
                    nonComplyingCells.addAll(expressionChecker.getNonComplyingCells());
                }
            }

            return (nonComplyingCells.size() == 0);
        }
        return false;
    }

    @Override
    public String getInfo() {
        boolean check = check();
        String info = "Expression clause: " + expressionClause + "\n";
        info += "\t* Success: " + check + "\n";

        if (!check) {
            info += "\t* Non-complying cells:\n";
            for (Cell cell : nonComplyingCells) {
                info += "\t\t* " + cell + "\n";
            }

        }
        return info;
    }
}
