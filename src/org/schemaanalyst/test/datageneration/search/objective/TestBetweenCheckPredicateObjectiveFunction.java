package org.schemaanalyst.test.datageneration.search.objective;

import org.junit.Test;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.constraint.BetweenCheckPredicateObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.predicate.ValueObjectiveFunction;
import org.schemaanalyst.schema.BetweenCheckPredicate;

import org.schemaanalyst.test.mock.OneColumnMockDatabase;

import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.GREATER_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.LESS;
import static org.schemaanalyst.logic.RelationalOperator.LESS_OR_EQUALS;

import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertEquivalent;

public class TestBetweenCheckPredicateObjectiveFunction {

	OneColumnMockDatabase database;
	Data data;
	
	public TestBetweenCheckPredicateObjectiveFunction() {
		// set up schema and data
		database = new OneColumnMockDatabase();
		data = database.createData(2);
	}		
	
    BetweenCheckPredicateObjectiveFunction setupBetween(boolean satisfy, int lower, int upper) {
    	BetweenCheckPredicate betweenPredicate = new BetweenCheckPredicate(database.column, lower, upper);
    	BetweenCheckPredicateObjectiveFunction objFun = 
    			new BetweenCheckPredicateObjectiveFunction(betweenPredicate, database.table, null, "", satisfy, false);
    	return objFun;
    }
	
    void test(boolean satisfy, int lower, int upper, Integer... values) {
    	BetweenCheckPredicateObjectiveFunction objFun = setupBetween(satisfy, lower, upper);
		database.setDataValues(values);
		
		MultiObjectiveValue expectedObjVal = new SumOfMultiObjectiveValue("Top level");
    	
		if (satisfy) {
			satisfy(lower, upper, expectedObjVal, values);
		} else {
			negate(lower, upper, expectedObjVal, values);
		}
		
		ObjectiveValue actualObjVal = objFun.evaluate(data);
		assertEquivalent(expectedObjVal, actualObjVal); 
    }

	void satisfy(int lower, int upper, MultiObjectiveValue expectedObjVal, Integer... values) {
		for (Integer value : values) {
    		SumOfMultiObjectiveValue betweenObjVal = new SumOfMultiObjectiveValue("Row between");
    		
    		NumericValue numericalValue = new NumericValue();
    		if (value != null) {
    			numericalValue.set(value);
    		} else {
    			numericalValue = null;
    		}
    		
    		betweenObjVal.add(ValueObjectiveFunction.compute(
    				numericalValue, GREATER_OR_EQUALS, new NumericValue(lower)));
    		betweenObjVal.add(ValueObjectiveFunction.compute(
    				numericalValue, LESS_OR_EQUALS, new NumericValue(upper)));
    	
    		expectedObjVal.add(betweenObjVal);
    	}
	}
	
	void negate(int lower, int upper, MultiObjectiveValue expectedObjVal, Integer... values) {
		for (Integer value : values) {
    		BestOfMultiObjectiveValue betweenObjVal = new BestOfMultiObjectiveValue("Row between");
    		
    		NumericValue numericalValue = new NumericValue();
    		if (value != null) {
    			numericalValue.set(value);
    		} else {
    			numericalValue = null;
    		}
    		
    		betweenObjVal.add(ValueObjectiveFunction.compute(
    				numericalValue, LESS, new NumericValue(lower)));
    		betweenObjVal.add(ValueObjectiveFunction.compute(
    				numericalValue, GREATER, new NumericValue(upper)));
    	
    		expectedObjVal.add(betweenObjVal);
    	}
	}	
    
	@Test 
	public void testSatisfy_AllInRange() {
		test(true, 
				0, 10, // lower and upper
				5, 5); // values 
	}	
	
	@Test 
	public void testSatisfy_NoneInRange() {
		test(true, 
				0, 10 ,  // lower and upper
			 	-5, -5); // values 
	}		
	
	@Test 
	public void testSatisfy_OneOutOfRange() {
		test(true, 
				0, 10,	// lower and upper
		       	5, -5); // values 
	}				
	
	@Test 
	public void testSatisfy_OneNull() {
		test(true, 
				0, 10, 		// lower and upper
	       	 	null, 5);  // values 		
	}	

	@Test 
	public void testSatisfy_OneRow() {
		test(true, 0, 
				10, 5);    // values 		
	}	
	
	@Test 
	public void testSatisfy_LowerBoundary() {
		test(true, 
				0, 10 // lower and upper
	       	    -1); // values 		
	}		
	
	@Test 
	public void testSatisfy_UpperBoundary() {
		test(true, 0, 
				10, 11);   // values 		
	}	
	
	@Test 
	public void testSatisfy_Inclusive() {
		test(true, 
				0, 10,  // lower and upper
	       	    0, 10); // values 		
	}		
	

	@Test 
	public void testNegate_AllInRange() {
		test(false,  
				0, 10,  // lower and upper
				3, 4);  // values 		
	}	
	
	@Test 
	public void testNegate_OneInRange() {
		test(false,  
				0, 10,  // lower and upper
				3, -10);  // values 		
	}		
	
	
	@Test 
	public void testNegate_NoneInRange() {
		test(false,  
				0, 10,  	// lower and upper
				-10, -10);  // values 
	}		
	
	@Test 
	public void testNegate_OneNull() {
		test(false,  
				0, 10,  	// lower and upper
				null, -5);  // values 		
	}	
}
