package org.schemaanalyst.test.mock;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.datatype.IntDataType;

public class OneColumnMockDatabase extends MockDatabase {
	
	public Column column;
	
	public OneColumnMockDatabase() {
		Schema schema = new Schema("SingleColumnSchema");
		
		table = schema.createTable("table");
		column = table.addColumn("column", new IntDataType());
	}

}
