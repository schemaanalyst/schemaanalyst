package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TwoColumnMockDatabase extends MockDatabase {

    public Column column1, column2;

    public TwoColumnMockDatabase() {
        super(2);
        Schema schema = new Schema("TwoColumnSchema");

        table = schema.createTable("table");
        column1 = table.addColumn("column1", new IntDataType());
        column2 = table.addColumn("column2", new IntDataType());
    }
}
