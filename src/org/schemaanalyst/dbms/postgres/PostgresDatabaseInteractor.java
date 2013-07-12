package org.schemaanalyst.dbms.postgres;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.dbms.DatabaseInteractor;

public class PostgresDatabaseInteractor extends DatabaseInteractor {

    /**
     * The shared connection to the database
     */
    private Connection connection;
    /**
     * This is the "Postgres" database that stores all database info
     */
    private static final String POSTGRES_DATABASE = "postgres";
    /**
     * This variable indicates whether or not the database connection has
     * already been initialized
     */
    private boolean initialize;

    /**
     * Constructor.
     */
    public PostgresDatabaseInteractor() {
        connection = null;
        initialize = false;
    }

    /**
     * Initialize the connection to the Postgres database.
     */
    public void initializeDatabaseConnection() {
        connection = null;
        try {
            // load the PostgresSQL driver using reflection 
            // spy using the P6spy interception drier
            if (Configuration.spy) {
                Class.forName("com.p6spy.engine.spy.P6SpyDriver");
            } // do not spy using the P6spy interception driver
            else {
                Class.forName("org.postgresql.Driver");
            }

            if (Configuration.debug) {
                System.out.println();
                System.out.println("JDBC Driver Registered.");
            }

            // note that right now the Postgres database management
            // system is hosting the "database" in the default
            // database which is called "postgres" ; we are not
            // creating a separate database because this is more
            // complex and does not provide any special features as
            // long as we name the mutant tables correctly
            String database = "jdbc:postgresql://"
                    + Configuration.host + ":"
                    + Configuration.port + "/"
                    + POSTGRES_DATABASE;
            connection =
                    DriverManager.
                    getConnection(database,
                    Configuration.user,
                    Configuration.password);

            if (Configuration.debug) {
                System.out.println("JDBC Resource.");
                System.out.println(database);
            }

            // tell Postgres to always auto-commit results to the database
            connection.setAutoCommit(true);

            if (Configuration.debug) {
                if (connection != null) {
                    System.out.println("JDBC Connection Okay.");
                } else {
                    System.out.println("Connection is Null.");
                }
            }

            // show that we have already initialized the database
            initialize = true;
        } catch (Exception e) {
            if (Configuration.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Execute a command against a Postgres database. Most useful for commands
     * that return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for an
     * INSERT, UPDATE, or DELETE.
     */
    public Integer executeUpdate(String command) {
        // create a List for storing the return counts that
        // tell us how many records were modified in the database
        Integer returnCounts = new Integer(START);

        try {
            // initialize the connection to Postgres only if it 
            // has not already been initialized 
            if (!initialize) {
                initializeDatabaseConnection();
            }

            if (Configuration.debug) {
                System.out.println();
                System.out.println("command = " + command);
            }

            // create the statement that we use for the commands
            Statement statement = connection.createStatement();

            // run the command and capture the number of modified
            // values or any other type of status return code
            int count = statement.executeUpdate(command);
            returnCounts = new Integer(count);

        } // display information about the error message
        catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                returnCounts = CREATE_TABLE_ERROR;
                //System.out.println("still born executeUpdate");
            }

            // produce debugging output for this exception
            if (Configuration.debug) {
                System.out.println("Error Code: " + e.getErrorCode());
                System.out.println("Message: " + e.getMessage());

                if (!e.getMessage().equals("not an error")) {
                    e.printStackTrace();
                }
            }
        } // do not close this connect any more, it will cause errors
        finally {
            try {
                //connection.close();
            } catch (Exception e) {
            }
        }

        return returnCounts;

    }

    /**
     * Execute a command against a Postgres database. Most useful for commands
     * that return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for a
     * statement that we do not know -- for U,I,D we still return the count and
     * for S we ignore result sets.
     *
     * I think that this only needs to be used with DBMonster integration.
     */
    public Integer execute(String command) {
        // create a List for storing the return counts that
        // tell us how many records were modified in the database
        Integer returnCounts = new Integer(START);

        try {
            // initialize the connection to Postgres only if it 
            // has not already been initialized 
            if (!initialize) {
                initializeDatabaseConnection();
            }

            if (Configuration.debug) {
                System.out.println();
                System.out.println("command = " + command);
            }

            // create the statement that we use for the commands
            Statement statement = connection.createStatement();

            // run the command and capture the number of modified
            // values or any other type of status return code
            boolean result = statement.execute(command);
            int count = START;

            // this is a U,I,D that has an update count
            if (result == UPDATE_COUNT) {
                count = statement.getUpdateCount();
            } // this is a sql select that returned a result set; ignore
            else if (result == RESULT_SET) {
            }

            returnCounts = new Integer(count);
        } // display information about the error message
        catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                returnCounts = CREATE_TABLE_ERROR;
                System.out.println("Still born execute!");
            }

            if (Configuration.debug) {
                System.out.println("Error Code: " + e.getErrorCode());
                System.out.println("Message: " + e.getMessage());
                if (!e.getMessage().equals("not an error")) {
                    e.printStackTrace();
                }
            }
        } // do not close this connect any more, it will cause errors
        finally {
            try {
                //connection.close();
            } catch (Exception e) {
            }
        }

        return returnCounts;
    }
}