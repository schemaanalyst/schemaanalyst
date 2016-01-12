package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TwoColumnMockDatabase extends MockDatabase {

    public Column column1, column2;

    public TwoColumnMockDatabase() {
        super(2);
        Schema schema = new Schema("TwoColumnSchema");

        table = new Table("table");
        schema.addTable(table);
        column1 = new Column("column1", new IntDataType());                 
        column2 = new Column("column2", new IntDataType());
        
        table.addColumn(column1);
        table.addColumn(column2);
    }
}
