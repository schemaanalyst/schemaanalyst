package org.schemaanalyst.dbms.mysql;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class MySQL extends DBMS {
    
	public DatabaseInteractor getDatabaseInteractor() {
		return null; // not implemented yet
	}
    
	public void accept(DBMSVisitor visitor) {
		visitor.visit(this);
	}
}
