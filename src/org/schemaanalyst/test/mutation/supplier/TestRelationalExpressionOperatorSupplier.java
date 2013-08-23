/*
 */
package org.schemaanalyst.test.mutation.supplier;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.expression.RelationalExpressionOperatorSupplier;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import static org.junit.Assert.*;

import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;

/**
 *
 * @author Chris J. Wright
 */
public class TestRelationalExpressionOperatorSupplier {
    
    
    RelationalExpression relationalExpression1 = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)),
            RelationalOperator.LESS,
            new ConstantExpression(new NumericValue(2)));
    
    RelationalExpression relationalExpression2 = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)),
            RelationalOperator.EQUALS,
            new ConstantExpression(new NumericValue(2)));
    
    ParenthesisedExpression parenExpression = new ParenthesisedExpression(
            relationalExpression2);
    
    AndExpression andExpression = new AndExpression(
            relationalExpression1, parenExpression);
    
    ConstantExpression constantExpression = new ConstantExpression(new NumericValue(1));
    
    @Test
    public void TestTopLevel() {
        RelationalExpressionOperatorSupplier supplier =
                new RelationalExpressionOperatorSupplier();
        supplier.initialise(relationalExpression1);
        assertTrue(supplier.hasNext());
        assertEquals(RelationalOperator.LESS, supplier.getNextComponent());
        assertFalse(supplier.hasNext());
    }
    
    @Test
    public void TestParenLevel() {
        RelationalExpressionOperatorSupplier supplier =
                new RelationalExpressionOperatorSupplier();
        supplier.initialise(parenExpression);
        assertTrue(supplier.hasNext());
        assertEquals(RelationalOperator.EQUALS, supplier.getNextComponent());
        assertFalse(supplier.hasNext());
    }
    
    @Test
    public void TestMultiple() {
        RelationalExpressionOperatorSupplier supplier =
                new RelationalExpressionOperatorSupplier();
        supplier.initialise(andExpression);
        assertTrue(supplier.hasNext());
        assertEquals(RelationalOperator.LESS, supplier.getNextComponent());
        assertTrue(supplier.hasNext());
        assertEquals(RelationalOperator.EQUALS, supplier.getNextComponent());
        assertFalse(supplier.hasNext());
    }
    
    @Test
    public void TestNone() {
        RelationalExpressionOperatorSupplier supplier =
                new RelationalExpressionOperatorSupplier();
        supplier.initialise(constantExpression);
        assertFalse(supplier.hasNext());
        assertNull(supplier.getNextComponent());
    }
    
    @Test
    public void TestDuplicate() {
        RelationalExpressionOperatorSupplier supplier =
                new RelationalExpressionOperatorSupplier();
        supplier.initialise(relationalExpression1);
        supplier.getNextComponent();
        assertNotSame(supplier.makeDuplicate(), relationalExpression1);
    }
}
