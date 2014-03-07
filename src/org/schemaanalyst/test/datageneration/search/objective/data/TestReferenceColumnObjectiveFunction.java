package org.schemaanalyst.test.datageneration.search.objective.data;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceColumnObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.test.testutil.mock.FourColumnMockDatabase;
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
public class TestReferenceColumnObjectiveFunction {
		
    Object[] oneColumnTestValues() {
        return $(
                $(EMPTY, EMPTY, true, false, false, 0, 0),
                $(EMPTY, EMPTY, true, true, false, 0, 0),
                $(EMPTY, EMPTY, false, false, false, 0, 0),
                $(EMPTY, EMPTY, false, true, false, 0, 0),                
                
                $(d(r(1, 1)), EMPTY, true, false, true, 1, 0),
                $(d(r(1, 1)), EMPTY, true, true, true, 1, 0),
                $(d(r(1, 1)), EMPTY, false, false, false, 1, 0),
                $(d(r(1, 1)), EMPTY, false, true, false, 1, 0),
                
                $(d(r(1, 2)), EMPTY, true, false, false, 0, 1),
                $(d(r(1, 2)), EMPTY, true, true, false, 0, 1),
                $(d(r(1, 2)), EMPTY, false, false, true, 0, 1),
                $(d(r(1, 2)), EMPTY, false, true, true, 0, 1),
                
                $(d(r(1, 2), r(5, 6)), EMPTY, true, false, false, 0, 2),
                $(d(r(1, 2), r(5, 6)), EMPTY, true, true, false, 0, 2),
                $(d(r(1, 2), r(5, 6)), EMPTY, false, false, true, 0, 2),
                $(d(r(1, 2), r(5, 6)), EMPTY, false, true, true, 0, 2),
                
                $(d(r(1, 5), r(5, 1)), EMPTY, true, false, true, 2, 0),
                $(d(r(1, 5), r(5, 1)), EMPTY, true, true, true, 2, 0),
                $(d(r(1, 5), r(5, 1)), EMPTY, false, false, false, 2, 0),
                $(d(r(1, 5), r(5, 1)), EMPTY, false, true, false, 2, 0),
                
                // since we didn't pass or fail BOTH rows, the optimum is never reached
                // for any of these test cases
                $(d(r(1, 5), r(5, 3)), EMPTY, true, false, false, 1, 1),
                $(d(r(1, 5), r(5, 3)), EMPTY, true, true, false, 1, 1),
                $(d(r(1, 5), r(5, 3)), EMPTY, false, false, false, 1, 1),
                $(d(r(1, 5), r(5, 3)), EMPTY, false, true, false, 1, 1),
                
                $(d(r(1, 5), r(2, 3)), EMPTY, true, false, false, 0, 2),
                $(d(r(1, 5), r(2, 3)), EMPTY, true, true, false, 0, 2),
                $(d(r(1, 5), r(2, 3)), EMPTY, false, false, true, 0, 2),
                $(d(r(1, 5), r(2, 3)), EMPTY, false, true, true, 0, 2),
               
                // there's no match in the state, but the objective
                // function is concerned with the data only, and 
                // the data is EMPTY
                $(EMPTY, d(r(1, 5), r(2, 3)), true, false, false, 0, 0),
                $(EMPTY, d(r(1, 5), r(2, 3)), true, true, false, 0, 0),
                $(EMPTY, d(r(1, 5), r(2, 3)), false, false, false, 0, 0),
                $(EMPTY, d(r(1, 5), r(2, 3)), false, true, false, 0, 0),                
                
                // ditto for above - we do not care about the state
                $(d(r(1, 1)), d(r(1, 5), r(2, 3)), true, false, true, 1, 0),
                $(d(r(1, 1)), d(r(1, 5), r(2, 3)), true, true, true, 1, 0),
                $(d(r(1, 1)), d(r(1, 5), r(2, 3)), false, false, false, 1, 0),
                $(d(r(1, 1)), d(r(1, 5), r(2, 3)), false, true, false, 1, 0),                
                
                $(d(r(2, 1), r(2, 2)), EMPTY, true, false, true, 2, 0),
                $(d(r(2, 1), r(2, 2)), EMPTY, true, true, true, 2, 0),
                $(d(r(2, 1), r(2, 2)), EMPTY, false, false, false, 2, 0),
                $(d(r(2, 1), r(2, 2)), EMPTY, false, true, false, 2, 0),                
                
                $(d(r(NULL, NULL)), EMPTY, true, false, false, 0, 1),
                $(d(r(NULL, NULL)), EMPTY, true, true, true, 1, 0),
                $(d(r(NULL, NULL)), EMPTY, false, false, false, 1, 0),
                $(d(r(NULL, NULL)), EMPTY, false, true, true, 0, 1),
                                  
                $(d(r(NULL, 1), r(NULL, NULL)), EMPTY, true, false, false, 0, 2),
                $(d(r(NULL, 1), r(NULL, NULL)), EMPTY, true, true, true, 2, 0),
                $(d(r(NULL, 1), r(NULL, NULL)), EMPTY, false, false, false, 2, 0),
                $(d(r(NULL, 1), r(NULL, NULL)), EMPTY, false, true, true, 0, 2),
                                        
                $(d(r(NULL, 1), r(1, NULL)), EMPTY, true, false, false, 1, 1),
                $(d(r(NULL, 1), r(1, NULL)), EMPTY, true, true, true, 2, 0),
                // partial match on 1 == 1, so this test fails
                $(d(r(NULL, 1), r(1, NULL)), EMPTY, false, false, false, 2, 0),                
                $(d(r(NULL, 1), r(1, NULL)), EMPTY, false, true, false, 1, 1),                
                
                $(d(r(1, 2)), d(r(2, 1)), true, false, true, 1, 0),
                $(d(r(1, 2)), d(r(2, 1)), true, true, true, 1, 0),
                $(d(r(1, 2)), d(r(2, 1)), false, false, false, 1, 0),
                $(d(r(1, 2)), d(r(2, 1)), false, true, false, 1, 0)                
        		);
    }    
    
