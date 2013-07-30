package org.schemaanalyst.test.datageneration.search.objective.data;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.NullColumnObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertEquivalent;

@RunWith(JUnitParamsRunner.class)
public class TestNullColumnObjectiveFunction {

    OneColumnMockDatabase database = new OneColumnMockDatabase();
    Data data = database.createData(2);

    ObjectiveValue optimal = ObjectiveValue.optimalObjectiveValue();
    SumOfMultiObjectiveValue oneOff = new SumOfMultiObjectiveValue();
    SumOfMultiObjectiveValue twoOff = new SumOfMultiObjectiveValue();
    
    public TestNullColumnObjectiveFunction() {
        database = new OneColumnMockDatabase();
        data = database.createData(2);
        
       oneOff.add(ObjectiveValue.worstObjectiveValue());  
       twoOff.add(ObjectiveValue.worstObjectiveValue());  
       twoOff.add(ObjectiveValue.worstObjectiveValue());  
    }
    
    Object[] testValues() {
        return $(
                $(null, null, true, optimal),
                $(null, 1, true, oneOff),
                $(1, 1, true, twoOff),
                $(null, null, false, twoOff),
                $(null, 1, false, oneOff),
                $(1, 1, false, optimal)
                );
    }
    
    @Test
    @Parameters(method = "testValues")     
    public void test(Integer val1, Integer val2, boolean goalIsToSatisfy, ObjectiveValue expected) {
        NullColumnObjectiveFunction objFun = 
                new NullColumnObjectiveFunction(database.column, "", goalIsToSatisfy);
        
        database.setDataValues(val1, val2);
        assertEquivalent(expected, objFun.evaluate(data));                   
    }
}
