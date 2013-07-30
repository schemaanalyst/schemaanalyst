package org.schemaanalyst.test.datageneration.search.objective.value;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertWorst;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;

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
    public void testExpression(Value value, boolean nullIsSatisfy, boolean optimal) {
        ObjectiveValue objVal = NullValueObjectiveFunction.compute(value, nullIsSatisfy);

        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertWorst(objVal);
        }                
    }
}
