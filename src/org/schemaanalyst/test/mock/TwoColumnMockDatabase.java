package org.schemaanalyst.test.mock;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.columntype.IntColumnType;

public class TwoColumnMockDatabase extends MockDatabase {

	public Column column1, column2;
	
	public TwoColumnMockDatabase() {
		Schema schema = new Schema("TwoColumnSchema");
		
		table = schema.createTable("table");
		column1 = table.addColumn("column1", new IntColumnType());
		column2 = table.addColumn("column2", new IntColumnType());
	}
}

