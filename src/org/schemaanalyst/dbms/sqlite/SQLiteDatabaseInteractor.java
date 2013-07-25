package org.schemaanalyst.dbms.sqlite;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;
import org.schemaanalyst.dbms.DatabaseInteractor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;

public class SQLiteDatabaseInteractor extends DatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(SQLiteDatabaseInteractor.class.getName());
    /**
     * The database name.
     */
    private String databaseName;

    public SQLiteDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        super(databaseConfiguration, locationConfiguration);
        this.databaseName = databaseName;
    }

    /**
     * Initialize the connection to the SQLite database.
     */
    @Override
    public void initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getSqliteDriver());
            LOGGER.log(Level.INFO, "Loading SQLite driver: {0}", databaseConfiguration.getSqliteDriver());

            File sqliteDirectory = new File(locationConfiguration.getDatabaseDir()
                    + File.separator + databaseConfiguration.getSqlitePath()
                    + File.separator + databaseName);
            if (sqliteDirectory.exists()) {
                LOGGER.log(Level.WARNING, "Database folder already exists: {0}", sqliteDirectory.getPath());
            }
            Files.createDirectories(sqliteDirectory.toPath());

            // create a database url; note that in this case for
            // SQLite, you are connecting to a file on the file
            // system, thus the used of Configuration.project
            String databaseUrl = "jdbc:sqlite:/" + sqliteDirectory.getAbsolutePath();
            LOGGER.log(Level.INFO, "JDBC Connection URL: {0}", databaseUrl);

            // enforce the foreign keys that are specified in the
            // relational schema; this could be turned off if you are
            // debugging and the data generation subsystem is not yet
            // generating data to satisfy the constraints.
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            // create the connection to the database,
            // using the properties for the SQLiteConfig
            // object to handle referential integrity's on/off switch
            connection = DriverManager.getConnection(databaseUrl, config.toProperties());

            initialized = true;
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}