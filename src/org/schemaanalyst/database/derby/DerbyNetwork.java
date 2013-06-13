package org.schemaanalyst.database.derby;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.database.DatabaseVisitor;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class DerbyNetwork extends Database {

    private SQLWriter sqlWriter = new DerbySQLWriter();

    private DerbyDatabaseInteractor databaseInteraction = new DerbyNetworkDatabaseInteractor();

	public SQLWriter getSQLWriter() {
		return sqlWriter;
	}

	public DatabaseInteractor getDatabaseInteraction() {
		return databaseInteraction;
	}

	public void accept(DatabaseVisitor visitor) {
		visitor.visit(this);
	}	
}
