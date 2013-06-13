package org.schemaanalyst.database.sqlite;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.script.ScriptCreator;

import java.io.File;

public class SQLiteDatabaseInteractor extends DatabaseInteractor {

	/** The shared connection to the database */
	private Connection connection;

        /** The flag indicating whether we issued the pragma */
        private boolean pragmaIssued;

        /** This variable indicates whether or not the database connection has already been initialized */
        private boolean initialize;

        /** The PRAGMA command */
        private static final String PRAGMA = "PRAGMA foreign_keys = ON;";

	/**
	 * Constructor.
	 */
	public SQLiteDatabaseInteractor() {
		connection = null;
		pragmaIssued = false;
		initialize = false;
	}

	/**
	 * Initialize the connection to the SQLite database.
	 */
	public void initializeDatabaseConnection() {
		// the connection is initially null
		connection = null;

		try {
		    
		    // load the SQLite driver using reflection 
		    // spy using the P6spy interception drier
		    if( Configuration.spy ) {
			Class.forName("com.p6spy.engine.spy.P6SpyDriver");
		    }
		    // do not spy using the P6spy interception driver
		    else {
			Class.forName("org.sqlite.JDBC");
		    }

			if (Configuration.debug) {
				System.out.println();
				System.out.println("JDBC Driver Registered.");
			}

			// create the databases directory for storing the databases
			// from a wide variety of databases that use the file system
			File databasesDirectory = new File(Configuration.project +
							   "Databases/");

			// if the Databases/ directory does not exist, then create it
			if (!databasesDirectory.exists()) {
			    databasesDirectory.mkdir();  
			}

			// create a file up to and including the directory that
			// will store the SQLite database files (one file per
			// database that we produce)
			File sqliteDirectory = new File(Configuration.project +
							"Databases/" +
							Configuration.type);

			// if the SQLite directory does not exist, then create it
			if (!sqliteDirectory.exists()) {
			    sqliteDirectory.mkdir();  
			}

			// create a database url; note that in this case for
			// SQLite, you are connecting to a file on the file
			// system, thus the used of Configuration.project
			String database = "jdbc:sqlite:/" + Configuration.project
			    + "Databases/" + Configuration.type + "/" + 
			    Configuration.database; 

			if (Configuration.debug) {
				System.out.println("JDBC Resource.");
				System.out.println(database);
			}

			// enforce the foreign keys that are specified in the
			// relational schema; this could be turned off if you are
			// debugging and the data generation subsystem is not yet
			// generating data to satisfy the constraints.
			SQLiteConfig config = new SQLiteConfig();
			if (Configuration.foreignkeys) {
			    config.enforceForeignKeys(true);
			    
			    if(!pragmaIssued) {
				ScriptCreator scriptCreator = ScriptCreator.getScriptCreator();
				scriptCreator.configure();
				scriptCreator.print(PRAGMA);
				pragmaIssued = true;
				
				if (Configuration.debug) {
				    System.out.println("Issued the PRAGMA for SQLite");
				    System.out.println(PRAGMA);
				}

			    }				
			}

			// create the connection to the database,
			// using the properties for the SQLiteConfig
			// object to handle referential integrity's on/off switch
			connection = DriverManager.getConnection(database, config.toProperties());

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
	 * Execute a command against a SQLite database. Most useful for commands
	 * that return a code, such as whether or not a command worked or how many
	 * records were changed inside of a table.
	 * So, this is designed for an INSERT, UPDATE, or DELETE.
	 */
	public Integer executeUpdate(String command) {
		// create a List for storing the return counts that
		// tell us how many records were modified in the database
		Integer returnCounts = new Integer(START);

		try {
			// initialize the connection to Postgres only if it 
			// has not already been initialized 
			if( !initialize ) {
			    initializeDatabaseConnection();
			}
			else {
			    if(Configuration.debug) {
				System.out.println("Did not initialize");
			    }
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

		// display information about the error message; note that
		// SQLite can return a code that is not really an error.
		// However, there are others that we will need to later
		// capture and handle during mutation testing. For instance,
		// one of the codes is related to "foreign key constraint
		// failed" and this would show that a mutant was killed
		catch (SQLException e) {
		    // if this command is a create table statement and it through 
		    // an exception, then set the return code to -1 to indicate 
		    // that there was a special failure in creating the schema;
		    // note that this is unlikely to ever happen for SQLite, based
		    // on our experience, but we are including this test anyways
		    if(command.toUpperCase().contains(CREATE_TABLE)) {
			returnCounts=CREATE_TABLE_ERROR;
		    }

		    if(Configuration.debug) {
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Message: " + e.getMessage());
			if (!e.getMessage().equals("not an error"))
				e.printStackTrace();
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
     * Execute a command against a SQLite database. Most useful for
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
	    // initialize the connection to Postgres only if it 
	    // has not already been initialized 
	    if( !initialize ) {
		initializeDatabaseConnection();
	    }
	    else {
		if(Configuration.debug) {
		    System.out.println("Did not initialize");
		}
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
	    // int count = statement.executeUpdate(command);
	    // returnCounts = new Integer(count);
	    
	}

	// display information about the error message; note that
	// SQLite can return a code that is not really an error.
	// However, there are others that we will need to later
	// capture and handle during mutation testing. For instance,
	// one of the codes is related to "foreign key constraint
	// failed" and this would show that a mutant was killed
	catch (SQLException e) {
	    System.out.println("Error Code: " + e.getErrorCode());
	    System.out.println("Message: " + e.getMessage());
	    if (!e.getMessage().equals("not an error"))
		e.printStackTrace();
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