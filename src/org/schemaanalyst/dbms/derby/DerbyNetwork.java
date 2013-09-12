package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * <p>
 * Contains the various objects relating to interacting with a Derby DBMS that 
 * is accessed over a network. This may be hosted on the local host or an 
 * alternative address, according to the {@link DatabaseConfiguration}.
 * </p>
 */
public class DerbyNetwork extends DBMS {

    private SQLWriter sqlWriter = new DerbySQLWriter();
    private DerbyDatabaseInteractor databaseInteractor;

    @Override
    public SQLWriter getSQLWriter() {
        return sqlWriter;
    }

    @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        if (databaseInteractor == null) {
            databaseInteractor = new DerbyNetworkDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
        }
        return databaseInteractor;
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
