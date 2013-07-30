package org.schemaanalyst.test.datageneration.search.objective.data;

import static junitparams.JUnitParamsRunner.$;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.test.testutil.mock.FourColumnMockDatabase;

@RunWith(JUnitParamsRunner.class)
public class TestReferenceObjectiveFunction {

	Integer[] empty = {};
	
	Integer[] oneRowUniform = {1, 1, 1, 1};
		
	Integer[] oneRowDifferent = {1, 2, 3, 4};
	
	Integer[] twoRowsDiffernet = {1, 2, 3, 4,
								  5, 6, 7, 8};	
	
	Integer[] twoRowsOneFullMatch = {1, 5, 0, 0,
			  						 5, 1, 0, 0};

	Integer[] twoRowsOneColPartialMatch = {1, 5, 0, 0,
									   	   5, 3, 0, 0};	

	Integer[] twoRowsOneColNoMatch = {1, 5, 0, 0,
		   	   						  2, 3, 0, 0};		
	
	Integer[] twoRowsMatchWithOne = {2, 1, 0, 0,
			  					     2, 2, 0, 0};	
	
	Integer[] oneRowNulls = {null, null, 0, 0};
	
	Integer[] twoRowsNullMatch1 = {null, 1, 0, 0,
								  null, null, 0, 0};

	Integer[] twoRowsNullMatch2 = {null, 1, 0, 0,
			  					  1, null, 0, 0};	
	
	Integer[] oneRowMatchWithOther1 = {1, 2, 0, 0};	
	Integer[] oneRowMatchWithOther2 = {2, 1, 0, 0};
	
