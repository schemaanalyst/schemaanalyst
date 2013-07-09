package org.schemaanalyst.test.datageneration.search.objective;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.data.UniqueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.value.NumericValueObjectiveFunction;
import org.schemaanalyst.test.mock.MockDatabase;
import org.schemaanalyst.test.mock.OneColumnMockDatabase;
import org.schemaanalyst.test.mock.TwoColumnMockDatabase;

import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertEquivalent;
import static org.schemaanalyst.test.junit.ObjectiveValueAssert.assertOptimal;

public class TestUniqueObjectiveFunction {

	MockDatabase database;
	
	UniqueObjectiveFunction objFun;
	Data data;
	
	void setup(boolean satisfy, boolean allowNull, int dataRows, int stateRows) {
		data = database.createData(dataRows);		
		Data state = database.createState(stateRows);
		objFun = new UniqueObjectiveFunction(
						database.table.getColumns(),
						state, "Configurable Unique column constraint",
						satisfy, 
						allowNull);
	}	
		
	@Test 
	public void satisfyNoNulls_2Columns2RowsUnique() {
		database = new TwoColumnMockDatabase();
		setup(true, false, 2, 0);
		database.setDataValues(
				1, 1, 
				2, 2);
		assertOptimal(objFun.evaluate(data));		
	}
	
	@Test 
	public void satisfyNoNulls_2Columns1RowData1RowStateUnique() {
		database = new TwoColumnMockDatabase();
		setup(true, false, 1, 1);
		database.setDataValues(1, 1);
		database.setStateValues(2, 2);
		assertOptimal(objFun.evaluate(data));		
	}	
		
	@Test 
	public void satisfyNoNulls_2Columns2RowsNotUnique() {
		database = new TwoColumnMockDatabase();
		setup(true, false, 2, 0);
		database.setDataValues(
				1, 1, 
				1, 1);
		
		MultiObjectiveValue expected = new SumOfMultiObjectiveValue("top level");
		SumOfMultiObjectiveValue row = new SumOfMultiObjectiveValue("row");
		DistanceObjectiveValue value = new DistanceObjectiveValue("value");
		value.setValueUsingDistance(NumericValueObjectiveFunction.K);
		row.add(value);
		expected.add(row);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertEquivalent(expected, actual);		
	}		
	
	@Test 
	public void satisfyNoNulls_2Columns1RowData1RowStateNotUnique() {
		database = new TwoColumnMockDatabase();
		setup(true, false, 1, 1);
		database.setDataValues(1, 1);
		database.setStateValues(1, 1);
		
		MultiObjectiveValue expected = new SumOfMultiObjectiveValue("top level");
		SumOfMultiObjectiveValue row = new SumOfMultiObjectiveValue("row");
		DistanceObjectiveValue value = new DistanceObjectiveValue("value");
		value.setValueUsingDistance(NumericValueObjectiveFunction.K);
		row.add(value);
		expected.add(row);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertEquivalent(expected, actual);		
	}	
	
	@Test 
	public void satisfyNoNulls_1ColumnNulls() {
		database = new OneColumnMockDatabase();
		setup(true, false, 2, 0);
		database.setDataValues(null, null);
		
		MultiObjectiveValue expected = new SumOfMultiObjectiveValue("top level");
		SumOfMultiObjectiveValue row = new SumOfMultiObjectiveValue("row");
		row.add(ObjectiveValue.worstObjectiveValue("null value"));
		expected.add(row);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertEquivalent(expected, actual);		
	}

	@Test 
	public void satisfyNulls_1ColumnNulls() {
		database = new OneColumnMockDatabase();
		setup(true, true, 2, 2);
		database.setDataValues(1, null);
		database.setStateValues(2, 3);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertOptimal(actual);		
	}	
	
	@Test 
	public void satisfyNulls_2ColumnNulls() {
		database = new TwoColumnMockDatabase();
		setup(true, true, 2, 0);
		database.setDataValues(1, null,
							   1, 1);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertOptimal(actual);		
	}			
	
	@Test 
	public void negateNoNulls_2Columns2RowsNotUnique() {
		database = new TwoColumnMockDatabase();
		setup(false, false, 2, 0);
		database.setDataValues(
				1, 1, 
				1, 1);
		assertOptimal(objFun.evaluate(data));		
	}	
	
	@Test 
	public void negateNoNulls_1ColumnsNulls() {
		database = new OneColumnMockDatabase();
		setup(false, false, 2, 0);
		database.setDataValues(null, null);
		
		MultiObjectiveValue expected = new SumOfMultiObjectiveValue("top level");
		MultiObjectiveValue row = new SumOfMultiObjectiveValue("row");
		row.add(ObjectiveValue.worstObjectiveValue("null value"));
		expected.add(row);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertEquivalent(expected, actual);		
	}	
	
	@Test 
	public void negateNulls_1ColumnNulls() {
		database = new OneColumnMockDatabase();
		setup(false, true, 2, 2);
		database.setDataValues(1, null);
		database.setStateValues(1, 1);
		
		ObjectiveValue actual = objFun.evaluate(data);
		assertOptimal(actual);		
	}	
}
