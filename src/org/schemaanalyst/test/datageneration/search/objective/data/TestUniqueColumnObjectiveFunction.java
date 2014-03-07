package org.schemaanalyst.test.datageneration.search.objective.data;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.UniqueColumnObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;
import org.schemaanalyst.test.testutil.mock.TwoColumnMockDatabase;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.Params.*;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestUniqueColumnObjectiveFunction {
    
    Object[] oneColumnTestValues() {
        return $(
                
                $(EMPTY, EMPTY, true, false, false, 0, 0),
                $(EMPTY, EMPTY, true, true, false, 0, 0),
                $(EMPTY, EMPTY, false, false, false, 0, 0),
                $(EMPTY, EMPTY, false, true, false, 0, 0),                

                $(EMPTY, d(r(1)), true, false, false, 0, 0),
                $(EMPTY, d(r(1)), true, true, false, 0, 0),
                $(EMPTY, d(r(1)), false, false, false, 0, 0),
                $(EMPTY, d(r(1)), false, true, false, 0, 0),         
                
                // non-uniqueness of the state should be ignored
                $(d(r(1)), d(r(2), r(2)), true, false, true, 1, 0),
                $(d(r(1)), d(r(2), r(2)), true, true, true, 1, 0),
                $(d(r(1)), d(r(2), r(2)), false, false, false, 1, 0),
                $(d(r(1)), d(r(2), r(2)), false, true, false, 1, 0),                  
                
                $(d(r(2), r(3)), EMPTY, true, false, true, 2, 0),
                $(d(r(2), r(3)), EMPTY, true, true, true, 2, 0),
                $(d(r(2), r(3)), EMPTY, false, false, false, 2, 0),
                $(d(r(2), r(3)), EMPTY, false, true, false, 2, 0),

                // one row in data clashes with one row in the state
                $(d(r(1), r(2)), d(r(2), r(3)), true, false, false, 1, 1),
                $(d(r(1), r(2)), d(r(2), r(3)), true, true, false, 1, 1),
                $(d(r(1), r(2)), d(r(2), r(3)), false, false, false, 1, 1),
                $(d(r(1), r(2)), d(r(2), r(3)), false, true, false, 1, 1),                
                
                $(d(r(2), r(2)), EMPTY, true, false, false, 1, 1),
                $(d(r(2), r(2)), EMPTY, true, true, false, 1, 1),
                $(d(r(2), r(2)), EMPTY, false, false, false, 1, 1),
                $(d(r(2), r(2)), EMPTY, false, true, false, 1, 1),

                $(d(r(NULL), r(NULL)), EMPTY, true, false, false, 0, 2),
                $(d(r(NULL), r(NULL)), EMPTY, true, true, true, 2, 0),
                $(d(r(NULL), r(NULL)), EMPTY, false, false, false, 2, 0),
                $(d(r(NULL), r(NULL)), EMPTY, false, true, true, 0, 2),
                
                $(d(r(1), r(NULL)), d(r(2), r(3)), true, false, false, 1, 1),
                $(d(r(1), r(NULL)), d(r(2), r(3)), true, true, true, 2, 0),
                $(d(r(1), r(NULL)), d(r(2), r(3)), false, false, false, 2, 0),
                $(d(r(1), r(NULL)), d(r(2), r(3)), false, true, false, 1, 1),           
                
                $(d(r(NULL), r(1)), d(r(2), r(3)), true, false, false, 1, 1),
                $(d(r(NULL), r(1)), d(r(2), r(3)), true, true, true, 2, 0),
                // due to not-accepting null for the goal (not to satisfy), all rows here are rejected
                $(d(r(NULL), r(1)), d(r(2), r(3)), false, false, false, 2, 0),
                $(d(r(NULL), r(1)), d(r(2), r(3)), false, true, false, 1, 1)
                ); 
    }

    @Test
    @Parameters(method = "oneColumnTestValues")
    public void oneColumnTests(
            Integer[] dataValues, Integer[] stateValues,
            boolean goalIsToSatisfy, boolean allowNull, boolean optimal,
            int numAcceptedRows, int numRejectedRows) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        
        Data data = database.createData(dataValues.length);
        Data state = database.createState(stateValues.length);
        
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);

        List<Column> columns = new ArrayList<>();
        columns.add(database.column);        
        
        evaluate(goalIsToSatisfy, allowNull, optimal, 
                 numAcceptedRows, numRejectedRows,
                 database.table, columns, data, state);    
    }

    Object[] twoColumnTestValues() {
        return $(
                $(EMPTY, EMPTY, true, false, false, 0, 0),
                $(EMPTY, EMPTY, true, true, false, 0, 0),
                $(EMPTY, EMPTY, false, false, false, 0, 0),
                $(EMPTY, EMPTY, false, true, false, 0, 0),

                $(d(r(1, 1), r(2, 2)), EMPTY, true, false, true, 2, 0),
                $(d(r(1, 1), r(2, 2)), EMPTY, true, true, true, 2, 0),
                $(d(r(1, 1), r(2, 2)), EMPTY, false, false, false, 2, 0),
                $(d(r(1, 1), r(2, 2)), EMPTY, false, true, false, 2, 0),

                $(d(r(1, 1)), d(r(2, 2)), true, false, true, 1, 0),
                $(d(r(1, 1)), d(r(2, 2)), true, true, true, 1, 0),
                $(d(r(1, 1)), d(r(2, 2)), false, false, false, 1, 0),
                $(d(r(1, 1)), d(r(2, 2)), false, true, false, 1, 0),

                $(d(r(1, 1), r(1, 1)), EMPTY, true, false, false, 1, 1),
                $(d(r(1, 1), r(1, 1)), EMPTY, true, true, false, 1, 1),
                $(d(r(1, 1), r(1, 1)), EMPTY, false, false, false, 1, 1),
                $(d(r(1, 1), r(1, 1)), EMPTY, false, true, false, 1, 1),

                $(d(r(1, 1)), d(r(1, 1)), true, false, false, 0, 1),
                $(d(r(1, 1)), d(r(1, 1)), true, true, false, 0, 1),
                $(d(r(1, 1)), d(r(1, 1)), false, false, true, 0, 1),
                $(d(r(1, 1)), d(r(1, 1)), false, true, true, 0, 1),

                $(d(r(1, NULL), r(1, 1)), EMPTY, true, false, false, 1, 1),
                $(d(r(1, NULL), r(1, 1)), EMPTY, true, true, true, 2, 0),
                $(d(r(1, NULL), r(1, 1)), EMPTY, false, false, false, 2, 0),
                $(d(r(1, NULL), r(1, 1)), EMPTY, false, true, false, 1, 1)
                );
    }

    @Test
    @Parameters(method = "twoColumnTestValues")
    public void twoColumnTests(Integer[] dataValues, Integer[] stateValues,
            boolean goalIsToSatisfy, boolean allowNull, boolean optimal, 
            int numAcceptedRows, int numRejectedRows) {

        TwoColumnMockDatabase database = new TwoColumnMockDatabase();

        Data data = database.createData(dataValues.length / 2);
        Data state = database.createState(stateValues.length / 2);
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);

        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        columns.add(database.column2);

        evaluate(goalIsToSatisfy, allowNull, optimal, 
                numAcceptedRows, numRejectedRows,
                database.table, columns, data, state); 
    }
    
    private void evaluate(
            boolean goalIsToSatisfy, boolean allowNull,
            boolean optimal, int numAcceptedRows, int numRejectedRows, 
            Table table, List<Column> columns,
            Data data, Data state) {
        UniqueColumnObjectiveFunction objFun = 
                new UniqueColumnObjectiveFunction(
                        table, columns, state, "", goalIsToSatisfy, allowNull);
        ObjectiveValue objVal = objFun.evaluate(data);

        if (optimal) {
            assertOptimal(objVal);
            
            if (goalIsToSatisfy) {
                assertEquals(
                        "No. of rejected rows should be zero", 
                        0, objFun.getFalsifyingRows().size());
            } else {
                assertEquals(
                        "No. of accepted rows should be zero", 
                        0, objFun.getSatisfyingRows().size());
            }
        } else {
            assertNonOptimal(objVal);
        }
        
        assertEquals("No. of accepted rows should be " + numAcceptedRows 
                + " (list is " + objFun.getSatisfyingRows() + ")",
                numAcceptedRows, objFun.getSatisfyingRows().size());        
        
        assertEquals("No. of rejected rows should be " + numRejectedRows 
                + " (list is " + objFun.getFalsifyingRows() + ")",
                numRejectedRows, objFun.getFalsifyingRows().size());
    }   
}