    Object[] oneColumnTestValues() {
        return $(
                $(oneRowUniform, empty, true, false, true),
                $(oneRowUniform, empty, true, true, true),
                $(oneRowUniform, empty, false, false, false),
                $(oneRowUniform, empty, false, true, false),
                
                $(oneRowDifferent, empty, true, false, false),
                $(oneRowDifferent, empty, true, true, false),
                $(oneRowDifferent, empty, false, false, true),
                $(oneRowDifferent, empty, false, true, true),
                
                $(twoRowsDiffernet, empty, true, false, false),
                $(twoRowsDiffernet, empty, true, true, false),
                $(twoRowsDiffernet, empty, false, false, true),
                $(twoRowsDiffernet, empty, false, true, true),
                
                $(twoRowsOneFullMatch, empty, true, false, true),
                $(twoRowsOneFullMatch, empty, true, true, true),
                $(twoRowsOneFullMatch, empty, false, false, false),
                $(twoRowsOneFullMatch, empty, false, true, false),
                
                // since we didn't pass or fail BOTH, the optimum is never reached
                // for any of these test cases
                $(twoRowsOneColPartialMatch, empty, true, false, false),
                $(twoRowsOneColPartialMatch, empty, true, true, false),
                $(twoRowsOneColPartialMatch, empty, false, false, false),
                $(twoRowsOneColPartialMatch, empty, false, true, false),
                
                $(twoRowsOneColNoMatch, empty, true, false, false),
                $(twoRowsOneColNoMatch, empty, true, true, false),
                $(twoRowsOneColNoMatch, empty, false, false, true),
                $(twoRowsOneColNoMatch, empty, false, true, true),
                
                $(twoRowsMatchWithOne, empty, true, false, true),
                $(twoRowsMatchWithOne, empty, true, true, true),
                $(twoRowsMatchWithOne, empty, false, false, false),
                $(twoRowsMatchWithOne, empty, false, true, false),                
                
                $(oneRowNulls, empty, true, false, false),
                $(oneRowNulls, empty, true, true, true),
                $(oneRowNulls, empty, false, false, false),
                $(oneRowNulls, empty, false, true, true),                  

                $(twoRowsNullMatch1, empty, true, false, false),
                $(twoRowsNullMatch1, empty, true, true, true),
                $(twoRowsNullMatch1, empty, false, false, false),
                $(twoRowsNullMatch1, empty, false, true, true),                        
                
                $(twoRowsNullMatch2, empty, true, false, false),
                $(twoRowsNullMatch2, empty, true, true, true),
                $(twoRowsNullMatch2, empty, false, false, false),                
                // partial match on 1 == 1, so this test actually fails
                $(twoRowsNullMatch2, empty, false, true, false),
                
                $(oneRowMatchWithOther1, oneRowMatchWithOther2, true, false, true),
                $(oneRowMatchWithOther1, oneRowMatchWithOther2, true, true, true),
                $(oneRowMatchWithOther1, oneRowMatchWithOther2, false, false, false),
                $(oneRowMatchWithOther1, oneRowMatchWithOther2, false, true, false)                
        		);
    }    
        
    
    @Test
    @Parameters(method = "oneColumnTestValues")     
    public void oneColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean nullAccepted, 
    						   boolean optimal) {
    	
    	FourColumnMockDatabase database = new FourColumnMockDatabase();
    	
        Data data = database.createData(dataValues.length / 4);
        Data state = database.createState(stateValues.length / 4);    	
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
    	
        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        
        List<Column> referenceColumns = new ArrayList<>();
        referenceColumns.add(database.column2);
        
        ReferenceObjectiveFunction objFun = new ReferenceObjectiveFunction(
        		columns, referenceColumns, state, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (optimal) {
        	assertOptimal(objVal);
        } else {
        	assertNonOptimal(objVal);
        }
    }
    
	Integer[] twoColOneRowUniform = {1, 2, 1, 2};
  
	Integer[] twoColTwoRowsMatch = {1, 2, 1, 2,
								   2, 3, 2, 3};

	Integer[] twoColTwoRowsNoMatch = {1, 2, 4, 5,
			   						 2, 3, 6, 7};	

	Integer[] twoColTwoRowsPartialMatch = {1, 2, 2, 3,
				 						   2, 3, 6, 7};	
	
	Integer[] twoColOneRowNoMatch = {1, 2, 1, 3};

	Integer[] twoColOneRowNullMatch = {1, null, 1, null};	
	
    Object[] twoColumnTestValues() {
        return $(
                $(twoColOneRowUniform, empty, true, false, true),
                $(twoColOneRowUniform, empty, true, true, true),
                $(twoColOneRowUniform, empty, false, false, false),
                $(twoColOneRowUniform, empty, false, true, false),
                
                $(twoColTwoRowsMatch, empty, true, false, true),
                $(twoColTwoRowsMatch, empty, true, true, true),
                $(twoColTwoRowsMatch, empty, false, false, false),
                $(twoColTwoRowsMatch, empty, false, true, false),      
                
                $(twoColTwoRowsNoMatch, empty, true, false, false),
                $(twoColTwoRowsNoMatch, empty, true, true, false),
                $(twoColTwoRowsNoMatch, empty, false, false, true),
                $(twoColTwoRowsNoMatch, empty, false, true, true),
                
                $(twoColTwoRowsPartialMatch, empty, true, false, false),
                $(twoColTwoRowsPartialMatch, empty, true, true, false),
                $(twoColTwoRowsPartialMatch, empty, false, false, false),
                $(twoColTwoRowsPartialMatch, empty, false, true, false),
                
                $(twoColOneRowNoMatch, empty, true, false, false),
                $(twoColOneRowNoMatch, empty, true, true, false),
                $(twoColOneRowNoMatch, empty, false, false, true),
                $(twoColOneRowNoMatch, empty, false, true, true),
                
                $(twoColOneRowNullMatch, empty, true, false, false),
                $(twoColOneRowNullMatch, empty, true, true, true),
                $(twoColOneRowNullMatch, empty, false, false, false),
                $(twoColOneRowNullMatch, empty, false, true, true)                  
        		);
    }
    
    @Test
    @Parameters(method = "twoColumnTestValues")     
    public void twoColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean nullAccepted, 
    						   boolean optimal) {
    	
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
        
        ReferenceObjectiveFunction objFun = new ReferenceObjectiveFunction(
        		columns, referenceColumns, state, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (optimal) {
        	assertOptimal(objVal);
        } else {
        	assertNonOptimal(objVal);
        }
    }    
 
}
