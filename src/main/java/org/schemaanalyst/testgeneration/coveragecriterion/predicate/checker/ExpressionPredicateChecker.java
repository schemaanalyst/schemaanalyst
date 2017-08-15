package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.Table;
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
    private Data nonComplyingData;

    public ExpressionPredicateChecker(ExpressionPredicate expressionPredicate, boolean allowNull, Data data) {
        this.expressionPredicate = expressionPredicate;
        this.allowNull = allowNull;
        this.data = data;
    }

    @Override
    public ExpressionPredicate getPredicate() {
        return expressionPredicate;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    public Data getNonComplyingData() {
        return nonComplyingData;
    }

    @Override
    public boolean check() {
        nonComplyingCells = new ArrayList<>();
        nonComplyingData = new Data();

        Table table = expressionPredicate.getTable();

        List<Row> rows = data.getRows(table);
        if (rows.size() > 0) {

            for (Row row : rows) {
                ExpressionChecker expressionChecker = new ExpressionChecker(
                        expressionPredicate.getExpression(),
                        expressionPredicate.getTruthValue(),
                        allowNull,
                        row);

                if (!expressionChecker.check()) {
                    List<Cell> nonComplyingCellsForRow = expressionChecker.getNonComplyingCells();
                    nonComplyingCells.addAll(nonComplyingCellsForRow);
                    nonComplyingData.addRow(table, new Row(nonComplyingCellsForRow));
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
