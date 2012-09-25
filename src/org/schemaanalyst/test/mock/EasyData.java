package org.schemaanalyst.test.mock;

import java.util.List;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

public class EasyData extends Data {

	protected Schema schema;
	
	public EasyData(Schema schema) {
		this.schema = schema;
	}
	
	public void addRow(String tableName, String rowData, ValueFactory valueFactory) {
		
		class SetValueFromString implements ValueVisitor {

			String string;
			
			public void set(Cell cell, String string) {
				if (string.equals("NULL")) {
					cell.setNull(true);
				} else {
					this.string = string;
					cell.setNull(false);
					cell.getValue().accept(this);
				}
			}
			
			public void visit(BooleanValue value) {
				// to complete...
			}			
			
			public void visit(DateValue value) {
				// to complete...
			}
			
			public void visit(DateTimeValue value) {				
				// to complete...
			}
			
			public void visit(NumericValue value) {
				value.set(string);
			}
			
			public void visit(StringValue value) {
				// remove quotes
				int endIndex = Math.max(1, string.length()-1);
				String stringValue = string.substring(1, endIndex);
				
				value.set(stringValue);
			}
			
			public void visit(TimeValue value) {
				// to complete...				
			}
			
			public void visit(TimestampValue value) {
				// to complete...				
			}			
		}
		
		Table table = schema.getTable(tableName);
		Row row = super.addRow(table, valueFactory);
		
		List<Cell> cells = row.getCells();
		String[] valueStrings = rowData.split(",");
		
		SetValueFromString svfs = new SetValueFromString();
		
		for (int i=0; i < valueStrings.length; i++) {
			svfs.set(cells.get(i), valueStrings[i].trim());
		}
	}
}
