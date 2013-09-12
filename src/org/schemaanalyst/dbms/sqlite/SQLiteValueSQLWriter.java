package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlwriter.ValueSQLWriter;

/**
 * <p>
 * A {@link ValueSQLWriter} that converts {@link Value} objects into their 
 * String equivalents for use in SQL statements for the SQLite DBMS.
 * </p>
 */
public class SQLiteValueSQLWriter extends ValueSQLWriter {

    @Override
    public String writeBooleanValue(BooleanValue booleanValue) {
        return booleanValue.get() ? "1" : "0";
    }
}
