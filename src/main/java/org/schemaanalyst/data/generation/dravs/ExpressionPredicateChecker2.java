package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ExpressionPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014. edited by abdullah
 */
public class ExpressionPredicateChecker2 extends PredicateChecker {

    private ExpressionPredicate expressionPredicate;
    private boolean allowNull;
    private Data data;
    private List<Cell> nonComplyingCells;
    private Data nonComplyingData = new Data();

    public ExpressionPredicateChecker2(ExpressionPredicate expressionPredicate, boolean allowNull, Data data) {
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
    
    public Data getData() {
    	return data;
    }
    
    public Data getNonComplyingData() {
    	return nonComplyingData;
    }

    @Override
    public boolean check() {

        nonComplyingCells = new ArrayList<>();
        nonComplyingData = new Data();

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
                    //nonComplyingData.appendData(expressionChecker.getNonComplyingData());
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
