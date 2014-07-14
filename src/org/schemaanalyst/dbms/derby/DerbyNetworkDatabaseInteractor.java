package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DatabaseInteractor;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link DatabaseInteractor} object used to communicate with a database in a 
 * Derby database over a network.
 * </p>
 */
public class DerbyNetworkDatabaseInteractor extends DerbyDatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(DerbyNetworkDatabaseInteractor.class.getName());
    private static final String URL_SUFFIX = ";create=true";

    DerbyNetworkDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        super(databaseName, databaseConfiguration, locationConfiguration);
    }

    /**
     * Initialize the connection to the Derby database.
     */
    @Override
    public void initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getDerbyDriver());
            LOGGER.log(Level.INFO, "Loading HSQLDB driver: {0}", databaseConfiguration.getDerbyDriver());

            // create a database url, this time making a connection
            // to the network interface for Derby
            String databaseUrl = "jdbc:derby://" + databaseConfiguration.getDerbyHost()
                    + ":" + databaseConfiguration.getDerbyPort()
                    + "/" + databaseName + URL_SUFFIX;
            LOGGER.log(Level.INFO, "JDBC Connection URL: {0}", databaseUrl);

            // create the connection to the database; do not use
            // a user name or a password so that Derby will always
            // create the schema called "APP" which can be accessed
            // even if a CREATE SCHEMA statement has not been issued
            connection = DriverManager.getConnection(databaseUrl);

            // tell Derby to always persist the data right away
            connection.setAutoCommit(true);

            initialized = true;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public DatabaseInteractor duplicate() {
        return new DerbyNetworkDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
    }
}