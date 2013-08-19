package org.schemaanalyst.test.mutation.artefactsupplier;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import static org.junit.Assert.*;

public class TestVisitorGenerics {

    
    class ExpressionFilter<E> {
        
        private E example;
        
        public ExpressionFilter(E example) {
            this.example = example;
        }
        
        public boolean test(Expression expression) {
            if (expression.getClass().equals(example.getClass())) {
                return true;
            }            
            return false;
        }
        
    }
    
    @Test
    public void testGenericExpression() {
        ExpressionFilter<RelationalExpression> reFilter = new ExpressionFilter<>(new RelationalExpression(null, null, null));
        
        Expression constantExpression = new ConstantExpression(new NumericValue(1)); 
        
        Expression relationalExpression = new RelationalExpression(
                constantExpression, 
                RelationalOperator.EQUALS, 
                constantExpression);
        
        assertTrue(reFilter.test(relationalExpression));
        assertFalse(reFilter.test(constantExpression));
    }    
}
