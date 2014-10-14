package org.schemaanalyst.unittest.data.generation.search.objective.row.value;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.NullValueObjectiveFunction;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertWorst;

@RunWith(JUnitParamsRunner.class)
public class TestNullValueObjectiveFunction {
    
    Object[] testValues() {
        return $(
                $(null, true, true),
                $(null, false, false),
                $(new NumericValue(), true, false),
                $(new NumericValue(), false, true)
                );
    }
    
    @Test
    @Parameters(method = "testValues")    
    public void testExpression(Value value, boolean allowNull, boolean optimal) {
        ObjectiveValue objVal = NullValueObjectiveFunction.compute(value, allowNull);

        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertWorst(objVal);
        }                
    }
}
