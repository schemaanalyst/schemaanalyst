package org.schemaanalyst.dbms;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A DatabaseInteractor object is used to communicate with a database in some
 * DBMS. This encapsulates the various JDBC calls required to submit statements
 * to the database and handles the possible exceptions.
 * </p>
 *
 * <p>
 * This class can be used for most DBMSs with JDBC drivers, however specialised
 * subclasses are provided for some DBMSs where behaviour may need to be handled
 * differently.
 * </p>
 */
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
    protected long totalInteractions = 0;
    protected long createInteractions = 0;
    protected long dropInteractions = 0;
    protected long insertInteractions = 0;
    protected long deleteInteractions = 0;

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
            recordInteraction(command);
            synchronized (this) {
                returnCount = statement.executeUpdate(command);
            }
            LOGGER.log(Level.FINE, "Statement: {0}\n Result: {1}", new Object[]{command, returnCount});
        } catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                LOGGER.log(Level.FINEST, "Create table failed because: ", e);
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
            recordInteraction(command);
            synchronized (this) {

                boolean result = statement.execute(command);

                // this is a U,I,D that has an update count
                if (result == UPDATE_COUNT) {
                    returnCount = statement.getUpdateCount();
                }
            }
        } catch (SQLException e) {
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                LOGGER.log(Level.FINEST, "Create table failed because: ", e);
                returnCount = CREATE_TABLE_ERROR;
            } else {
                LOGGER.log(Level.FINE, "Statement failed: " + command, e);
            }
        }
        return returnCount;
    }

    public Integer executeCreatesAsTransaction(List<String> commands, int transactionSize) {
        return executeCreatesOrDropsAsTransaction(commands, transactionSize);
    }

    public Integer executeDropsAsTransaction(List<String> commands, int transactionSize) {
        return executeCreatesOrDropsAsTransaction(commands, transactionSize);
    }

    protected Integer executeCreatesOrDropsAsTransaction(List<String> commands, int transactionSize) {
        Integer result = START;
        for (int i = 0; i * transactionSize < commands.size(); i++) {
            int lower = i * transactionSize;
            int upper = (i + 1) * transactionSize;
            if (upper > commands.size()) {
                upper = commands.size();
            }
            result = executeUpdatesAsTransaction(commands.subList(lower, upper));
            if (result == 1) {
                return result;
            }
        }
        return result;
    }

    public Integer executeUpdatesAsTransaction(Iterable<String> commands) {
        Integer returnCount = START;
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }
            LOGGER.log(Level.FINE, "Starting transaction");
            synchronized (this) {
                connection.setAutoCommit(false);

                for (String command : commands) {
                    try {
                        LOGGER.log(Level.FINER, "Executing statement: {0} (in transaction)", command);
                        Statement statement = connection.createStatement();
                        recordInteraction(command);
                        returnCount = statement.executeUpdate(command);
                        LOGGER.log(Level.FINE, "Statement: {0}\n Result: {1}", new Object[]{command, returnCount});
                    } catch (SQLException e) {
                        if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                            LOGGER.log(Level.FINE, "Create table failed: {0}", command);
                            LOGGER.log(Level.FINEST, "Create table failed because: ", e);
                            returnCount = CREATE_TABLE_ERROR;
                        } else {
                            LOGGER.log(Level.FINE, "Statement failed: " + command, e);
                        }
                        connection.rollback();
                        break;
                    }
                }

                LOGGER.log(Level.FINE, "Ending transaction");
                connection.commit();
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Transaction failed: {0}", e.getMessage());
        }
        return returnCount;
    }

    public Integer executeUpdatesAsBatch(Iterable<String> commands) {
        Integer returnCount = START;
        try {
            if (!initialized) {
                initializeDatabaseConnection();
            }

            Statement statement = connection.createStatement();
            for (String command : commands) {
                try {
                    LOGGER.log(Level.FINE, "Executing statement: {0} (in batch)", command);
                    statement.addBatch(command);
                } catch (SQLException e) {
                    LOGGER.log(Level.FINE, "Statement failed: " + command, e);
                }
            }
            // Only one "interaction" is made. Classify based on first item
            recordInteraction(commands.iterator().next());
            synchronized (this) {
                int[] batchResults = statement.executeBatch();
                for (int i : batchResults) {
                    if (i == 1) {
                        returnCount = 1;
                        break;
                    }
                }
            }
            LOGGER.log(Level.FINE, "Batch succeeded: {0}", returnCount);
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, "Batch failed: {0}", e.getMessage());
        }
        return returnCount;
    }

    /**
     * @TODO This method should be removed and is for debugging only.
     * @return The number of tables in a database
     */
    public int getTableCount() {
        return 0;
    }

    /**
     * Increments the appropriate counter to record the database interaction.
     *
     * @param stmt The statement executed
     */
    protected synchronized void recordInteraction(String stmt) {
        totalInteractions++;
        // Identify subtotal to increment
        String statement = stmt.toLowerCase();
        if (statement.startsWith("insert")) {
            insertInteractions++;
        } else if (statement.startsWith("create")) {
            createInteractions++;
        } else if (statement.startsWith("drop")) {
            dropInteractions++;
        } else if (statement.startsWith("delete")) {
            deleteInteractions++;
        } else {
            LOGGER.log(Level.WARNING, "Unclassified database interaction: {0}", stmt);
        }
    }

    public long getTotalInteractions() {
        return totalInteractions;
    }

    public long getCreateInteractions() {
        return createInteractions;
    }

    public long getDropInteractions() {
        return dropInteractions;
    }

    public long getInsertInteractions() {
        return insertInteractions;
    }

    public long getDeleteInteractions() {
        return deleteInteractions;
    }
}
