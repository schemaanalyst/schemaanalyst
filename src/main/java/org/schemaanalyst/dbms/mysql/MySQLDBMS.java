package org.schemaanalyst.dbms.mysql;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.dbms.DatabaseInteractor;

/**
 * <p>
 * Contains the various objects relating to interacting with a MySQL DBMS.
 * </p>
 */
public class MySQLDBMS extends DBMS {

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
    	return "MySQL";
    }        
    
    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
