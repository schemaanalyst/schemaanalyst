package org.schemaanalyst.database.hsqldb;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.database.DatabaseVisitor;

public class Hsqldb extends Database {
	
	private HsqldbDatabaseInteractor databaseInteraction = new HsqldbDatabaseInteractor();

	public DatabaseInteractor getDatabaseInteraction() {
		return databaseInteraction;
	}
	
	public void accept(DatabaseVisitor visitor) {
		visitor.visit(this);
	}	
}
