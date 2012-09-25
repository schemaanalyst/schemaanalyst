package org.schemaanalyst.test.mock;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.columntype.IntColumnType;

public class OneColumnMockDatabase extends MockDatabase {
	
	public Column column;
	
	public OneColumnMockDatabase() {
		Schema schema = new Schema("SingleColumnSchema");
		
		table = schema.createTable("table");
		column = table.addColumn("column", new IntColumnType());
	}

}
