package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class OneColumnMockDatabase extends MockDatabase {

    public Column column;

    public OneColumnMockDatabase() {
        super(1);
        Schema schema = new Schema("SingleColumnSchema");

        table = new Table("table");
        schema.addTable(table);
        column = new Column("column", new IntDataType());                 
        table.addColumn(column);
    }
}
