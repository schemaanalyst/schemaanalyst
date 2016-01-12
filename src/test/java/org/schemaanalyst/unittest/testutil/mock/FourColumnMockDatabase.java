package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class FourColumnMockDatabase extends MockDatabase {

    public Column column1, column2, column3, column4;

    public FourColumnMockDatabase() {
        super(4);
        Schema schema = new Schema("FourColumnSchema");

        table = new Table("table");
        schema.addTable(table);
        column1 = new Column("column1", new IntDataType());                 
        column2 = new Column("column2", new IntDataType());
        column3 = new Column("column3", new IntDataType());
        column4 = new Column("column4", new IntDataType());
        
        table.addColumn(column1);
        table.addColumn(column2);
        table.addColumn(column3);
        table.addColumn(column4);
    }
}
