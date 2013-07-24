package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class DerbySQLWriter extends SQLWriter {

    @Override
    public String writeDropTableStatement(Table table) {
        return writeDropTableStatement(table, false);
    }

    @Override
    public String writeDropTableStatement(Table table, boolean addIfExists) {
        String sql = "DROP TABLE ";
        //if (addIfExists) {
        //	sql += "IF EXISTS "; 
        //}
        sql += table.getName();
        return sql;
    }
}
