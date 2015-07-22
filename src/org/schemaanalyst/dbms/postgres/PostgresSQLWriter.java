package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * Created by phil on 22/07/2015.
 */
public class PostgresSQLWriter extends SQLWriter {

    @Override
    protected void instanitateSubWriters() {
        super.instanitateSubWriters();
        dataTypeSQLWriter = new PostgresDataTypeSQLWriter();
    }
}