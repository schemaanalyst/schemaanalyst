package org.schemaanalyst.test.datageneration.search.objective.data;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.assertion.ObjectiveValueAssert.assertOptimal;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceConstraintObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.test.testutil.mock.FourColumnMockDatabase;
import org.schemaanalyst.test.testutil.mock.OneColumnMockDatabase;
import org.schemaanalyst.test.testutil.mock.TwoColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestReferenceConstraintObjectiveFunction {

	Integer[] empty = {};
	
	Integer[] oneOne = {1, 1};
		
	Integer[] oneTwo = {1, 2};
	
	Integer[] oneTwo_fiveSix = {1, 2,
								5, 6};	
	
	Integer[] oneFive_fiveOne = {1, 5,
			  				     5, 1};

	Integer[] oneFive_fiveThree = {1, 5,
								   5, 3};	

	Integer[] oneFive_twoThree = {1, 5,
		   	   				      2, 3};		
	
	Integer[] twoOne_twoTwo = {2, 1,
			  				   2, 2};	
	
	Integer[] nullNull = {null, null};
	
	Integer[] nullOne_nullNull = {null, 1,
								  null, null};

	Integer[] nullOne_oneNull = {null, 1,
			  					 1, null};	
	
	Integer[] twoOne = {2, 1};
	
    Object[] oneColumnTestValues() {
        return $(
                $(empty, empty, true, false, false, 0, 0),
                $(empty, empty, true, true, false, 0, 0),
                $(empty, empty, false, false, false, 0, 0),
                $(empty, empty, false, true, false, 0, 0),                
                
                $(oneOne, empty, true, false, true, 1, 0),
                $(oneOne, empty, true, true, true, 1, 0),
                $(oneOne, empty, false, false, false, 1, 0),
                $(oneOne, empty, false, true, false, 1, 0),
                
                $(oneTwo, empty, true, false, false, 0, 1),
                $(oneTwo, empty, true, true, false, 0, 1),
                $(oneTwo, empty, false, false, true, 0, 1),
                $(oneTwo, empty, false, true, true, 0, 1),
                
                $(oneTwo_fiveSix, empty, true, false, false, 0, 2),
                $(oneTwo_fiveSix, empty, true, true, false, 0, 2),
                $(oneTwo_fiveSix, empty, false, false, true, 0, 2),
                $(oneTwo_fiveSix, empty, false, true, true, 0, 2),
                
                $(oneFive_fiveOne, empty, true, false, true, 2, 0),
                $(oneFive_fiveOne, empty, true, true, true, 2, 0),
                $(oneFive_fiveOne, empty, false, false, false, 2, 0),
                $(oneFive_fiveOne, empty, false, true, false, 2, 0),
                
                // since we didn't pass or fail BOTH rows, the optimum is never reached
                // for any of these test cases
                $(oneFive_fiveThree, empty, true, false, false, 1, 1),
                $(oneFive_fiveThree, empty, true, true, false, 1, 1),
                $(oneFive_fiveThree, empty, false, false, false, 1, 1),
                $(oneFive_fiveThree, empty, false, true, false, 1, 1),
                
                $(oneFive_twoThree, empty, true, false, false, 0, 2),
                $(oneFive_twoThree, empty, true, true, false, 0, 2),
                $(oneFive_twoThree, empty, false, false, true, 0, 2),
                $(oneFive_twoThree, empty, false, true, true, 0, 2),
               
                // there's no match in the state, but the objective
                // function is concerned with the data only, and 
                // the data is empty
                $(empty, oneFive_twoThree, true, false, false, 0, 0),
                $(empty, oneFive_twoThree, true, true, false, 0, 0),
                $(empty, oneFive_twoThree, false, false, false, 0, 0),
                $(empty, oneFive_twoThree, false, true, false, 0, 0),                
                
                // ditto for above - we do not care about the state
                $(oneOne, oneFive_twoThree, true, false, true, 1, 0),
                $(oneOne, oneFive_twoThree, true, true, true, 1, 0),
                $(oneOne, oneFive_twoThree, false, false, false, 1, 0),
                $(oneOne, oneFive_twoThree, false, true, false, 1, 0),                
                
                $(twoOne_twoTwo, empty, true, false, true, 2, 0),
                $(twoOne_twoTwo, empty, true, true, true, 2, 0),
                $(twoOne_twoTwo, empty, false, false, false, 2, 0),
                $(twoOne_twoTwo, empty, false, true, false, 2, 0),                
                
                $(nullNull, empty, true, false, false, 0, 1),
                $(nullNull, empty, true, true, true, 1, 0),
                $(nullNull, empty, false, false, false, 1, 0),
                $(nullNull, empty, false, true, true, 0, 1),
                                  
                $(nullOne_nullNull, empty, true, false, false, 0, 2),
                $(nullOne_nullNull, empty, true, true, true, 2, 0),
                $(nullOne_nullNull, empty, false, false, false, 2, 0),
                $(nullOne_nullNull, empty, false, true, true, 0, 2),
                                        
                $(nullOne_oneNull, empty, true, false, false, 1, 1),
                $(nullOne_oneNull, empty, true, true, true, 2, 0),
                // partial match on 1 == 1, so this test fails
                $(nullOne_oneNull, empty, false, false, false, 2, 0),                
                $(nullOne_oneNull, empty, false, true, false, 1, 1),                
                
                $(oneTwo, twoOne, true, false, true, 1, 0),
                $(oneTwo, twoOne, true, true, true, 1, 0),
                $(oneTwo, twoOne, false, false, false, 1, 0),
                $(oneTwo, twoOne, false, true, false, 1, 0)                
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
                numAcceptedRows, numRejectedRows, data,
                state, columns, referenceColumns);
    }
    
	Integer[] oneTwoOneTwo = {1, 2, 1, 2};
  
	Integer[] oneTwoOneTwo_twoThreeTwoThree 
	    = {1, 2, 1, 2,
		  2, 3, 2, 3};

	Integer[] oneTwoFourFive_twoThreeSixSeven 
	    = {1, 2, 4, 5,
	       2, 3, 6, 7};	

	Integer[] oneTwoTwoThree_twoThreeSixSeven 
	    = {1, 2, 2, 3,
		   2, 3, 6, 7};	
	
	Integer[] oneTwoOneThree = {1, 2, 1, 3};

	Integer[] oneNullOneNull = {1, null, 1, null};	
	
    Object[] twoColumnTestValues() {
        return $(
                $(oneTwoOneTwo, empty, true, false, true, 1, 0),
                $(oneTwoOneTwo, empty, true, true, true, 1, 0),
                $(oneTwoOneTwo, empty, false, false, false, 1, 0),
                $(oneTwoOneTwo, empty, false, true, false, 1, 0),
                
                $(oneTwoOneTwo_twoThreeTwoThree, empty, true, false, true, 2, 0),
                $(oneTwoOneTwo_twoThreeTwoThree, empty, true, true, true, 2, 0),
                $(oneTwoOneTwo_twoThreeTwoThree, empty, false, false, false, 2, 0),
                $(oneTwoOneTwo_twoThreeTwoThree, empty, false, true, false, 2, 0),      
                
                $(oneTwoFourFive_twoThreeSixSeven, empty, true, false, false, 0, 2),
                $(oneTwoFourFive_twoThreeSixSeven, empty, true, true, false, 0, 2),
                $(oneTwoFourFive_twoThreeSixSeven, empty, false, false, true, 0, 2),
                $(oneTwoFourFive_twoThreeSixSeven, empty, false, true, true, 0, 2),
                
                $(oneTwoTwoThree_twoThreeSixSeven, empty, true, false, false, 1, 1),
                $(oneTwoTwoThree_twoThreeSixSeven, empty, true, true, false, 1, 1),
                $(oneTwoTwoThree_twoThreeSixSeven, empty, false, false, false, 1, 1),
                $(oneTwoTwoThree_twoThreeSixSeven, empty, false, true, false, 1, 1),
                
                $(oneTwoOneThree, empty, true, false, false, 0, 1),
                $(oneTwoOneThree, empty, true, true, false, 0, 1),
                $(oneTwoOneThree, empty, false, false, true, 0, 1),
                $(oneTwoOneThree, empty, false, true, true, 0, 1),
                
                $(oneNullOneNull, empty, true, false, false, 0, 1), 
                $(oneNullOneNull, empty, true, true, true, 1, 0),
                $(oneNullOneNull, empty, false, false, false, 1, 0),
                $(oneNullOneNull, empty, false, true, true, 0, 1)                
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
                numAcceptedRows, numRejectedRows, data,
                state, columns, referenceColumns);
    }

    
    Integer[] nullValue = {null};
    
    Integer[] oneValue = {1};
    
    Object[] noReferenceColumnTestValues() {
        return $(
                $(nullValue, empty, true, false, false, 0, 1),
                $(nullValue, empty, true, true, true, 1, 0),
                $(nullValue, empty, false, false, false, 1, 0),
                $(nullValue, empty, false, true, true, 0, 1),
                

                $(oneValue, empty, true, false, false, 0, 1),
                $(oneValue, empty, true, true, false, 0, 1),
                $(oneValue, empty, false, false, true, 0, 1),
                $(oneValue, empty, false, true, true, 0, 1)
                                
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
                state, columns, referenceColumns);
    }    
    
    private void evaluate(boolean goalIsToSatisfy, boolean allowNull,
            boolean optimal, int numAcceptedRows, int numRejectedRows, Data data, Data state,
            List<Column> columns, List<Column> referenceColumns) {
        
        ReferenceConstraintObjectiveFunction objFun = new ReferenceConstraintObjectiveFunction(
                columns, referenceColumns, state, "", goalIsToSatisfy, allowNull);
        
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
