package org.schemaanalyst.dbms.hypersql;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DatabaseInteractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link DatabaseInteractor} object used to communicate with a database in a 
 * HSQLDB database.
 * </p>
 */
public class HyperSQLDatabaseInteractor extends DatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(HyperSQLDatabaseInteractor.class.getName());
    private static final String URL_SUFFIX = ";hsqldb.write_delay=false";
    /**
     * The database name.
     */
    private String databaseName;

    HyperSQLDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        super(databaseConfiguration, locationConfiguration);
        this.databaseName = databaseName;
    }

    /**
     * Initialize the connection to the HSQLDB database.
     */
    @Override
    public void initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getHsqldbDriver());
            LOGGER.log(Level.FINE, "Loading HSQLDB driver: {0}", databaseConfiguration.getHsqldbDriver());

            File hsqldbDirectory = new File(locationConfiguration.getDatabaseDir()
                    + File.separator + databaseConfiguration.getHsqldbPath()
                    + File.separator + databaseName);
            if (hsqldbDirectory.exists()) {
                LOGGER.log(Level.CONFIG, "Database folder already exists: {0}", hsqldbDirectory.getPath());
            }
            Files.createDirectories(hsqldbDirectory.toPath());

            // create a database url; note that in this case for
            // HSQLDB, you are connecting to a file on the file
            // system, thus the use of Configuration.project.  Also,
            // note that you should set the write_delay in the 
            // connection to the database supporting quick persists.
            String databaseUrl;
            if (!databaseConfiguration.getHsqldb_in_memory()) {
                databaseUrl = "jdbc:hsqldb:file:/" + hsqldbDirectory.getAbsolutePath() + URL_SUFFIX;
            } else {
                databaseUrl = "jdbc:hsqldb:mem:/database" + UUID.randomUUID().toString() + URL_SUFFIX;
            }
            LOGGER.log(Level.FINE, "JDBC Connection URL: {0}", databaseUrl);

            // create the connection to the database, 
            connection = DriverManager.getConnection(databaseUrl,
                    databaseConfiguration.getHsqldbUsername(),
                    databaseConfiguration.getHsqldbPassword());

            // tell hsqldb to always persist the data right away
            connection.setAutoCommit(true);

            initialized = true;
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public DatabaseInteractor duplicate() {
        return new HyperSQLDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
    }
}