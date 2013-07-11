package org.schemaanalyst.dbms;

import org.schemaanalyst.dbms.derby.Derby;
import org.schemaanalyst.dbms.derby.DerbyNetwork;
import org.schemaanalyst.dbms.hsqldb.HSQLDB;
import org.schemaanalyst.dbms.mysql.MySQL;
import org.schemaanalyst.dbms.postgres.Postgres;
import org.schemaanalyst.dbms.sqlite.SQLite;

public interface DBMSVisitor {

	public void visit(Derby dbms);	
	
	public void visit(DerbyNetwork dbms);		
	
	public void visit(HSQLDB dbms);	
	
	public void visit(MySQL dbms);	
	
	public void visit(Postgres dbms);	
	
	public void visit(SQLite dbms);
}
