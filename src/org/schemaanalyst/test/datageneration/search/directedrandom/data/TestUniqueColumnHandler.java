package org.schemaanalyst.test.datageneration.search.directedrandom.data;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.Params.*;
import static org.schemaanalyst.test.testutil.assertion.DataAssert.assertDataEquals;

import java.util.Collections;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.datageneration.search.handler.UniqueColumnHandler;
import org.schemaanalyst.datageneration.search.objective.data.UniqueColumnObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.MockCellRandomiser;
import org.schemaanalyst.test.testutil.mock.MockRandom;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestUniqueColumnHandler {

    
    Object[] testValues() {
        return $(
                // satisfies ...                                
                $(d(r(1), r(2), r(2)), d(), d(I(1), r(2), r(3)), I(3), i(), true, false),                
                $(d(r(2), r(2), r(2)), d(), d(I(2), r(1), r(3)), I(1, 3), i(), true, false),
                                
                // --- already satisfied
                $(d(r(1), r(2), r(3)), d(), d(I(1), r(2), r(3)), I(), i(), true, false),
                
                // falsifies...
                $(d(r(1), r(2), r(1)), d(r(1), r(3)), d(r(1), r(1), r(1)), I(), i(0), false, false),
                $(d(r(1), r(2), r(1)), d(r(1), r(3)), d(r(1), r(3), r(1)), I(), i(1), false, false),
                               
                // --- does not falsify last value due to it being non-unique with respect to previous
                //     data row not state row
                $(d(r(4), r(5), r(4)), d(r(1), r(3)), d(I(1), r(3), r(4)), I(), i(0, 1), false, false),
                
                // --- already falsified
                $(d(r(1), r(1), r(1)), d(r(1)), d(I(1), r(1), r(1)), I(), i(), false, false)
                );
    }
  
    
    // When the test code is harder to write than the object it's testing ....
    // This tests that the row values get changed as expected by the ExpressionConstraintHandler
    @Test 
    @Parameters(method = "testValues")    
    public void test(Integer[] dataValues,
            Integer[] stateValues,
            Integer[] expectedFinalDataValues,
            Integer[] randomCellValues,
            int[] randomInts,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        
        OneColumnMockDatabase database = new OneColumnMockDatabase();        
                       
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
        
        MockCellRandomiser cellRandomiser = new MockCellRandomiser(randomCellValues);
        MockRandom random = new MockRandom(randomInts);

        UniqueColumnObjectiveFunction objFun = 
                new UniqueColumnObjectiveFunction(
                        database.table,
                        Collections.singletonList(database.column), 
                        database.state, 
                        "", goalIsToSatisfy, allowNull);
        
        UniqueColumnHandler uch = new UniqueColumnHandler(
                objFun, random, cellRandomiser);
        uch.attemptToFindSolution(database.data);
        
        assertDataEquals(expectedFinalDataValues, database.data);
    }    
    
}
