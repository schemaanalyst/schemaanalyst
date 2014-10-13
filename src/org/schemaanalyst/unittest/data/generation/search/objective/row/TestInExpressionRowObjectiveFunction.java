package org.schemaanalyst.unittest.data.generation.search.objective.row;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.row.InExpressionRowObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.unittest.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestInExpressionRowObjectiveFunction {

    InExpression trueInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ListExpression(new ConstantExpression(new NumericValue(0)), 
                                       new ConstantExpression(new NumericValue(1))),
                    false);    
    
    InExpression falseInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ListExpression(new ConstantExpression(new NumericValue(0)), 
                                       new ConstantExpression(new NumericValue(1))),
                    false);     
    
    InExpression trueNotInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(2)),
                    new ListExpression(new ConstantExpression(new NumericValue(0)), 
                                       new ConstantExpression(new NumericValue(1))),
                    true);    
    
    InExpression falseNotInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ListExpression(new ConstantExpression(new NumericValue(0)), 
                                       new ConstantExpression(new NumericValue(1))),
                    true);
    
    InExpression listElementNullTrueInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    false);    
    
    InExpression listElementNullFalseInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(0)),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    false);    
    
    InExpression subjectNullInExp = 
            new InExpression(
                    new ConstantExpression(null),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    false);    
    
    InExpression listElementNullTrueNotInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    true);    
    
    InExpression listElementNullFalseNotInExp = 
            new InExpression(
                    new ConstantExpression(new NumericValue(0)),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    true);    
    
    InExpression subjectNullNotInExp = 
            new InExpression(
                    new ConstantExpression(null),
                    new ListExpression(new ConstantExpression(null), 
                                       new ConstantExpression(new NumericValue(1))),
                    true);     
    
    InExpression emptyInExp = 
            new InExpression(new ConstantExpression(new NumericValue()), new ListExpression(), false);    
    
    InExpression emptyNotInExp = 
            new InExpression(new ConstantExpression(new NumericValue()), new ListExpression(), true);    
    
    Object[] testValues() {
        return $(
                $(trueInExp, true, false, true),
                $(trueInExp, false, false, false),
                $(trueInExp, true, true, true),
                $(trueInExp, false, true, false),
                
                $(falseInExp, true, false, false),
                $(falseInExp, false, false, true),                
                $(falseInExp, true, true, false),
                $(falseInExp, false, true, true),
                
                $(trueNotInExp, true, false, true),
                $(trueNotInExp, false, false, false),
                $(trueNotInExp, true, true, true),
                $(trueNotInExp, false, true, false),
               
                $(falseNotInExp, true, false, false),
                $(falseNotInExp, false, false, true),                
                $(falseNotInExp, true, true, false),
                $(falseNotInExp, false, true, true),
                                
                $(listElementNullTrueInExp, true, false, true),
                $(listElementNullTrueInExp, true, true, true),
                $(listElementNullTrueInExp, false, false, false),
                $(listElementNullTrueInExp, false, true, false),
                
                $(listElementNullFalseInExp, true, false, false),
                $(listElementNullFalseInExp, true, true, true),
                $(listElementNullFalseInExp, false, false, false),
                $(listElementNullFalseInExp, false, true, true),
                
                $(subjectNullInExp, true, false, false),
                $(subjectNullInExp, true, true, true),
                $(subjectNullInExp, false, false, false),
                $(subjectNullInExp, false, true, true),
                
                $(listElementNullTrueNotInExp, true, false, false),
                $(listElementNullTrueNotInExp, true, true, false),
                $(listElementNullTrueNotInExp, false, false, true),
                $(listElementNullTrueNotInExp, false, true, true),
                
                $(listElementNullFalseNotInExp, true, false, false),
                $(listElementNullFalseNotInExp, true, true, true),
                $(listElementNullFalseNotInExp, false, false, false),
                $(listElementNullFalseNotInExp, false, true, true),
                
                $(subjectNullNotInExp, true, false, false),
                $(subjectNullNotInExp, true, true, true),
                $(subjectNullNotInExp, false, false, false),
                $(subjectNullNotInExp, false, true, true),
                
                $(emptyInExp, true, true, false),
                $(emptyInExp, false, true, true),
                $(emptyInExp, true, false, false),
                $(emptyInExp, false, false, true),

                $(emptyNotInExp, true, true, true),
                $(emptyNotInExp, false, true, false),
                $(emptyNotInExp, true, false, true),
                $(emptyNotInExp, false, false, false)                
                );
    }
    
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(InExpression exp, boolean goalIsToSatisfy, boolean allowNull, boolean optimal) {
        ObjectiveFunction<Row> objFun = new InExpressionRowObjectiveFunction(
                exp, goalIsToSatisfy, allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }                
    }    
}
