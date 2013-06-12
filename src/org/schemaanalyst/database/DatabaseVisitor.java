package org.schemaanalyst.database;

import org.schemaanalyst.database.derby.Derby;
import org.schemaanalyst.database.derby.DerbyNetwork;
import org.schemaanalyst.database.hsqldb.Hsqldb;
import org.schemaanalyst.database.mysql.MySQL;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.database.sqlite.SQLite;

public interface DatabaseVisitor {

	public void visit(Derby database);	
	
	public void visit(DerbyNetwork database);		
	
	public void visit(Hsqldb database);	
	
	public void visit(MySQL mySQL);	
	
	public void visit(Postgres database);	
	
	public void visit(SQLite database);
}
