package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DatabaseInteractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link DatabaseInteractor} object used to communicate with a database in a
 * Derby database.
 * </p>
 */
public class DerbyDatabaseInteractor extends DatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(DerbyDatabaseInteractor.class.getName());
    /**
     * The Derby error code for DROP TABLES.
     */
    private static final String DERBY_ERROR_CODE = "42Y55";
    /**
     * The name for the database.
     */
    protected String databaseName;

    DerbyDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        super(databaseConfiguration, locationConfiguration);
        this.databaseName = databaseName;
    }

    /**
     * Initialize the connection to the Derby database.
     */
    @Override
    public void initializeDatabaseConnection() {
        try {
            Class.forName(databaseConfiguration.getDerbyDriver());
            LOGGER.log(Level.INFO, "Loading Derby driver: {0}", databaseConfiguration.getDerbyDriver());

            File derbyDirectory = new File(locationConfiguration.getDatabaseDir()
                    + File.separator + databaseConfiguration.getDerbyPath()
                    + File.separator + databaseName);
            if (derbyDirectory.exists()) {
                LOGGER.log(Level.WARNING, "Database folder already exists: {0}", derbyDirectory.getPath());
            }
            Files.createDirectories(derbyDirectory.toPath());

            // create a database url; note that in this case for
            // Derby, you are connecting to a file on the file
            // system, thus the use of Configuration.project.  
            String databaseUrl = "jdbc:derby:" + derbyDirectory + ";";
            LOGGER.log(Level.INFO, "JDBC Connection URL: {0}", databaseUrl);

            // create the connection to the database; do not use
            // a user name or a password so that Derby will always
            // create the schema called "APP" which can be accessed
            // even if a CREATE SCHEMA statement has not been issued
            connection = DriverManager.getConnection(databaseUrl);

            // tell Derby to always persist the data right away
            connection.setAutoCommit(true);

            initialized = true;
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Execute a command against a Derby database. Most useful for commands that
     * return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for an
     * INSERT, UPDATE, or DELETE.
     */
    @Override
    public Integer executeUpdate(String command) {
        Integer returnCount = START;
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            LOGGER.log(Level.FINE, "Executing statement: {0}", command);
            Statement statement = connection.createStatement();
            returnCount = statement.executeUpdate(command);
            LOGGER.log(Level.FINE, "Statement: {0}\n Result: {1}", new Object[]{command, returnCount});
        } catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (e.getSQLState().equals(DERBY_ERROR_CODE)) {
                LOGGER.log(Level.INFO, "Derby DROP TABLE failed (was expected): {0}", command);
            } else if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                returnCount = CREATE_TABLE_ERROR;
            } else {
                LOGGER.log(Level.FINE, "Statement failed: " + command, e);
            }
        }
        return returnCount;
    }

    /**
     * Execute a command against a Derby database. Most useful for commands that
     * return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for a
     * statement that we do not know -- for U,I,D we still return the count and
     * for S we ignore result sets.
     *
     * I think that this only needs to be used with DBMonster integration.
     */
    @Override
    public Integer execute(String command) {
        Integer returnCount = START;
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            LOGGER.log(Level.FINE, "Executing statement: {0}", command);
            Statement statement = connection.createStatement();

            // run the command and capture the number of modified
            // values or any other type of status return code
            boolean result = statement.execute(command);

            // this is a U,I,D that has an update count
            if (result == UPDATE_COUNT) {
                returnCount = statement.getUpdateCount();
            }
        } catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (e.getSQLState().equals(DERBY_ERROR_CODE)) {
                LOGGER.log(Level.INFO, "Derby DROP TABLE failed (was expected): {0}", command);
            } else if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                returnCount = CREATE_TABLE_ERROR;
            } else {
                LOGGER.log(Level.FINE, "Statement failed: " + command, e);
            }
        }
        return returnCount;
    }

    @Override
    public DatabaseInteractor duplicate() {
        return new DerbyDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
    }
}