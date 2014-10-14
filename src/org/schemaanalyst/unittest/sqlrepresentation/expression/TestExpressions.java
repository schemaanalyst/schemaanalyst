package org.schemaanalyst.unittest.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.*;

import static org.junit.Assert.*;

public class TestExpressions {

    @Test
    public void testAndExpression() {
        ConstantExpression subexp1 = new ConstantExpression(new NumericValue(1));
        ConstantExpression subexp2 = new ConstantExpression(new NumericValue(2));
        
        AndExpression exp1 = new AndExpression(subexp1, subexp2);
        AndExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated AndExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated AndExpression should be equal to the original",
                exp1, exp2);   
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(0), exp2.getSubexpressions().get(0));            

        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(1), exp2.getSubexpressions().get(1));    
    }    
    
    @Test
    public void testBetweenExpression() {
        ConstantExpression subject = new ConstantExpression(new NumericValue(1));
        ConstantExpression lhs = new ConstantExpression(new NumericValue(0));
        ConstantExpression rhs = new ConstantExpression(new NumericValue(2));
        BetweenExpression exp1 = new BetweenExpression(subject, lhs, rhs, true, true);
        BetweenExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated BetweenExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated BetweenExpression should be equal to the original",
                exp1, exp2);    
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(0), exp2.getSubexpressions().get(0));
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(1), exp2.getSubexpressions().get(1));
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(2), exp2.getSubexpressions().get(2));
        
        assertEquals(
                "The duplicated BetweenExpression should have the same notBetween setting",
                exp1.isNotBetween(), exp2.isNotBetween());          

        assertEquals(
                "The duplicated BetweenExpression should have the same symmetric setting",
                exp1.isSymmetric(), exp2.isSymmetric());    
    }
    
    @Test
    public void testColumnExpression() {
        Table table = new Table("test");
        Column column = table.createColumn("test", new IntDataType());
        
        ColumnExpression exp1 = new ColumnExpression(table, column);
        ColumnExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated ColumnExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated ColumnExpression should be equal to the original",
                exp1, exp2);    
        
        assertSame(
                "The table should be shallow copied",
                exp1.getTable(), exp2.getTable());
        
        assertSame(
                "The column should be shallow copied",
                exp1.getColumn(), exp2.getColumn());        
    }
    
    @Test
    public void testConstantExpression() {
        NumericValue nv = new NumericValue(1);
        ConstantExpression exp1 = new ConstantExpression(nv);
        ConstantExpression exp2 = exp1.duplicate();
                
        assertNotSame(
                "The duplicated ConstantExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated ConstantExpression should be equal to the original",
                exp1, exp2);   
        
        assertNotSame(
                "The ConstantExpression should be deep copied",
                exp1.getValue(), exp2.getValue());        
    }    
    
    @Test
    public void testInExpression() {
        ConstantExpression lhs = new ConstantExpression(new NumericValue(0));
        ConstantExpression rhs = new ConstantExpression(new NumericValue(2));
        InExpression exp1 = new InExpression(lhs, rhs, false);
        InExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated InExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated InExpression should be equal to the original",
                exp1, exp2);    
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getLHS(), exp2.getLHS());
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getRHS(), exp2.getRHS());
        
        assertEquals(
                "The duplicated InExpression should have the same notIn setting",
                exp1.isNotIn(), exp2.isNotIn());         
    }
    
    @Test
    public void testListExpression() {
        ConstantExpression subexp1 = new ConstantExpression(new NumericValue(1));
        ConstantExpression subexp2 = new ConstantExpression(new NumericValue(2));
        
        ListExpression exp1 = new ListExpression(subexp1, subexp2);
        ListExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated ListExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated ListExpression should be equal to the original",
                exp1, exp2);   
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(0), exp2.getSubexpressions().get(0));            

        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(1), exp2.getSubexpressions().get(1));    
    }    
    
    @Test
    public void testNullExpression() {
        ConstantExpression subexp = new ConstantExpression(new NumericValue(1));
        
        NullExpression exp1 = new NullExpression(subexp, true);
        NullExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated NullExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated NullExpression should be equal to the original",
                exp1, exp2);   
        
        assertEquals(
                "The duplicated NullExpression should have the same notIn setting",
                exp1.isNotNull(), exp2.isNotNull()); 
    }
     
    @Test
    public void testOrExpression() {
        ConstantExpression subexp1 = new ConstantExpression(new NumericValue(1));
        ConstantExpression subexp2 = new ConstantExpression(new NumericValue(2));
        
        OrExpression exp1 = new OrExpression(subexp1, subexp2);
        OrExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated OrExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated OrExpression should be equal to the original",
                exp1, exp2);   
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(0), exp2.getSubexpressions().get(0));            

        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpressions().get(1), exp2.getSubexpressions().get(1));    
    }     
    
    @Test
    public void testParenthesisedExpression() {
        ConstantExpression subexp = new ConstantExpression(new NumericValue(1));
        
        ParenthesisedExpression exp1 = new ParenthesisedExpression(subexp);
        ParenthesisedExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated OrExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated OrExpression should be equal to the original",
                exp1, exp2);   
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getSubexpression(), exp2.getSubexpression());                
    }
    
    @Test
    public void testRelationalExpression() {
        ConstantExpression lhs = new ConstantExpression(new NumericValue(0));
        ConstantExpression rhs = new ConstantExpression(new NumericValue(2));
        RelationalOperator op = RelationalOperator.EQUALS;
        RelationalExpression exp1 = new RelationalExpression(lhs, op, rhs);
        RelationalExpression exp2 = exp1.duplicate();
        
        assertNotSame(
                "The duplicated RelationalExpression should not be the same as the original",
                exp1, exp2);
        
        assertEquals(
                "The duplicated RelationalExpression should be equal to the original",
                exp1, exp2);    
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getLHS(), exp2.getLHS());
        
        assertNotSame(
                "The subexpressions should also be deep copied",
                exp1.getRHS(), exp2.getRHS());
        
        assertEquals(
                "The RelationalOperator should be the same",
                exp1.getRelationalOperator(), exp2.getRelationalOperator());         
    }    
}
