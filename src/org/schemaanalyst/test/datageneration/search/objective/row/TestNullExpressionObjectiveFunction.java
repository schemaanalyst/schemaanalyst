package org.schemaanalyst.test.datageneration.search.objective.row;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertWorst;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.NullExpressionObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.test.testutil.mock.MockRow;

@RunWith(JUnitParamsRunner.class)
public class TestNullExpressionObjectiveFunction {
    
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
    public void testExpression(Value value, boolean isNotNull, boolean nullIsTrue, boolean optimal) {

        ObjectiveFunction<Row> objFun = new NullExpressionObjectiveFunction(
                new NullExpression(new ConstantExpression(value), isNotNull), nullIsTrue);

        ObjectiveValue objVal = objFun.evaluate(new MockRow()); 
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertWorst(objVal);
        }                
    }
}
