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
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertNonOptimal;
import static org.schemaanalyst.test.testutil.ObjectiveValueAssert.assertOptimal;

@RunWith(JUnitParamsRunner.class)
public class TestUniqueObjectiveFunction {

	Integer[] empty = {};
	
	Integer[] oneColTwoRowsTwoDiffValues = {2, 
			                                3};	
	
	Integer[] oneColTwoRowsTwoSameValues = {2, 
            								2};
	
	Integer[] oneColTwoRowsAllNulls = {null, 
							   		   null};
	
	Integer[] oneColTwoRowsOneNull = {1, 
			                          null};	

	
    Object[] oneColumnTestValues() {
        return $(
                $(oneColTwoRowsTwoDiffValues, empty, true, false, true),
                $(oneColTwoRowsTwoDiffValues, empty, true, true, true),
                $(oneColTwoRowsTwoDiffValues, empty, false, false, false),
                $(oneColTwoRowsTwoDiffValues, empty, false, true, false),           		

                $(oneColTwoRowsTwoSameValues, empty, true, false, false),
                $(oneColTwoRowsTwoSameValues, empty, true, true, false),
                $(oneColTwoRowsTwoSameValues, empty, false, false, true),
                $(oneColTwoRowsTwoSameValues, empty, false, true, true),                 
                
                $(oneColTwoRowsAllNulls, empty, true, false, false),
                $(oneColTwoRowsAllNulls, empty, true, true, true),
                $(oneColTwoRowsAllNulls, empty, false, false, false),
                $(oneColTwoRowsAllNulls, empty, false, true, true),
                
                $(oneColTwoRowsOneNull, oneColTwoRowsTwoDiffValues, true, false, false),
                $(oneColTwoRowsOneNull, oneColTwoRowsTwoDiffValues, true, true, true),
                $(oneColTwoRowsOneNull, oneColTwoRowsTwoDiffValues, false, false, false),
                $(oneColTwoRowsOneNull, oneColTwoRowsTwoDiffValues, false, true, true)                
                );
    }              
                
	
    @Test
    @Parameters(method = "oneColumnTestValues")     
    public void oneColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean nullAccepted, 
    						   boolean optimal) {
    	
    	OneColumnMockDatabase database = new OneColumnMockDatabase();
    	
        Data data = database.createData(dataValues.length);
        Data state = database.createState(stateValues.length);    	
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
    	
        List<Column> columns = new ArrayList<>();
        columns.add(database.column);
                
        UniqueObjectiveFunction objFun = new UniqueObjectiveFunction(
        		columns, state, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (optimal) {
        	assertOptimal(objVal);
        } else {
        	assertNonOptimal(objVal);
        }
    }	
    
    Integer[] twoColsTwoRowsUnique = {1, 1,
            						  2, 2};	

    Integer[] twoColsOneRow1 = {1, 1};
    
    Integer[] twoColsOneRow2 = {2, 2};
    
    Integer[] twoColsTwoRowsNonUnique = {1, 1,
			  							 1, 1};    

    Integer[] twoColsTwoRowsOneNull = {1, null,
    		                           1, 1};
    
	Object[] twoColumnTestValues() {
		return $(
			$(twoColsTwoRowsUnique, empty, true, false, true),
			$(twoColsTwoRowsUnique, empty, true, true, true),
			$(twoColsTwoRowsUnique, empty, false, false, false),
			$(twoColsTwoRowsUnique, empty, false, true, false),
			
			$(twoColsOneRow1, twoColsOneRow2, true, false, true),
			$(twoColsOneRow1, twoColsOneRow2, true, true, true),
			$(twoColsOneRow1, twoColsOneRow2, false, false, false),
			$(twoColsOneRow1, twoColsOneRow2, false, true, false),
			
			$(twoColsTwoRowsNonUnique, empty, true, false, false),
			$(twoColsTwoRowsNonUnique, empty, true, true, false),
			$(twoColsTwoRowsNonUnique, empty, false, false, true),
			$(twoColsTwoRowsNonUnique, empty, false, true, true),
			
			$(twoColsOneRow1, twoColsOneRow1, true, false, false),
			$(twoColsOneRow1, twoColsOneRow1, true, true, false),
			$(twoColsOneRow1, twoColsOneRow1, false, false, true),
			$(twoColsOneRow1, twoColsOneRow1, false, true, true),
			
			$(twoColsTwoRowsOneNull, empty, true, false, false),
			$(twoColsTwoRowsOneNull, empty, true, true, true),
			$(twoColsTwoRowsOneNull, empty, false, false, false),
			$(twoColsTwoRowsOneNull, empty, false, true, true)
			);
	}      
    
    
    @Test
    @Parameters(method = "twoColumnTestValues")     
    public void twoColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean nullAccepted, 
    						   boolean optimal) {
    	
    	TwoColumnMockDatabase database = new TwoColumnMockDatabase();
    	
        Data data = database.createData(dataValues.length / 2);
        Data state = database.createState(stateValues.length / 2);    	
        database.setDataValues(dataValues);
        database.setStateValues(stateValues);
    	
        List<Column> columns = new ArrayList<>();
        columns.add(database.column1);
        columns.add(database.column2);
                
        UniqueObjectiveFunction objFun = new UniqueObjectiveFunction(
        		columns, state, "", goalIsToSatisfy, nullAccepted);
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (optimal) {
        	assertOptimal(objVal);
        } else {
        	assertNonOptimal(objVal);
        }
    }	    
}
