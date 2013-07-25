package org.schemaanalyst.dbms.mysql;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;

public class MySQL extends DBMS {

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
