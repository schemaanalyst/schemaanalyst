package org.schemaanalyst.database.derby;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.database.DatabaseInteractor;

public class DerbyDatabaseInteractor extends DatabaseInteractor {

        /** The shared connection to the database */
        protected Connection connection;

        /** The Derby error code for DROP TABLES */
        private static final String DERBY_ERROR_CODE = "42Y55"; 

    /** This variable indicates whether or not the database connection has already been initialized */
    private boolean initialize;

	/**
	 * Constructor.
	 */
	public DerbyDatabaseInteractor() {
	    connection = null;
	    initialize = false;
	}

	/**
	 * Initialize the connection to the Derby database.
	 */
	public void initializeDatabaseConnection() {
		// the connection is initially null
		connection = null;

		try {
			// load the Derby driver using reflection
		        //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		    // load the Derby driver using reflection 
		    // spy using the P6spy interception driver
		    if( Configuration.spy ) {
			Class.forName("com.p6spy.engine.spy.P6SpyDriver");
		    }
		    // do not spy using the P6spy interception drier
		    else {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		    }

			if (Configuration.debug) {
				System.out.println();
				System.out.println("JDBC Driver Registered.");
			}

			// create a database url; note that in this case for
			// Derby, you are connecting to a file on the file
			// system, thus the use of Configuration.project.  
			String database = "jdbc:derby:" + Configuration.project
			    + "Databases/" + Configuration.type + "/" + 
			    Configuration.database + ";";

			if (Configuration.debug) {
				System.out.println("JDBC Resource.");
				System.out.println(database);
			}

			// create the connection to the database; do not use
			// a user name or a password so that Derby will always
			// create the schema called "APP" which can be accessed
			// even if a CREATE SCHEMA statement has not been issued
			connection = DriverManager.getConnection(database); 

			// tell Derby to always persist the data right away
			connection.setAutoCommit(true);

			if (Configuration.debug) {
			    if (connection != null) {
				System.out.println("JDBC Connection Okay.");
			    }
			    else {
				System.out.println("Connection is Null.");
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// show that we have already initialized the database
		initialize = true;
	}

	/**
	 * Execute a command against a Derby database. Most useful for commands
	 * that return a code, such as whether or not a command worked or how many
	 * records were changed inside of a table.
	 * So, this is designed for an INSERT, UPDATE, or DELETE.
	 */
	public Integer executeUpdate(String command) {
		// create a List for storing the return counts that
		// tell us how many records were modified in the database
	        Integer returnCounts = new Integer(START);

		try {
			// initialize the connection to Derby only if it 
			// has not already been initialized 
			if( !initialize ) {
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

		}

		// display information about the error message.  Note that for Derby we
		// are currently checking an error code to determine if the problem 
		// was related to the fact that this database does not support the IF
		// EXISTS part of a DROP TABLE.  So, we have to blindly execute the 
		// DROP TABLE, let it fail, and then catch the error code to know that 
		// a real error did not, in fact, take place.  This will only happen
		// in a situation when there is no table already in exists (first run).
		catch (SQLException e) {

		     if (!e.getSQLState().equals(DERBY_ERROR_CODE)) {
			     System.out.println("Error Code: " + e.getErrorCode());
			     System.out.println("Message: " + e.getMessage());
			     if (!e.getMessage().equals("not an error"))
				 e.printStackTrace();
		     }
		     else {
			 if(Configuration.debug) {
			     System.out.println("Derby DROP TABLE failed as expected. Okay.");
			 }
		     }
		}

		finally {
			try {
			    //connection.close();
			} catch (Exception e) {
			}
		}

		return returnCounts;
	}
    
    /**
     * Execute a command against a Postgres database. Most useful for
     * commands that return a code, such as whether or not a command
     * worked or how many records were changed inside of a table. So,
     * this is designed for a statement that we do not know -- for
     * U,I,D we still return the count and for S we ignore result sets.
     *
     * I think that this only needs to be used with DBMonster integration.
     */
    public Integer execute(String command) {
	// create a List for storing the return counts that
	// tell us how many records were modified in the database
	Integer returnCounts = new Integer(START);

	try {
	    // initialize the connection to Derby only if it 
	    // has not already been initialized 
	    if( !initialize ) {
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
	    if(result == UPDATE_COUNT) {
		count = statement.getUpdateCount();
	    }

	    // this is a sql select that returned a result set; ignore
	    else if(result == RESULT_SET) {
	    }

	    returnCounts = new Integer(count);

	    // run the command and capture the number of modified
	    // values or any other type of status return code
	    //int count = statement.executeUpdate(command);
	    //returnCounts = new Integer(count);
	}

	// display information about the error message.  Note that for Derby we
	// are currently checking an error code to determine if the problem 
	// was related to the fact that this database does not support the IF
	// EXISTS part of a DROP TABLE.  So, we have to blindly execute the 
	// DROP TABLE, let it fail, and then catch the error code to know that 
	// a real error did not, in fact, take place.  This will only happen
	// in a situation when there is no table already in exists (first run).
	catch (SQLException e) {

	    if (!e.getSQLState().equals(DERBY_ERROR_CODE)) {
		System.out.println("Error Code: " + e.getErrorCode());
		System.out.println("Message: " + e.getMessage());
		if (!e.getMessage().equals("not an error"))
		    e.printStackTrace();
	    }
	    else {
		if(Configuration.debug) {
		    System.out.println("Derby DROP TABLE failed as expected. Okay.");
		}
	    }
	}
	
	finally {
	    try {
		//connection.close();
	    } catch (Exception e) {
	    }
	}
	return returnCounts;
    }
}