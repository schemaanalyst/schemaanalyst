package org.schemaanalyst.test.mock;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.datatype.IntDataType;

public class TwoColumnMockDatabase extends MockDatabase {

	public Column column1, column2;
	
	public TwoColumnMockDatabase() {
		Schema schema = new Schema("TwoColumnSchema");
		
		table = schema.createTable("table");
		column1 = table.addColumn("column1", new IntDataType());
		column2 = table.addColumn("column2", new IntDataType());
	}
}

