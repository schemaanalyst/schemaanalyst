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
	
	Integer[] oneRowNulls = {null, null, 0, 0};		
	
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
                
                $(oneRowNulls, empty, true, false, false),
                $(oneRowNulls, empty, true, true, true),
                $(oneRowNulls, empty, false, false, false),
                $(oneRowNulls, empty, false, true, true)                  
                );
    }    
        
    
    @Test
    @Parameters(method = "oneColumnTestValues")     
    public void oneColumnTests(Integer[] dataValues, 
    						   Integer[] stateValues, 
    						   boolean goalIsToSatisfy, 
    						   boolean nullIsSatisfy, 
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
        		columns, referenceColumns, state, "", goalIsToSatisfy, nullIsSatisfy);
        ObjectiveValue objVal = objFun.evaluate(data);
        
        if (optimal) {
        	assertOptimal(objVal);
        } else {
        	assertNonOptimal(objVal);
        }
    }
 
}
