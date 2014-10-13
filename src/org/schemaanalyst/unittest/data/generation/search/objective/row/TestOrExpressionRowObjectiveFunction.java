package org.schemaanalyst.unittest.data.generation.search.objective.row;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.row.OrExpressionRowObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.unittest.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestOrExpressionRowObjectiveFunction {

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
    
    OrExpression trueSingleExp = new OrExpression(trueAtomicExp);
    
    OrExpression falseSingleExp = new OrExpression(falseAtomicExp);
    
    OrExpression trueMultiExp1 = new OrExpression(trueAtomicExp, trueAtomicExp);
    
    OrExpression trueMultiExp2 = new OrExpression(falseAtomicExp, trueAtomicExp);
    
    OrExpression trueMultiExp3 = new OrExpression(trueAtomicExp, falseAtomicExp);
    
    OrExpression falseMultiExp = new OrExpression(falseAtomicExp, falseAtomicExp);    
    
    OrExpression nullSingleExp = new OrExpression(nullAtomicExp);
    
    OrExpression nullMultiExp = new OrExpression(trueAtomicExp, nullAtomicExp);
    
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
                
                $(trueMultiExp1, true, false, true),
                $(trueMultiExp1, false, false, false),
                $(trueMultiExp1, true, true, true),
                $(trueMultiExp1, false, true, false),           

                $(trueMultiExp2, true, false, true),
                $(trueMultiExp2, false, false, false),
                $(trueMultiExp2, true, true, true),
                $(trueMultiExp2, false, true, false),           

                $(trueMultiExp3, true, false, true),
                $(trueMultiExp3, false, false, false),
                $(trueMultiExp3, true, true, true),
                $(trueMultiExp3, false, true, false),           
                
                $(falseMultiExp, true, false, false),
                $(falseMultiExp, false, false, true),                
                $(falseMultiExp, true, true, false),
                $(falseMultiExp, false, true, true),
                                
                $(nullSingleExp, true, false, false),
                $(nullSingleExp, false, false, false),                
                $(nullSingleExp, true, true, true),
                $(nullSingleExp, false, true, true),
                
                $(nullMultiExp, true, false, true),
                $(nullMultiExp, false, false, false),                
                $(nullMultiExp, true, true, true),
                $(nullMultiExp, false, true, false)                                 
        );
    }
        
    @Test
    @Parameters(method = "testValues")     
    public void testExpression(OrExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = new OrExpressionRowObjectiveFunction(
                exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                 
    }
}
