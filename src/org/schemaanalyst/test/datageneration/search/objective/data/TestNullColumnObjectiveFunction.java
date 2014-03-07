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
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;

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
                $(null, null, true, optimal, 2, 0),
                $(null, 1, true, oneOff, 1, 1),
                $(1, 1, true, twoOff, 0, 2),
                $(null, null, false, twoOff, 2, 0),
                $(null, 1, false, oneOff, 1, 1),
                $(1, 1, false, optimal, 0, 2)
                );
    }
    
    @Test
    @Parameters(method = "testValues")     
    public void test(Integer val1, Integer val2, 
                     boolean goalIsToSatisfy, ObjectiveValue expected, 
                     int numAcceptedRows, int numRejectedRows) {
        NullColumnObjectiveFunction objFun = 
                new NullColumnObjectiveFunction(
                        database.table, database.column, "", goalIsToSatisfy);
        
        database.setDataValues(val1, val2);
        assertEquivalent(expected, objFun.evaluate(data));                   

        assertEquals("Number of accepted rows should be " + numAcceptedRows, 
                numAcceptedRows, objFun.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be " + numRejectedRows, 
                numRejectedRows, objFun.getFalsifyingRows().size());
    }
    
    @Test
    public void testNoRows() {
        NullColumnObjectiveFunction objFunTrue = 
                new NullColumnObjectiveFunction(
                        database.table, database.column, "", true);
        
        assertNonOptimal(objFunTrue.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunTrue.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunTrue.getFalsifyingRows().size());  
        
        NullColumnObjectiveFunction objFunFalse = 
                new NullColumnObjectiveFunction(
                        database.table, database.column, "", false);
        
        assertNonOptimal(objFunFalse.evaluate(new Data()));  
        
        assertEquals("Number of accepted rows should be zero", 
                0, objFunFalse.getSatisfyingRows().size());        
        
        assertEquals("Number of rejected rows should be zero", 
                0, objFunFalse.getFalsifyingRows().size());             
    }
}
