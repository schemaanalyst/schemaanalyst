package org.schemaanalyst.test.datageneration.search.directedrandom.data;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.Params.*;
import static org.schemaanalyst.test.testutil.assertion.DataAssert.assertDataEquals;

import java.util.Collections;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.datageneration.search.handler.ReferenceColumnHandler;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceColumnObjectiveFunction;
import org.schemaanalyst.test.testutil.mock.MockCellRandomiser;
import org.schemaanalyst.test.testutil.mock.MockRandom;
import org.schemaanalyst.test.testutil.mock.TwoColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestReferenceColumnHandler {

    
    Object[] testValues() {
        return $(
                // satisfies ...                                
                $(d(r(1, 2)), d(), d(r(2, 2)), I(), i(0), true, false),
                
                // --- trivially satisfies
                $(d(r(1, 1), r(2, 2)), d(), d(r(1, 1), r(2, 2)), I(), i(0), true, false),                

                // falsifies ...                                
                $(d(r(1, 1)), d(), d(r(10, 1)), I(10), i(), false, false),                
                // --- trivially falsifies
                $(d(r(1, 2), r(3, 4)), d(), d(r(1, 2), r(3, 4)), I(), i(0), false, false)                
                
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
        
        TwoColumnMockDatabase database = new TwoColumnMockDatabase();        
        
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
        
        MockCellRandomiser cellRandomiser = new MockCellRandomiser(randomCellValues);
        MockRandom random = new MockRandom(randomInts);
        
        ReferenceColumnObjectiveFunction objFun = new ReferenceColumnObjectiveFunction(
                Collections.singletonList(database.column1),
                Collections.singletonList(database.column2),
                database.state, "", goalIsToSatisfy, allowNull);
        
        ReferenceColumnHandler rch = new ReferenceColumnHandler(objFun, random, cellRandomiser);
        rch.attemptToFindSolution(database.data);
        
        assertDataEquals(expectedFinalDataValues, database.data);
    }    
    
}
