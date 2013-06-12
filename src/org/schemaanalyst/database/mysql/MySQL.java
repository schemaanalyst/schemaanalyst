package org.schemaanalyst.database.mysql;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseVisitor;
import org.schemaanalyst.databaseinteraction.DatabaseInteractor;

public class MySQL extends Database {
    
	public DatabaseInteractor getDatabaseInteraction() {
		return null; // not implemented yet
	}
    
	public void accept(DatabaseVisitor visitor) {
		visitor.visit(this);
	}
}
