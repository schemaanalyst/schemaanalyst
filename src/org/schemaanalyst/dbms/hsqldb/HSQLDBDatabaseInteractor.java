package org.schemaanalyst.dbms.hsqldb;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.deprecated.configuration.Configuration;

public class HSQLDBDatabaseInteractor extends DatabaseInteractor {

    /**
     * The shared connection to the database
     */
    private Connection connection;
    /**
     * This variable indicates whether or not the database connection has
     * already been initialized
     */
    private boolean initialize;

    /**
     * Constructor.
     */
    public HSQLDBDatabaseInteractor() {
        connection = null;
        initialize = false;
    }

    /**
     * Initialize the connection to the HSQLDB database.
     */
    public void initializeDatabaseConnection() {
        // the connection is initially null
        connection = null;

        try {
            // load the HSQLDB driver using reflection
            //Class.forName("org.hsqldb.jdbc.JDBCDriver");

            // load the Hsqldb driver using reflection 
            // spy using the P6spy interception drier
            if (Configuration.spy) {
                Class.forName("com.p6spy.engine.spy.P6SpyDriver");
            } // do not spy using the P6spy interception driver
            else {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
            }

            if (Configuration.debug) {
                System.out.println();
                System.out.println("JDBC Driver Registered.");
            }

            // create a database url; note that in this case for
            // HSQLDB, you are connecting to a file on the file
            // system, thus the use of Configuration.project.  Also,
            // note that you should set the write_delay in the 
            // connection to the database supporting quick persists.
            String database = "jdbc:hsqldb:file:/" + Configuration.project
                    + "Databases/" + Configuration.type + "/"
                    + Configuration.database + ";hsqldb.write_delay=false";

            if (Configuration.debug) {
                System.out.println("JDBC Resource.");
                System.out.println(database);
            }

            // create the connection to the database, using the
            // properties for the SQLiteConfig object (handle
            // referential integrity)
            connection = DriverManager.getConnection(database,
                    Configuration.user,
                    Configuration.password);

            // tell hsqldb to always persist the data right away
            connection.setAutoCommit(true);

            if (Configuration.debug) {
                if (connection != null) {
                    System.out.println("JDBC Connection Okay.");
                } else {
                    System.out.println("Connection is Null.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            if (Configuration.debug) {
                e.printStackTrace();
            }
        }

        // show that we have already initialized the database
        initialize = true;
    }

    /**
     * Execute a command against an HSQLDB database. Most useful for commands
     * that return a code, such as whether or not a command worked or how many
     * records were changed inside of a table. So, this is designed for an
     * INSERT, UPDATE, or DELETE.
     */
    public Integer executeUpdate(String command) {
        // create a List for storing the return counts that
        // tell us how many records were modified in the database
        Integer returnCounts = new Integer(START);

        try {
            // initialize the connection to HSQLDB; note that right
            // now I am always creating a connection and then
            // destroying it when finished with the database interaction;
            // this is not efficient but eases debugging and is better
            // for ensuring the correctness of the system
            //initializeDatabaseConnection();

            // initialize the connection to HSQLDB only if it 
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

            // set the WRITE DELAY to zero because in
            // certain versions of HSQLDB this is needed
            // to make sure that the data quickly persists
            // to the file system
            statement.execute("SET WRITE_DELAY 0");

            // run the command and capture the number of modified
            // values or any other type of status return code
            int count = statement.executeUpdate(command);
            returnCounts = new Integer(count);

        } // display information about the error message; note that
        // SQLite can return a code that is not really an error.
        // However, there are others that we will need to later
        // capture and handle during mutation testing. For instance,
        // one of the codes is related to "foreign key constraint
        // failed" and this would show that a mutant was killed
        catch (SQLException e) {
            // if this command is a create table statement and it through 
            // an exception, then set the return code to -1 to indicate 
            // that there was a special failure in creating the schema
            if (command.toUpperCase().contains(CREATE_TABLE_SIGNATURE)) {
                returnCounts = CREATE_TABLE_ERROR;
            }

            if (Configuration.debug) {
                System.out.println("Error Code: " + e.getErrorCode());
                System.out.println("Message: " + e.getMessage());
                if (!e.getMessage().equals("not an error")) {
                    e.printStackTrace();
                }
            }
        } // this connection no longer needs to be closed
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
            // initialize the connection to HSQLDB; note that right
            // now I am always creating a connection and then
            // destroying it when finished with the database interaction;
            // this is not efficient but eases debugging and is better
            // for ensuring the correctness of the system
            //initializeDatabaseConnection();

            // initialize the connection to HSQLDB only if it 
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

            // set the WRITE DELAY to zero because in
            // certain versions of HSQLDB this is needed
            // to make sure that the data quickly persists
            // to the file system
            statement.execute("SET WRITE_DELAY 0");

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

            // run the command and capture the number of modified
            // values or any other type of status return code
            // int count = statement.executeUpdate(command);
            // returnCounts = new Integer(count);
        } // display information about the error message; note that
        // SQLite can return a code that is not really an error.
        // However, there are others that we will need to later
        // capture and handle during mutation testing. For instance,
        // one of the codes is related to "foreign key constraint
        // failed" and this would show that a mutant was killed
        catch (SQLException e) {
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            if (!e.getMessage().equals("not an error")) {
                e.printStackTrace();
            }
        } // this connection no longer needs to be closed
        finally {
            try {
                //connection.close();
            } catch (Exception e) {
            }
        }
        return returnCounts;
    }
}