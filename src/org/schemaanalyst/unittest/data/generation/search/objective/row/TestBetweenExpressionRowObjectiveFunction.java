package org.schemaanalyst.unittest.data.generation.search.objective.row;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.row.BetweenExpressionRowObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.unittest.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestBetweenExpressionRowObjectiveFunction {

    BetweenExpression trueBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    false, false);

    BetweenExpression falseBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(1)),
                    false, false);    

    BetweenExpression trueNotBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(1)),
                    true, false);        
    
    BetweenExpression falseNotBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    true, false);
    
    BetweenExpression subjectNullBetweenExp = new BetweenExpression(
                    new ConstantExpression(null),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    false, false);    

    BetweenExpression subjectNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(null),
            new ConstantExpression(new NumericValue(0)), 
            new ConstantExpression(new NumericValue(2)),
            true, false);        
    
    BetweenExpression lhsOperandNullBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(null), 
            new ConstantExpression(new NumericValue(2)),
            false, false);       
    
    BetweenExpression lhsOperandNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(null), 
            new ConstantExpression(new NumericValue(2)),
            true, false);         
    
    BetweenExpression rhsOperandNullBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(new NumericValue(2)),
            new ConstantExpression(null),             
            false, false);       
    
    BetweenExpression rhsOperandNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(new NumericValue(2)),
            new ConstantExpression(null),             
            true, false);     
    
    BetweenExpression trueBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    false, false);

    BetweenExpression falseBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    false, false);     

    BetweenExpression trueNotBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    true, false);       
    
    BetweenExpression falseNotBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(1)), 
                    new ConstantExpression(new NumericValue(0)),
                    true, false);
    
    BetweenExpression trueSymmetricBetween = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    false, true); 
    
    BetweenExpression trueSymmetricNotBetween = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(3)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    true, true);    
    
    BetweenExpression falseSymmetricNotBetween = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    true, true);      
    
    Object[] testValues() {
        return $(
                $(trueBetweenExp, true, false, true),
                $(trueBetweenExp, false, false, false),
                $(trueBetweenExp, true, true, true),
                $(trueBetweenExp, false, true, false),
               
                $(falseBetweenExp, true, false, false),
                $(falseBetweenExp, false, false, true),                
                $(falseBetweenExp, true, true, false),
                $(falseBetweenExp, false, true, true),
                
                $(trueNotBetweenExp, true, false, true),
                $(trueNotBetweenExp, false, false, false),
                $(trueNotBetweenExp, true, true, true),
                $(trueNotBetweenExp, false, true, false),
               
                $(falseNotBetweenExp, true, false, false),
                $(falseNotBetweenExp, false, false, true),                
                $(falseNotBetweenExp, true, true, false),
                $(falseNotBetweenExp, false, true, true),
                
                $(subjectNullBetweenExp, true, false, false),
                $(subjectNullBetweenExp, false, false, false),
                $(subjectNullBetweenExp, true, true, true),
                $(subjectNullBetweenExp, false, true, true),                
                                
                $(subjectNullNotBetweenExp, true, false, false),                
                $(subjectNullNotBetweenExp, false, false, false),
                $(subjectNullNotBetweenExp, true, true, true),                
                $(subjectNullNotBetweenExp, false, true, true),                
                
                $(lhsOperandNullBetweenExp, true, false, false),
                $(lhsOperandNullBetweenExp, false, false, false),
                $(lhsOperandNullBetweenExp, true, true, true),
                $(lhsOperandNullBetweenExp, false, true, true),                

                $(lhsOperandNullNotBetweenExp, true, false, false),
                $(lhsOperandNullNotBetweenExp, false, false, false),
                $(lhsOperandNullNotBetweenExp, true, true, true),
                $(lhsOperandNullNotBetweenExp, false, true, true),                

                $(rhsOperandNullBetweenExp, true, false, false),
                $(rhsOperandNullBetweenExp, false, false, true),
                $(rhsOperandNullBetweenExp, true, true, false),
                $(rhsOperandNullBetweenExp, false, true, true),                

                $(rhsOperandNullNotBetweenExp, true, false, true),
                $(rhsOperandNullNotBetweenExp, false, false, false),
                $(rhsOperandNullNotBetweenExp, true, true, true),
                $(rhsOperandNullNotBetweenExp, false, true, false),                
                
                $(trueBetweenInverseOperandsExp, true, false, false),
                $(trueBetweenInverseOperandsExp, false, false, true),
                $(trueBetweenInverseOperandsExp, true, true, false),
                $(trueBetweenInverseOperandsExp, false, true, true),
               
                $(falseBetweenInverseOperandsExp, true, false, false),
                $(falseBetweenInverseOperandsExp, false, false, true),                
                $(falseBetweenInverseOperandsExp, true, true, false),
                $(falseBetweenInverseOperandsExp, false, true, true),
                
                $(trueNotBetweenInverseOperandsExp, true, false, true),
                $(trueNotBetweenInverseOperandsExp, false, false, false),
                $(trueNotBetweenInverseOperandsExp, true, true, true),
                $(trueNotBetweenInverseOperandsExp, false, true, false),
               
                $(falseNotBetweenInverseOperandsExp, true, false, true),
                $(falseNotBetweenInverseOperandsExp, false, false, false),                
                $(falseNotBetweenInverseOperandsExp, true, true, true),
                $(falseNotBetweenInverseOperandsExp, false, true, false),
                
                $(trueSymmetricBetween, true, false, true),
                $(trueSymmetricBetween, false, false, false),                
                $(trueSymmetricBetween, true, true, true),
                $(trueSymmetricBetween, false, true, false),
                
                $(trueSymmetricNotBetween, true, false, true),
                $(trueSymmetricNotBetween, false, false, false),                
                $(trueSymmetricNotBetween, true, true, true),
                $(trueSymmetricNotBetween, false, true, false),
                
                $(falseSymmetricNotBetween, true, false, false),
                $(falseSymmetricNotBetween, false, false, true),                
                $(falseSymmetricNotBetween, true, true, false),
                $(falseSymmetricNotBetween, false, true, true)                 
         );
    }    
        
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(BetweenExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = new BetweenExpressionRowObjectiveFunction(
                exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                
    }
}
