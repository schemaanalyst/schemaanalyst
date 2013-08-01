package org.schemaanalyst.test.datageneration.search.objective.data;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.UniqueObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;
import org.schemaanalyst.test.testutil.mock.TwoColumnMockDatabase;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestUniqueObjectiveFunction {

    Integer[] empty = {};

    Integer[] one = { 1 };
    
    Integer[] two_three = { 2, 
                            3 };

    Integer[] one_two = { 1, 
                         2 };      
    
    Integer[] two_two = { 2, 
                          2 };

    Integer[] null_null = { null, 
                            null };

    Integer[] one_null = { 1, 
                           null };

    Integer[] null_one = { null, 
                           1 };    
    
    Object[] oneColumnTestValues() {
        return $(
                
                $(empty, empty, true, false, true, 0, 0),
                $(empty, empty, true, true, true, 0, 0),
                $(empty, empty, false, false, false, 0, 0),
                $(empty, empty, false, true, false, 0, 0),                

                $(empty, one, true, false, true, 0, 0),
                $(empty, one, true, true, true, 0, 0),
                $(empty, one, false, false, false, 0, 0),
                $(empty, one, false, true, false, 0, 0),         
                
                // non-uniqueness of the state should be ignored
                $(one, two_two, true, false, true, 1, 0),
                $(one, two_two, true, true, true, 1, 0),
                $(one, two_two, false, false, false, 1, 0),
                $(one, two_two, false, true, false, 1, 0),                  
                
                $(two_three, empty, true, false, true, 2, 0),
                $(two_three, empty, true, true, true, 2, 0),
                $(two_three, empty, false, false, false, 2, 0),
                $(two_three, empty, false, true, false, 2, 0),

                // one row in data clashes with one row in the state
                $(one_two, two_three, true, false, false, 1, 1),
                $(one_two, two_three, true, true, false, 1, 1),
                $(one_two, two_three, false, false, false, 1, 1),
                $(one_two, two_three, false, true, false, 1, 1),                
                
                $(two_two, empty, true, false, false, 1, 1),
                $(two_two, empty, true, true, false, 1, 1),
                $(two_two, empty, false, false, false, 1, 1),
                $(two_two, empty, false, true, false, 1, 1),

                $(null_null, empty, true, false, false, 0, 2),
                $(null_null, empty, true, true, true, 2, 0),
                $(null_null, empty, false, false, true, 0, 2),
                $(null_null, empty, false, true, false, 2, 0),

                $(one_null, two_three, true, false, false, 1, 1),
                $(one_null, two_three, true, true, true, 2, 0),
                $(one_null, two_three, false, false, false, 1, 1),           
                $(one_null, two_three, false, true, false, 2, 0),
                
                $(null_one, two_three, true, false, false, 1, 1),
                $(null_one, two_three, true, true, true, 2, 0),
                $(null_one, two_three, false, false, false, 1, 1),
                // due to null-acceptance for the goal (not to satisfy), all rows here are rejected
                $(null_one, two_three, false, true, false, 2, 0)
               
                ); 
    }

    @Test
    @Parameters(method = "oneColumnTestValues")
    public void oneColumnTests(
            Integer[] dataValues, Integer[] stateValues,
            boolean goalIsToSatisfy, boolean nullAccepted, boolean optimal,
            int numAcceptedRows, int numRejectedRows) {

        OneColumnMockDatabase database = new OneColumnMockDatabase();
        
        Data data = database.createData(dataValues.length);
        Data state = database.createState(stateValues.length);
        
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);

        List<Column> columns = new ArrayList<>();
        columns.add(database.column);        
        
        evaluate(goalIsToSatisfy, nullAccepted, optimal, 
                 numAcceptedRows, numRejectedRows,
                 columns, data, state);    
    }

    Integer[] oneOne_twoTwo = { 1, 1, 
                                2, 2 };

    Integer[] oneOne = { 1, 1 };

    Integer[] twoTwo = { 2, 2 };

    Integer[] oneOne_oneOne = { 1, 1, 
                                1, 1 };

    Integer[] oneNull_oneOne = { 1, null, 
                                 1, 1 };

    Object[] twoColumnTestValues() {
        return $(
                $(empty, empty, true, false, true, 0, 0),
                $(empty, empty, true, true, true, 0, 0),
                $(empty, empty, false, false, false, 0, 0),
                $(empty, empty, false, true, false, 0, 0),

                $(oneOne_twoTwo, empty, true, false, true, 2, 0),
                $(oneOne_twoTwo, empty, true, true, true, 2, 0),
                $(oneOne_twoTwo, empty, false, false, false, 2, 0),
                $(oneOne_twoTwo, empty, false, true, false, 2, 0),

                $(oneOne, twoTwo, true, false, true, 1, 0),
                $(oneOne, twoTwo, true, true, true, 1, 0),
                $(oneOne, twoTwo, false, false, false, 1, 0),
                $(oneOne, twoTwo, false, true, false, 1, 0),

                $(oneOne_oneOne, empty, true, false, false, 1, 1),
                $(oneOne_oneOne, empty, true, true, false, 1, 1),
                $(oneOne_oneOne, empty, false, false, false, 1, 1),
                $(oneOne_oneOne, empty, false, true, false, 1, 1),

                $(oneOne, oneOne, true, false, false, 0, 1),
                $(oneOne, oneOne, true, true, false, 0, 1),
                $(oneOne, oneOne, false, false, true, 0, 1),
                $(oneOne, oneOne, false, true, true, 0, 1),

                $(oneNull_oneOne, empty, true, false, false, 1, 1),
                $(oneNull_oneOne, empty, true, true, true, 2, 0),
                $(oneNull_oneOne, empty, false, false, false, 1, 1),
                $(oneNull_oneOne, empty, false, true, false, 2, 0));
    }

    @Test
    @Parameters(method = "twoColumnTestValues")
    public void twoColumnTests(Integer[] dataValues, Integer[] stateValues,
            boolean goalIsToSatisfy, boolean nullAccepted, boolean optimal, 
            int numAcceptedRows, int numRejectedRows) {

        TwoColumnMockDatabase database = new TwoColumnMockDatabase();

        Data data = database.createData(dataValues.length / 2);
        Data state = database.createState(stateValues.length / 2);
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);

        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        columns.add(database.column2);

        evaluate(goalIsToSatisfy, nullAccepted, optimal, 
                numAcceptedRows, numRejectedRows,
                columns, data, state); 
    }
    
    private void evaluate(
            boolean goalIsToSatisfy, boolean nullAccepted,
            boolean optimal, int numAcceptedRows, int numRejectedRows, 
            List<Column> columns,
            Data data, Data state) {
        UniqueObjectiveFunction objFun = new UniqueObjectiveFunction(columns,
                state, "", goalIsToSatisfy, nullAccepted);
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
