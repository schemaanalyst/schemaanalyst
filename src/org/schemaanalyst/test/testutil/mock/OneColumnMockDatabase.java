package org.schemaanalyst.test.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class OneColumnMockDatabase extends MockDatabase {

    public Column column;

    public OneColumnMockDatabase() {
        super(1);
        Schema schema = new Schema("SingleColumnSchema");

        table = schema.createTable("table");
        column = table.addColumn("column", new IntDataType());
    }
}
