package org.schemaanalyst.test.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.FourColumnMockDatabase;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestExpression {
    
    @Test
    public void testColumnsInvolvedOneColumn() {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        
        RelationalExpression expression = 
                new RelationalExpression(
                        new ColumnExpression(database.column),
                        RelationalOperator.EQUALS, 
                        new ConstantExpression(new NumericValue(1)));
     
        assertEquals(expression.getColumnsInvolved().size(), 1);
        assertEquals(expression.getColumnsInvolved().get(0), database.column);
    }
    
    @Test
    public void testColumnsInvolvedOneColumnRepeated() {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        
        RelationalExpression expression = 
                new RelationalExpression(
                        new ColumnExpression(database.column),
                        RelationalOperator.EQUALS, 
                        new ColumnExpression(database.column));
        
        assertEquals(expression.getColumnsInvolved().size(), 1);
        assertEquals(expression.getColumnsInvolved().get(0), database.column);
    } 
    
    @Test
    public void testColumnsInvolvedMultipleExpressions() {

        FourColumnMockDatabase database = new FourColumnMockDatabase();
        
        RelationalExpression relationalExpression1 = 
                new RelationalExpression(
                        new ColumnExpression(database.column1),
                        RelationalOperator.EQUALS, 
                        new ColumnExpression(database.column2));
        
        RelationalExpression relationalExpression2 = 
                new RelationalExpression(
                        new ColumnExpression(database.column3),
                        RelationalOperator.EQUALS, 
                        new ColumnExpression(database.column4));
        
        AndExpression andExpression = 
                new AndExpression(relationalExpression1, relationalExpression2);
        
        assertEquals(andExpression.getColumnsInvolved().size(), 4);
        assertTrue(andExpression.getColumnsInvolved().contains(database.column1));
        assertTrue(andExpression.getColumnsInvolved().contains(database.column2));
        assertTrue(andExpression.getColumnsInvolved().contains(database.column3));
        assertTrue(andExpression.getColumnsInvolved().contains(database.column4));
    }    
    
    @Test
    public void testColumnsInvolvedMultipleExpressionsColumnsRepeated() {

        FourColumnMockDatabase database = new FourColumnMockDatabase();
        
        RelationalExpression relationalExpression1 = 
                new RelationalExpression(
                        new ColumnExpression(database.column1),
                        RelationalOperator.EQUALS, 
                        new ColumnExpression(database.column2));
        
        RelationalExpression relationalExpression2 = 
                new RelationalExpression(
                        new ColumnExpression(database.column1),
                        RelationalOperator.EQUALS, 
                        new ColumnExpression(database.column4));
        
        AndExpression andExpression = 
                new AndExpression(relationalExpression1, relationalExpression2);
        
        assertEquals(andExpression.getColumnsInvolved().size(), 3);
        assertTrue(andExpression.getColumnsInvolved().contains(database.column1));
        assertTrue(andExpression.getColumnsInvolved().contains(database.column2));
        assertTrue(andExpression.getColumnsInvolved().contains(database.column4));
    }      
}
