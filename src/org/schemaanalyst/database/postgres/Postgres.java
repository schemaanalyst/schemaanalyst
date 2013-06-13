package org.schemaanalyst.database.postgres;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.database.DatabaseVisitor;

public class Postgres extends Database {
    private PostgresDatabaseInteractor databaseInteraction = new PostgresDatabaseInteractor();

    public Postgres() {
    	sqlWriter = new PostgresSQLWriter();
    }

    public DatabaseInteractor getDatabaseInteraction() {
    	return databaseInteraction;
    }
    
	public void accept(DatabaseVisitor visitor) {
		visitor.visit(this);
	}    
}
