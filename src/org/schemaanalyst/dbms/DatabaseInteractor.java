package org.schemaanalyst.dbms;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;

public abstract class DatabaseInteractor {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInteractor.class.getName());
    /**
     * No database interaction return code.
     */
    protected static final int START = 0;
    /**
     * The return code to specify that there was an error in creating the SQL
     * schema with a CREATE TABLE.
     */
    protected static final int CREATE_TABLE_ERROR = -1;
    /**
     * The signature for the CREATE TABLE statement.
     */
    protected static final String CREATE_TABLE_SIGNATURE = "CREATE TABLE";
    /**
     * The return code indicates an UPDATE, INSERT, DELETE.
     */
    protected static final boolean UPDATE_COUNT = false;
    /**
     * The return code indicates a SELECT with a ResultSet.
     */
    protected static final boolean RESULT_SET = true;
    /**
     * The shared connection to the database
     */
    protected Connection connection;
    /**
     * The database configuration.
     */
    protected DatabaseConfiguration databaseConfiguration;
    /**
     * The location configuration.
     */
    protected LocationsConfiguration locationConfiguration;
    /**
     * Whether the connection has been made yet.
     */
    protected boolean initialized = false;

    public DatabaseInteractor(DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        this.locationConfiguration = locationConfiguration;
    }

    public abstract void initializeDatabaseConnection();

    /**
     * Execute a command against a database. Most useful for commands that
     * return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for an
     * INSERT, UPDATE, or DELETE.
     */
    public Integer executeUpdate(String command) {
        Integer returnCount = START;
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            LOGGER.log(Level.FINER, "Executing statement: {0}", command);
            Statement statement = connection.createStatement();
            returnCount = statement.executeUpdate(command);
            LOGGER.log(Level.FINE, "Statement: {0}\n Result: {1}", new Object[]{command, returnCount});
        } catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                returnCount = CREATE_TABLE_ERROR;
            } else {
                LOGGER.log(Level.FINE, "Statement failed: " + command, e);
            }
        }
        return returnCount;
    }

    /**
     * Execute a command against a database. Most useful for commands that
     * return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for a
     * statement that we do not know -- for U,I,D we still return the count and
     * for S we ignore result sets.
     *
     * I think that this only needs to be used with DBMonster integration.
     */
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
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                returnCount = CREATE_TABLE_ERROR;
            } else {
                LOGGER.log(Level.FINE, "Statement failed: " + command, e);
            }
        }
        return returnCount;
    }
}