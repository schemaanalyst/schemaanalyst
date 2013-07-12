package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.sqlwriter.ValueSQLWriter;

public class SQLiteValueSQLWriter extends ValueSQLWriter {

    public String writeBooleanValue(BooleanValue booleanValue) {
        return booleanValue.get() ? "1" : "0";
    }
}
