package org.schemaanalyst.database.derby;

import org.schemaanalyst.representation.*;
import org.schemaanalyst.sqlwriter.SQLWriter;


public class DerbySQLWriter extends SQLWriter {

    public String writeDropTableStatement(Table table) {
    	return writeDropTableStatement(table, false); 
    }
  
    public String writeDropTableStatement(Table table, boolean addIfExists) {
		String sql = "DROP TABLE ";
		//if (addIfExists) {
		//	sql += "IF EXISTS "; 
		//}
		sql += table.getName();
		return sql;
    }	
 
}
