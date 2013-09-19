package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

/**
 * <p>
 * Contains the various objects relating to interacting with a HSQLDB DBMS.
 * </p>
 */
public class HyperSQLDBMS extends DBMS {

    private HyperSQLDatabaseInteractor databaseInteractor;

    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        if (databaseInteractor == null) {
            databaseInteractor = new HyperSQLDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
        }
        return databaseInteractor;
    }
    
    @Override
    public String getName() {
    	return "HyperSQL";
    }        

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
