package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ExpressionPredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class ExpressionPredicateChecker extends PredicateChecker {

    private ExpressionPredicate expressionPredicate;
    private boolean allowNull;
    private Data data;
    private List<Cell> nonComplyingCells;

    public ExpressionPredicateChecker(ExpressionPredicate expressionPredicate, boolean allowNull, Data data) {
        this.expressionPredicate = expressionPredicate;
        this.allowNull = allowNull;
        this.data = data;
    }

    public ExpressionPredicate getPredicate() {
        return expressionPredicate;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {

        nonComplyingCells = new ArrayList<>();

        List<Row> rows = data.getRows(expressionPredicate.getTable());
        if (rows.size() > 0) {

            for (Row row : rows) {
                ExpressionChecker expressionChecker = new ExpressionChecker(
                        expressionPredicate.getExpression(),
                        expressionPredicate.getTruthValue(),
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
        String info = "Expression clause: " + expressionPredicate + "\n";
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