    @Test
    @Parameters(method = "oneColumnTestValues")     
    public void oneColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean allowNull, 
    						   boolean optimal,
    						   int numAcceptedRows,
    						   int numRejectedRows) {
    	
    	TwoColumnMockDatabase database = new TwoColumnMockDatabase();
    	
        Data data = database.createData(dataValues.length / 2);
        Data state = database.createState(stateValues.length / 2);    	
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
    	
        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        
        List<Column> referenceColumns = new ArrayList<>();
        referenceColumns.add(database.column2);
        
        evaluate(goalIsToSatisfy, allowNull, optimal, 
                numAcceptedRows, numRejectedRows, data, state, 
                database.table, columns, database.table, referenceColumns);
    }
 
	
    Object[] twoColumnTestValues() {
        return $(
                $(d(r(1, 2, 1, 2)), EMPTY, true, false, true, 1, 0),
                $(d(r(1, 2, 1, 2)), EMPTY, true, true, true, 1, 0),
                $(d(r(1, 2, 1, 2)), EMPTY, false, false, false, 1, 0),
                $(d(r(1, 2, 1, 2)), EMPTY, false, true, false, 1, 0),
                
                $(d(r(1, 2, 1, 2), r(2, 3, 2, 3)), EMPTY, true, false, true, 2, 0),
                $(d(r(1, 2, 1, 2), r(2, 3, 2, 3)), EMPTY, true, true, true, 2, 0),
                $(d(r(1, 2, 1, 2), r(2, 3, 2, 3)), EMPTY, false, false, false, 2, 0),
                $(d(r(1, 2, 1, 2), r(2, 3, 2, 3)), EMPTY, false, true, false, 2, 0),      
                
                $(d(r(1, 2, 4, 5), r(2, 3, 6, 7)), EMPTY, true, false, false, 0, 2),
                $(d(r(1, 2, 4, 5), r(2, 3, 6, 7)), EMPTY, true, true, false, 0, 2),
                $(d(r(1, 2, 4, 5), r(2, 3, 6, 7)), EMPTY, false, false, true, 0, 2),
                $(d(r(1, 2, 4, 5), r(2, 3, 6, 7)), EMPTY, false, true, true, 0, 2),
                
                $(d(r(1, 2, 2, 3), r(2, 3, 6, 7)), EMPTY, true, false, false, 1, 1),
                $(d(r(1, 2, 2, 3), r(2, 3, 6, 7)), EMPTY, true, true, false, 1, 1),
                $(d(r(1, 2, 2, 3), r(2, 3, 6, 7)), EMPTY, false, false, false, 1, 1),
                $(d(r(1, 2, 2, 3), r(2, 3, 6, 7)), EMPTY, false, true, false, 1, 1),
                
                $(d(r(1, 2, 1, 3)), EMPTY, true, false, false, 0, 1),
                $(d(r(1, 2, 1, 3)), EMPTY, true, true, false, 0, 1),
                $(d(r(1, 2, 1, 3)), EMPTY, false, false, true, 0, 1),
                $(d(r(1, 2, 1, 3)), EMPTY, false, true, true, 0, 1),
                
                $(d(r(1, NULL, 1, NULL)), EMPTY, true, false, false, 0, 1), 
                $(d(r(1, NULL, 1, NULL)), EMPTY, true, true, true, 1, 0),
                $(d(r(1, NULL, 1, NULL)), EMPTY, false, false, false, 1, 0),
                $(d(r(1, NULL, 1, NULL)), EMPTY, false, true, true, 0, 1)                
        		);
    }     
    
    @Test
    @Parameters(method = "twoColumnTestValues")     
    public void twoColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean allowNull, 
    						   boolean optimal,
    						   int numAcceptedRows,
    						   int numRejectedRows) {
    	
    	FourColumnMockDatabase database = new FourColumnMockDatabase();
    	
        Data data = database.createData(dataValues.length / 4);
        Data state = database.createState(stateValues.length / 4);    	
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
    	
        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        columns.add(database.column2);
        
        List<Column> referenceColumns = new ArrayList<>();
        referenceColumns.add(database.column3);
        referenceColumns.add(database.column4);
        
        evaluate(goalIsToSatisfy, allowNull, optimal, 
                numAcceptedRows, numRejectedRows, data, state, 
                database.table, columns, database.table, referenceColumns);
    }

    
    Integer[] nullValue = {null};
    
    Integer[] oneValue = {1};
    
    Object[] noReferenceColumnTestValues() {
        return $(
                $(nullValue, EMPTY, true, false, false, 0, 1),
                $(nullValue, EMPTY, true, true, true, 1, 0),
                $(nullValue, EMPTY, false, false, false, 1, 0),
                $(nullValue, EMPTY, false, true, true, 0, 1),
                

                $(oneValue, EMPTY, true, false, false, 0, 1),
                $(oneValue, EMPTY, true, true, false, 0, 1),
                $(oneValue, EMPTY, false, false, true, 0, 1),
                $(oneValue, EMPTY, false, true, true, 0, 1)
                                
                );
    }
    
    @Test
    @Parameters(method = "noReferenceColumnTestValues")     
    public void noReferenceColumnTests(
            Integer[] dataValues, 
            Integer[] stateValues, 
            boolean goalIsToSatisfy, 
            boolean allowNull, 
            boolean optimal,
            int numAcceptedRows,
            int numRejectedRows) {
        
        OneColumnMockDatabase database = new OneColumnMockDatabase();
        
        Data data = database.createData(dataValues.length);
        Data state = database.createState(stateValues.length);      
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
        
        List<Column> columns = new ArrayList<>();
        columns.add(database.column);
        
        List<Column> referenceColumns = new ArrayList<>();
        
        evaluate(goalIsToSatisfy, allowNull, optimal, 
                numAcceptedRows, numRejectedRows, data,
                state, database.table, columns, database.table, referenceColumns);
    }    
    
    private void evaluate(boolean goalIsToSatisfy, boolean allowNull,
            boolean optimal, int numAcceptedRows, int numRejectedRows, Data data, Data state,
            Table table, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        
        ReferenceColumnObjectiveFunction objFun = 
                new ReferenceColumnObjectiveFunction(
                        table, columns, referenceTable, referenceColumns, 
                        state, "", goalIsToSatisfy, allowNull);
        
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
