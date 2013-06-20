package org.schemaanalyst.data;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.util.Duplicable;

public class Cell implements Duplicable<Cell> {
	
	protected Column column;
	protected ValueFactory valueFactory;
	protected Value lastValue, value;
	
	public Cell(Column column, ValueFactory valueFactory) {
		this.column = column;
		this.valueFactory = valueFactory;
		this.lastValue = null;
		this.value = createValue();
	}
	
	protected Value createValue() {
		return valueFactory.createValue(column.getType());
	}
	
	public void setNull(boolean setToNull) {
		if (setToNull) {
			lastValue = value;
			value = null;
		} else if (isNull()) {
			value = lastValue == null
					? createValue()
				    : lastValue;
		}
	}
	
	public boolean isNull() {
		return value == null;
	}
	
	public Column getColumn() {
		return column;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public Value getValue() {
		return value;
	}
	
	public Value getValueInstance() {
		return value;
	}
	
	public Cell duplicate() {
		Cell duplicate = new Cell(column, valueFactory);
		if (isNull()) {
			duplicate.value = null;
		} else {
			duplicate.value = this.value.duplicate();
		}
		
		return duplicate;
	}

	public Boolean valuesEqual3VL(Cell other) {
		return Value.equals3VL(this.value, other.value);
	}
	
	public String toString() {
		String str = column + "(";
		if (isNull()) {
			str += "NULL"; 
		} else {
			str += value;
		}
		str += ")";
		return str;	
	}
}
