package org.schemaanalyst.test.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

import static org.junit.Assert.*;

public class TestExpressions {

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
}
