package org.schemaanalyst.test.datageneration.search.objective.expression;

import org.junit.Test;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.AndExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestAndExpressionObjectiveFunction {
    
    @Test
    public void expTrue_satisfyTrue_allowNullFalse() {
        
        RelationalExpression relExp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        AndExpression andExp = new AndExpression(relExp, relExp);
        
        AndExpressionObjectiveFunction objFun =
                new AndExpressionObjectiveFunction(andExp, true, false);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertOptimal(objVal);
    }
    
    @Test
    public void expFalse_satisfyTrue_allowNullFalse() {

        RelationalExpression relExp1 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpression relExp2 
            = new RelationalExpression(
                new ConstantExpression(new NumericValue(1)),
                RelationalOperator.EQUALS, 
                new ConstantExpression(new NumericValue(2)));        
        
        AndExpression andExp = new AndExpression(relExp1, relExp2);
        
        AndExpressionObjectiveFunction objFun =
                new AndExpressionObjectiveFunction(andExp, true, false);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertNonOptimal(objVal);
    } 
    
    @Test
    public void expMultiNulls_satisfyTrue_allowNullTrue() {
        
        RelationalExpression relExp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        AndExpression andExp = new AndExpression(relExp, relExp);
        
        AndExpressionObjectiveFunction objFun =
                new AndExpressionObjectiveFunction(andExp, true, true);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertOptimal(objVal);
    }
    
    @Test
    public void expNull_satisfyTrue_allowNullTrue() {

        RelationalExpression relExp1 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        RelationalExpression relExp2 
            = new RelationalExpression(
                new ConstantExpression(new NumericValue(1)),
                RelationalOperator.EQUALS, 
                new ConstantExpression(null));        
        
        AndExpression andExp = new AndExpression(relExp1, relExp2);
        
        AndExpressionObjectiveFunction objFun =
                new AndExpressionObjectiveFunction(andExp, true, true);
        
        ObjectiveValue objVal = objFun.evaluate(new MockRow());
        
        assertOptimal(objVal);
    }      
}
