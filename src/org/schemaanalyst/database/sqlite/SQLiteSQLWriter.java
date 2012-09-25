package org.schemaanalyst.database.sqlite;

import org.schemaanalyst.sqlwriter.SQLWriter;

public class SQLiteSQLWriter extends SQLWriter {
    protected void instanitateSubWriters() {
    	super.instanitateSubWriters();
    	valueSQLWriter = new SQLiteValueSQLWriter();
    }
}
