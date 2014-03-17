package org.schemaanalyst.test._deprecated.datageneration.search.objective.row;

import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.ObjectiveValue;
import _deprecated.datageneration.search.objective.row.NullExpressionRowObjectiveFunction;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertWorst;

@RunWith(JUnitParamsRunner.class)
public class TestNullExpressionRowObjectiveFunction {
    
    Object[] testValues() {
        return $(
                $(null, false, true, true),
                $(null, false, false, false),
                $(new NumericValue(), false, true, false),
                $(new NumericValue(), false, false, true),
                $(null, true, true, false),
                $(null, true, false, true),
                $(new NumericValue(), true, true, true),
                $(new NumericValue(), true, false, false)
                );
    }
    
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(Value value, boolean isNotNull, boolean allowNull, boolean optimal) {

        ObjectiveFunction<Row> objFun = new NullExpressionRowObjectiveFunction(
                new NullExpression(new ConstantExpression(value), isNotNull), allowNull);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertWorst(objVal);
        }                
    }
}
