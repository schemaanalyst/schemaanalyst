package org.schemaanalyst.dbms.postgres;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.hypersql.HyperSQLSQLWriter;

/**
 * <p>
 * Contains the various objects relating to interacting with a Postgres DBMS.
 * </p>
 */
public class PostgresDBMS extends DBMS {

    private PostgresDatabaseInteractor databaseInteractor;

    public PostgresDBMS() {
        sqlWriter = new PostgresSQLWriter();
        valueFactory = new ValueFactory();
    }

    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        if (databaseInteractor == null) {
            databaseInteractor = new PostgresDatabaseInteractor(databaseConfiguration, locationConfiguration);
        }
        return databaseInteractor;
    }

    @Override
    public String getName() {
    	return "Postgres";
    }    
    
    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
