package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.DatabaseInteractor;

/**
 * <p>
 * Contains the various objects relating to interacting with an SQLite DBMS.
 * </p>
 */
public class SQLiteDBMS extends DBMS {

    private SQLiteDatabaseInteractor databaseInteractor;

    public SQLiteDBMS() {
        sqlWriter = new SQLiteSQLWriter();
        valueFactory = new ValueFactory();
    }

    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        if (databaseInteractor == null) {
            databaseInteractor = new SQLiteDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
        }
        return databaseInteractor;
    }
    
    @Override
    public String getName() {
    	return "SQLite";
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
