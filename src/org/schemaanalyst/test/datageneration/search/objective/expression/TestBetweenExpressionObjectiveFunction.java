package org.schemaanalyst.test.datageneration.search.objective.expression;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.BetweenExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

@RunWith(JUnitParamsRunner.class)
public class TestBetweenExpressionObjectiveFunction {

    BetweenExpression trueBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    false);

    BetweenExpression falseBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(1)),
                    false);    

    BetweenExpression trueNotBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(1)),
                    true);        
    
    BetweenExpression falseNotBetweenExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    true);
    
    BetweenExpression subjectNullBetweenExp = new BetweenExpression(
                    new ConstantExpression(null),
                    new ConstantExpression(new NumericValue(0)), 
                    new ConstantExpression(new NumericValue(2)),
                    false);    

    BetweenExpression subjectNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(null),
            new ConstantExpression(new NumericValue(0)), 
            new ConstantExpression(new NumericValue(2)),
            true);        
    
    BetweenExpression lhsOperandNullBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(null), 
            new ConstantExpression(new NumericValue(2)),
            false);       
    
    BetweenExpression lhsOperandNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(null), 
            new ConstantExpression(new NumericValue(2)),
            true);         
    
    BetweenExpression rhsOperandNullBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(new NumericValue(2)),
            new ConstantExpression(null),             
            false);       
    
    BetweenExpression rhsOperandNullNotBetweenExp = new BetweenExpression(
            new ConstantExpression(new NumericValue(1)),
            new ConstantExpression(new NumericValue(2)),
            new ConstantExpression(null),             
            true);     
    
    BetweenExpression trueBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    false);

    BetweenExpression falseBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(1)), 
                    new ConstantExpression(new NumericValue(0)),
                    false);     

    BetweenExpression trueNotBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(1)), 
                    new ConstantExpression(new NumericValue(0)),
                    true);       
    
    BetweenExpression falseNotBetweenInverseOperandsExp = 
            new BetweenExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)), 
                    new ConstantExpression(new NumericValue(0)),
                    true);
    
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
                $(rhsOperandNullBetweenExp, false, false, false),
                $(rhsOperandNullBetweenExp, true, true, true),
                $(rhsOperandNullBetweenExp, false, true, true),                

                $(rhsOperandNullNotBetweenExp, true, false, false),
                $(rhsOperandNullNotBetweenExp, false, false, false),
                $(rhsOperandNullNotBetweenExp, true, true, true),
                $(rhsOperandNullNotBetweenExp, false, true, true),                
                
                $(trueBetweenInverseOperandsExp, true, false, true),
                $(trueBetweenInverseOperandsExp, false, false, false),
                $(trueBetweenInverseOperandsExp, true, true, true),
                $(trueBetweenInverseOperandsExp, false, true, false),
               
                $(falseBetweenInverseOperandsExp, true, false, false),
                $(falseBetweenInverseOperandsExp, false, false, true),                
                $(falseBetweenInverseOperandsExp, true, true, false),
                $(falseBetweenInverseOperandsExp, false, true, true),
                
                $(trueNotBetweenInverseOperandsExp, true, false, true),
                $(trueNotBetweenInverseOperandsExp, false, false, false),
                $(trueNotBetweenInverseOperandsExp, true, true, true),
                $(trueNotBetweenInverseOperandsExp, false, true, false),
               
                $(falseNotBetweenInverseOperandsExp, true, false, false),
                $(falseNotBetweenInverseOperandsExp, false, false, true),                
                $(falseNotBetweenInverseOperandsExp, true, true, false),
                $(falseNotBetweenInverseOperandsExp, false, true, true)           
         );
    }    
        
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(BetweenExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = new BetweenExpressionObjectiveFunction(
                exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                
    }
}
