package org.schemaanalyst.unittest.testutil.schema;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;

/**
 * Created by phil on 24/03/2014.
 */
public class AllValues extends Schema {

    public AllValues() {
        super("AllValues");

        Table table = this.createTable("table");

        table.createColumn("boolean_column", new BooleanDataType());
        table.createColumn("datetime_column", new DateTimeDataType());
        table.createColumn("date_column", new DateDataType());
        table.createColumn("numeric_column", new IntDataType());
        table.createColumn("string_column", new VarCharDataType(10));
        table.createColumn("timestamp_column", new TimestampDataType());
        table.createColumn("time_column", new TimeDataType());
    }
}
