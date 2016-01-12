package org.schemaanalyst.unittest.data.generation.search.objective.row;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.row.AndExpressionRowObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.unittest.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestAndExpressionRowObjectiveFunction {

    Expression trueAtomicExp = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)), 
            RelationalOperator.EQUALS, 
            new ConstantExpression(new NumericValue(1)));
    
    Expression falseAtomicExp = new RelationalExpression(
            new ConstantExpression(new NumericValue(1)), 
            RelationalOperator.EQUALS, 
            new ConstantExpression(new NumericValue(2)));    

    Expression nullAtomicExp = new RelationalExpression(
            new ConstantExpression(null), 
            RelationalOperator.EQUALS, 
            new ConstantExpression(new NumericValue(2)));        
    
    AndExpression trueSingleExp = new AndExpression(trueAtomicExp);
    
    AndExpression falseSingleExp = new AndExpression(falseAtomicExp);
    
    AndExpression trueMultiExp = new AndExpression(trueAtomicExp, trueAtomicExp);
    
    AndExpression falseMultiExp1 = new AndExpression(falseAtomicExp, trueAtomicExp);
    
    AndExpression falseMultiExp2 = new AndExpression(falseAtomicExp, falseAtomicExp);    
    
    AndExpression falseMultiExp3 = new AndExpression(trueAtomicExp, falseAtomicExp);
    
    AndExpression nullSingleExp = new AndExpression(nullAtomicExp);
    
    AndExpression nullMultiExp = new AndExpression(trueAtomicExp, nullAtomicExp);
    
    Object[] testValues() {
        return $(
                $(trueSingleExp, true, false, true),
                $(trueSingleExp, false, false, false),
                $(trueSingleExp, true, true, true),
                $(trueSingleExp, false, true, false),
                
                $(falseSingleExp, true, false, false),
                $(falseSingleExp, false, false, true),                
                $(falseSingleExp, true, true, false),
                $(falseSingleExp, false, true, true),                
                
                $(trueMultiExp, true, false, true),
                $(trueMultiExp, false, false, false),
                $(trueMultiExp, true, true, true),
                $(trueMultiExp, false, true, false),           

                $(falseMultiExp1, true, false, false),
                $(falseMultiExp1, false, false, true),                
                $(falseMultiExp1, true, true, false),
                $(falseMultiExp1, false, true, true),
                
                $(falseMultiExp2, true, false, false),
                $(falseMultiExp2, false, false, true),                
                $(falseMultiExp2, true, true, false),
                $(falseMultiExp2, false, true, true),
                
                $(falseMultiExp3, true, false, false),
                $(falseMultiExp3, false, false, true),                
                $(falseMultiExp3, true, true, false),
                $(falseMultiExp3, false, true, true),
                
                $(nullSingleExp, true, false, false),
                $(nullSingleExp, false, false, false),                
                $(nullSingleExp, true, true, true),
                $(nullSingleExp, false, true, true),
                
                $(nullMultiExp, true, false, false),
                $(nullMultiExp, false, false, false),                
                $(nullMultiExp, true, true, true),
                $(nullMultiExp, false, true, true)                                 
        );
    }
        
    @Test
    @Parameters(method = "testValues")     
    public void testExpression(AndExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = new AndExpressionRowObjectiveFunction(
                exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                 
    }
}
