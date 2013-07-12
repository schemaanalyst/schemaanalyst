package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.sqlwriter.SQLWriter;

public class SQLiteSQLWriter extends SQLWriter {

    protected void instanitateSubWriters() {
        super.instanitateSubWriters();
        valueSQLWriter = new SQLiteValueSQLWriter();
    }
}
