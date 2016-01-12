package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * <p>
 * An SQLWriter for converting the SchemaAnalyst internal representation of SQL 
 * into SQL statements for the Derby DBMS.
 * </p>
 */
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
