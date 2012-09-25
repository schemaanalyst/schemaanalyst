package org.schemaanalyst.database.hsqldb;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.databaseinteraction.DatabaseInteractor;

public class Hsqldb extends Database {
	
	private HsqldbDatabaseInteractor databaseInteraction = new HsqldbDatabaseInteractor();

	public DatabaseInteractor getDatabaseInteraction() {
		return databaseInteraction;
	}

}
