package org.schemaanalyst.database.postgres;

import org.schemaanalyst.sqlwriter.SQLWriter;

public class PostgresSQLWriter extends SQLWriter {

    protected void instanitateSubWriters() {
    	super.instanitateSubWriters();
	attributeSQLWriter = new PostgresAttributeSQLWriter();
    }
}
