package org.schemaanalyst.database.mysql;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.database.DatabaseVisitor;

public class MySQL extends Database {
    
	public DatabaseInteractor getDatabaseInteraction() {
		return null; // not implemented yet
	}
    
	public void accept(DatabaseVisitor visitor) {
		visitor.visit(this);
	}
}
