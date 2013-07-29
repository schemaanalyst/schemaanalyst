package org.schemaanalyst.deprecated.test.datageneration.search.objective.expression;

import org.junit.Test;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.row.AndExpressionObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.*;

public class TestAndExpressionObjectiveFunction {
    
    @Test
    public void expTrue_allowNullFalse() {
        
        RelationalExpression relExp 
            = new RelationalExpression(
                    new ConstantExpression(new NumericValue(1)),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        AndExpression andExp = new AndExpression(relExp, relExp);
        
        AndExpressionObjectiveFunction objFunTrue =
                new AndExpressionObjectiveFunction(andExp, true, false);
        
        AndExpressionObjectiveFunction objFunFalse =
                new AndExpressionObjectiveFunction(andExp, false, false);
        
        assertOptimal(objFunTrue.evaluate(new MockRow()));
        assertNonOptimal(objFunFalse.evaluate(new MockRow()));
    }
    
    @Test
    public void expFalse_allowNullFalse() {

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
        
        AndExpressionObjectiveFunction objFunTrue =
                new AndExpressionObjectiveFunction(andExp, true, false);
        
        AndExpressionObjectiveFunction objFunFalse =
                new AndExpressionObjectiveFunction(andExp, false, false);        
        
        assertNonOptimal(objFunTrue.evaluate(new MockRow()));
        assertOptimal(objFunFalse.evaluate(new MockRow()));        
    } 
    
    @Test
    public void expMultiNulls_allowNullTrue() {
        
        RelationalExpression relExp 
            = new RelationalExpression(
                    new ConstantExpression(null),
                    RelationalOperator.EQUALS, 
                    new ConstantExpression(new NumericValue(1)));
        
        AndExpression andExp = new AndExpression(relExp, relExp);
        
        AndExpressionObjectiveFunction objFunTrue =
                new AndExpressionObjectiveFunction(andExp, true, true);

        AndExpressionObjectiveFunction objFunFalse =
                new AndExpressionObjectiveFunction(andExp, false, true);
        
        // both true and false are optimal, since NULL is allowed ...
        assertOptimal(objFunTrue.evaluate(new MockRow()));
        assertOptimal(objFunFalse.evaluate(new MockRow()));        
    }
    
    @Test
    public void expNull_allowNullTrue() {

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
        
        AndExpressionObjectiveFunction objFunTrue =
                new AndExpressionObjectiveFunction(andExp, true, true);
        
        AndExpressionObjectiveFunction objFunFalse =
                new AndExpressionObjectiveFunction(andExp, false, true);

        
        assertOptimal(objFunTrue.evaluate(new MockRow()));
        assertOptimal(objFunFalse.evaluate(new MockRow()));
    }      
}
