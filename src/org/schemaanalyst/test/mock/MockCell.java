package org.schemaanalyst.test.mock;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.representation.Column;

public 	class MockCell extends Cell {
	
	public MockCell(Value value) {
		super(null, null);
		this.value = value;
	}
	
	public MockCell(Value value, Column column) {
		super(column, null);
		this.value = value;
	}	
	
	protected Value createValue() {
		return null;
	}		
}