package org.schemaanalyst.test.mock;

import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Table;

public abstract class MockDatabase {

	public Table table;
	public Data state, data;
	
	public Data createState(int rows) {
		state = instantiateDataObject(rows);
		return state;
	}	
	
	public Data createData(int rows) {
		data = instantiateDataObject(rows);
		return data;
	}
	
	protected Data instantiateDataObject(int rows) {
		ValueFactory valueFactory = new ValueFactory();
		Data data = new Data();
		for (int i=0; i < rows; i++) {
			data.addRow(table, valueFactory);
		}
		return data;
	}
	
	public void setStateValues(Integer... values) {
		setValues(state, values);
	}
	
	public void setDataValues(Integer... values) {
		setValues(data, values);
	}	
	
	protected void setValues(Data data, Integer... values) {
		List<Cell> cells = data.getCells();
		for (int i=0; i < values.length; i++) {	
			if (values[i] == null) {
				cells.get(i).setNull(true);
			} else {
				((NumericValue) cells.get(i).getValue()).set(values[i]);
			}
		}
	}		
}
