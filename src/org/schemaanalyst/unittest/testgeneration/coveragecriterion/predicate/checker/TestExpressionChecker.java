package org.schemaanalyst.unittest.testgeneration.coveragecriterion.predicate.checker;

import org.junit.Test;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionChecker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 12/03/2014.
 */
public class TestExpressionChecker {

    @Test
    public void testRelationalExpression() {
        Table table = new Table("table");
        Column column = new Column("col", new IntDataType());
        Cell cell = new Cell(column, new ValueFactory());
        Row row = new Row(cell);
        NumericValue zero = new NumericValue(0);
        NumericValue minusOne = new NumericValue(-1);

        RelationalExpression relationalExpression = new RelationalExpression(
                new ColumnExpression(table, column),
                RelationalOperator.GREATER_OR_EQUALS,
                new ConstantExpression(zero));

        // Test SATISFY expression
        ExpressionChecker satisfyExpressionChecker = new ExpressionChecker(relationalExpression, true, false, row);

        // test true outcome
        cell.setValue(zero);
        assertTrue(satisfyExpressionChecker.check());

        // test false outcome
        cell.setValue(minusOne);
        assertFalse(satisfyExpressionChecker.check());

        // Test SATISFY expression
        ExpressionChecker notSatisfyExpressionChecker = new ExpressionChecker(relationalExpression, false, false, row);

        // test true outcome
        cell.setValue(minusOne);
        assertTrue(notSatisfyExpressionChecker.check());

        // test false outcome
        cell.setValue(zero);
        assertFalse(notSatisfyExpressionChecker.check());
    }

    @Test
    public void testParenthesisedExpression() {
        Table table = new Table("table");
        Column column = new Column("col", new IntDataType());
        Cell cell = new Cell(column, new ValueFactory());
        Row row = new Row(cell);
        NumericValue zero = new NumericValue(0);
        NumericValue minusOne = new NumericValue(-1);

        RelationalExpression relationalExpression = new RelationalExpression(
                new ColumnExpression(table, column),
                RelationalOperator.GREATER_OR_EQUALS,
                new ConstantExpression(zero));

        ParenthesisedExpression parenthesisedExpression = new ParenthesisedExpression(relationalExpression);

        // Test SATISFY expression
        ExpressionChecker satisfyExpressionChecker = new ExpressionChecker(parenthesisedExpression, true, false, row);

        // test true outcome
        cell.setValue(zero);
        assertTrue(satisfyExpressionChecker.check());

        // test false outcome
        cell.setValue(minusOne);
        assertFalse(satisfyExpressionChecker.check());

        // Test SATISFY expression
        ExpressionChecker notSatisfyExpressionChecker = new ExpressionChecker(parenthesisedExpression, false, false, row);

        // test true outcome
        cell.setValue(minusOne);
        assertTrue(notSatisfyExpressionChecker.check());

        // test false outcome
        cell.setValue(zero);
        assertFalse(notSatisfyExpressionChecker.check());

    }
}