package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * <p>
 * An SQLWriter for converting the SchemaAnalyst internal representation of SQL 
 * into SQL statements for the SQLite DBMS.
 * </p>
 */
public class SQLiteSQLWriter extends SQLWriter {

    @Override
    protected void instanitateSubWriters() {
        super.instanitateSubWriters();
        valueSQLWriter = new SQLiteValueSQLWriter();
    }
}
