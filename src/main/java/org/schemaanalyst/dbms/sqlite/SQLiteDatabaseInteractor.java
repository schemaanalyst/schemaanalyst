package org.schemaanalyst.dbms.sqlite;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link DatabaseInteractor} object used to communicate with a database in an
 * SQLite database.
 * </p>
 */
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
            LOGGER.log(Level.FINE, "Loading SQLite driver: {0}", databaseConfiguration.getSqliteDriver());

            File sqliteDirectory = new File(locationConfiguration.getDatabaseDir()
                    + File.separator + databaseConfiguration.getSqlitePath());
            if (sqliteDirectory.exists()) {
                LOGGER.log(Level.CONFIG, "Database folder already exists: {0}", sqliteDirectory.getPath());
            }
            Files.createDirectories(sqliteDirectory.toPath());

            String databaseUrl;
            if (!databaseConfiguration.getSqliteInMemory()) {
                databaseUrl = "jdbc:sqlite:" + sqliteDirectory.getAbsolutePath() + File.separator + databaseName;
            } else {
                databaseUrl = "jdbc:sqlite::memory:";
            }
            LOGGER.log(Level.FINE, "JDBC Connection URL: {0}", databaseUrl);

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

    @Override
    public int getTableCount() {
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM sqlite_master WHERE type='table';")) {
                resultSet.next();
                int result = resultSet.getInt(1);
                return result;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public DatabaseInteractor duplicate() {
        return new SQLiteDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
    }
}