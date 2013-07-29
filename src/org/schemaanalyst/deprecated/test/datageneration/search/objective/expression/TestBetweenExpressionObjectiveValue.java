package org.schemaanalyst.deprecated.test.datageneration.search.objective.expression;

import org.junit.Test;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.row.BetweenExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestBetweenExpressionObjectiveValue {

    BetweenExpressionObjectiveFunction objFunSatisfy, objFunNotSatisfy;
    
    @Test
    public void expTrue_isNotBetweenFalse_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(2)),
                        false);
                
        setupObjFuns(exp, false);        
        assertOptimality(true);        
    }
    
    @Test
    public void expTrue_isNotBetweenTrue_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(1)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(2)),
                        true);
                
        setupObjFuns(exp, false);        
        assertOptimality(false);        
    }    

    @Test
    public void expFalse_isNotBetweenFalse_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(2)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(1)),
                        false);
                
        setupObjFuns(exp, false);        
        assertOptimality(false);        
    }
    
    @Test
    public void expFalse_isNotBetweenTrue_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(2)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(1)),
                        true);
                
        setupObjFuns(exp, false);        
        assertOptimality(true);        
    }    
    
    @Test
    public void expNull_isNotBetweenFalse_allowNullTrue() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(null),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(2)),
                        false);
                
        setupObjFuns(exp, true);        
        assertNullOptimality(true);        
    }

    @Test
    public void expNull_isNotBetweenTrue_allowNullTrue() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(null),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(new NumericValue(2)),
                        true);
                
        setupObjFuns(exp, true);        
        assertNullOptimality(true);        
    }    
    
    @Test
    public void expNull_isNotBetweenFalse_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(2)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(null),
                        false);
                
        setupObjFuns(exp, false);        
        assertNullOptimality(false);        
    }
    
    @Test
    public void expNull_isNotBetweenTrue_allowNullFalse() {

        BetweenExpression exp = 
                new BetweenExpression(
                        new ConstantExpression(new NumericValue(2)),
                        new ConstantExpression(new NumericValue(0)), 
                        new ConstantExpression(null),
                        true);
                
        setupObjFuns(exp, false);        
        assertNullOptimality(false);        
    }    
    
    private void assertOptimality(boolean satisfyIsOptimal) {
        if (satisfyIsOptimal) {
            assertOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertNonOptimal(objFunNotSatisfy.evaluate(new MockRow()));
        } else {
            assertNonOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertOptimal(objFunNotSatisfy.evaluate(new MockRow()));            
        }
    }

    private void assertNullOptimality(boolean allowNull) {
        if (allowNull) {
            assertOptimal(objFunSatisfy.evaluate(new MockRow()));
            //assertOptimal(objFunFalse.evaluate(new MockRow()));
        } else {
            assertNonOptimal(objFunSatisfy.evaluate(new MockRow()));
            assertNonOptimal(objFunNotSatisfy.evaluate(new MockRow()));            
        }
    }    
    
    private void setupObjFuns(BetweenExpression exp, boolean allowNull) {
        objFunSatisfy = new BetweenExpressionObjectiveFunction(exp, true, allowNull);
        objFunNotSatisfy = new BetweenExpressionObjectiveFunction(exp, false, allowNull);
    }     
}
