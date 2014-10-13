package org.schemaanalyst.unittest.data.generation.search.objective.row.value;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.value.EqualsMultiValueObjectiveFunction;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.fail;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.unittest.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestEqualsMultiValueObjectiveFunction {

    List<Value> atomicList1, atomicList2, multiList1, multiList2, containsNullList, allNullList;
    
    public TestEqualsMultiValueObjectiveFunction() {
        atomicList1 = new ArrayList<>();
        atomicList1.add(new NumericValue(1));
        
        atomicList2 = new ArrayList<>();
        atomicList2.add(new NumericValue(2));
        
        multiList1 = new ArrayList<>();
        multiList1.add(new NumericValue(1));
        multiList1.add(new NumericValue(2));
        
        multiList2 = new ArrayList<>();
        multiList2.add(new NumericValue(2));
        multiList2.add(new NumericValue(1));
        
        containsNullList = new ArrayList<>();
        containsNullList.add(new NumericValue(1));
        containsNullList.add(null);
        
        allNullList = new ArrayList<>();
        allNullList.add(null);
        allNullList.add(null);
    }

    Object[] testValues() {
        return $(
                $(atomicList1, true, atomicList1, true, true),
                $(atomicList1, false, atomicList1, true, false),
                $(atomicList1, true, atomicList1, false, true),
                $(atomicList1, false, atomicList1, false, false),

                $(atomicList1, true, atomicList2, true, false),
                $(atomicList1, false, atomicList2, true, true),
                $(atomicList1, true, atomicList2, false, false),
                $(atomicList1, false, atomicList2, false, true),                
                
                $(multiList1, true, multiList1, true, true),
                $(multiList1, false, multiList1, true, false),
                $(multiList1, true, multiList1, false, true),
                $(multiList1, false, multiList1, false, false),
                
                $(multiList1, true, multiList2, true, false),
                $(multiList1, false, multiList2, true, true),
                $(multiList1, true, multiList2, false, false),
                $(multiList1, false, multiList2, false, true),
                
                $(multiList1, true, containsNullList, true, true),
                $(multiList1, false, containsNullList, true, true),
                $(multiList1, true, containsNullList, false, false),
                $(multiList1, false, containsNullList, false, false),                

                $(containsNullList, true, containsNullList, true, true),
                $(containsNullList, false, containsNullList, true, true),
                $(containsNullList, true, containsNullList, false, false),
                $(containsNullList, false, containsNullList, false, false),
                
                $(allNullList, true, allNullList, true, true),
                $(allNullList, false, allNullList, true, true),
                $(allNullList, true, allNullList, false, false),
                $(allNullList, false, allNullList, false, false)                                
                );
    }    
    
    
    @Test
    @Parameters(method = "testValues")       
    public void test(List<Value> lhs, boolean equals, List<Value> rhs, 
                     boolean allowNull, boolean optimal) {
        
        ObjectiveValue objVal = EqualsMultiValueObjectiveFunction.compute(lhs, equals, rhs, allowNull);
        
        if (optimal) {
            assertOptimal(objVal);            
        } else {
            assertNonOptimal(objVal);
        }        
    }
    
    Object[] exceptionValues() {
        return $(
                $(atomicList1, true, multiList1, true),
                $(atomicList1, false, multiList1, false),
                $(atomicList1, true, multiList1, false),
                $(atomicList1, false, multiList1, true),
                $(multiList1, true, atomicList1, true),
                $(multiList1, false, atomicList1, false),
                $(multiList1, true, atomicList1, false),
                $(multiList1, false, atomicList1, true)                
                );
    }    
    
    @Test
    @Parameters(method = "exceptionValues")   
    public void testNonEqualSizedLists(List<Value> lhs, boolean equals, List<Value> rhs, 
                                       boolean allowNull) {
        boolean exceptionThrown = false;
        try {
            EqualsMultiValueObjectiveFunction.compute(lhs, equals, rhs, false);
        } catch (ObjectiveFunctionException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            fail("Expected ObjectiveFunctionException to be thrown for unequal lists " 
                    + lhs + " and " + rhs);
        }
    }
}
