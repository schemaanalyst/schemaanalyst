package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class FourColumnMockDatabase extends MockDatabase {

    public Column column1, column2, column3, column4;

    public FourColumnMockDatabase() {
        Schema schema = new Schema("FourColumnSchema");

        table = schema.createTable("table");
        column1 = table.addColumn("column1", new IntDataType());
        column2 = table.addColumn("column2", new IntDataType());
        column3 = table.addColumn("column3", new IntDataType());
        column4 = table.addColumn("column4", new IntDataType());        
    }
}
