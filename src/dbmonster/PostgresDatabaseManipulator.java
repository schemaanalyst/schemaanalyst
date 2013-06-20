package dbmonster;

import plume.*;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseInteractor;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

import dbmonster.Configuration;

public class PostgresDatabaseManipulator {

    /** No database interaction return code */
    protected static final int START = 0; 
    
    /** The shared connection to the database */
    private Connection connection;

    /** This is the "Postgres" database that stores all database info */
    private static final String POSTGRES_DATABASE = "postgres";

    /** This variable indicates whether or not the database connection has already been initialized */
    private boolean initialize;

    /** 
     *	Constructor.
     */
    public PostgresDatabaseManipulator() {
	connection = null;
	initialize = false;
    }

    /** 
     *	Initialize the connection to the Postgres database.
     */
    public void initializeDatabaseConnection() {
 	connection = null; 
	try {
	    // do not spy using the P6spy interception driver
	    Class.forName("org.postgresql.Driver");

	    if( Configuration.debug ) {
		System.out.println();
		System.out.println("JDBC Driver Registered.");
	    }

	    // note that right now the Postgres database management
	    // system is hosting the "database" in the default
	    // database which is called "postgres" ; we are not
	    // creating a separate database because this is more
	    // complex and does not provide any special features as
	    // long as we name the mutant tables correctly
	    String database = "jdbc:postgresql://" +
		Configuration.host + ":" +
		Configuration.port + "/" +
		POSTGRES_DATABASE;
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

	    if( Configuration.debug ) {
		if ( connection != null ) {
		    System.out.println("JDBC Connection Okay.");
		}
		else {
		    System.out.println("Connection is Null.");
		}
	    }

	    // show that we have already initialized the database
	    initialize = true;
	}

	catch( Exception e ) 
	    { e.printStackTrace(); }
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
    public ResultSet executeQuery(String command) {
	ResultSet result = null;
	try {
	    // initialize the connection to Postgres only if it 
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
	    result = statement.executeQuery(command);
	}

	// display information about the error message
	catch (SQLException e) {
	    System.out.println("Error Code: " + e.getErrorCode());
	    System.out.println("Message: " + e.getMessage());
	    if (!e.getMessage().equals("not an error"))
		e.printStackTrace();
	}

	// do not close this connect any more, it will cause errors
	finally {
	    try {
		//connection.close();
	    } catch (Exception e) {
	    }
	}
	return result;
    }

    /** Drop all of the tables inside of the database by asking Postgres to create the needed
	DROP TABLE statements and then run each DROP TABLE separately. */
    public void dropAllTables() {
	if(Configuration.debug) {
	    System.out.println("Dropping all of the tables.");
	}

	try {
	    // extract the tables that need to be dropped from the database, excluding
	    // those that are related to the internals of the Postgres system
	    String dropAllCommandExtractor = "select 'drop table \"\' || tablename || \'\" cascade;' from pg_tables where schemaname = \'public\';";
	    ResultSet dropAllCommands = executeQuery(dropAllCommandExtractor);
	
	    // iteratate through all of the commands and execute the separately
	    while(dropAllCommands.next()) {
		String dropCommand = dropAllCommands.getString(1);
	    
		if(Configuration.debug) {
		    System.out.println("Drop Command: " + dropCommand);
		}

		// run this dropCommand against the real database
		executeUpdate(dropCommand);
		
	    }	
	}

	catch(SQLException e) {
	    System.out.println("Database error when dropping all tables");
	    e.printStackTrace();
	}
    }

    /** Add the specified tables to the database */
    public void addSpecifiedTables() {
	// create the database using reflection; this is based on the
	// type of the database provided in the configuration (i.e.,
	// the user could request the Postres database in FQN)
	Database database = constructDatabase(Configuration.type);
	SQLWriter sqlWriter = database.getSQLWriter();

	// create the schema using reflection; this is based on the
	// name of the database provided in the configuration
	Schema schema = constructSchema(Configuration.database);

	// drop tables inside the schema
	List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
	for (String statement : dropTableStatements) {
	    executeUpdate(statement);
	}

	// create the schema inside of the real database
	List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
	for (String statement : createTableStatements) {
	    int returnCount = executeUpdate(statement);
	}
    }

    /** Add the specified tables to the database */
    public void dropSpecifiedTables() {
	// create the database using reflection; this is based on the
	// type of the database provided in the configuration (i.e.,
	// the user could request the Postres database in FQN)
	Database database = constructDatabase(Configuration.type);
	SQLWriter sqlWriter = database.getSQLWriter();

	// create the schema using reflection; this is based on the
	// name of the database provided in the configuration
	Schema schema = constructSchema(Configuration.database);

	// drop tables inside the schema
	List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
	for (String statement : dropTableStatements) {
	    executeUpdate(statement);
	}
    }

    /**
     * Execute a command against a Postgres database. Most useful for
     * commands that return a code, such as whether or not a command
     * worked or how many records were changed inside of a table. 
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

	// display information about the error message
	catch (SQLException e) {
	    System.out.println("Error Code: " + e.getErrorCode());
	    System.out.println("Message: " + e.getMessage());
	    if (!e.getMessage().equals("not an error"))
		e.printStackTrace();
	}

	// do not close this connect any more, it will cause errors
	finally {
	    try {
		//connection.close();
	    } catch (Exception e) {
	    }
	}

	return returnCounts;

    }

    /**
     * Create the Database instanced based on the name provided.
     */
    public static Database constructDatabase(String databaseType) {
	try {
	    return (Database) Class.forName(databaseType).newInstance();
	} catch (Exception e) {
	    throw new RuntimeException("Could not construct database type \"" + databaseType +"\"");
	}
    }

    /**
     * Create the Schema instance based on the name provided.
     */
    public static Schema constructSchema(String schemaName) {
	try {
	    return (Schema) Class.forName(schemaName).newInstance();
	} catch (Exception e) {
	    throw new RuntimeException("Could not construct schema \"" + schemaName +"\"");
	}		
    }

    /** Run the database manipulator that helps when running DBMonster */
    public static void main(String[] args) {
	// extract all of the database configurations
	Configuration configuration = new Configuration();
	Options options = new Options("PostgresDatabaseManipulator [options]", configuration);
	options.parse_or_usage(args);
	
	// print the debugging information
	if (Configuration.debug) {
	    System.out.println(options.settings());
	}
	
	// print the help screen to see the commands
	if (Configuration.help) {
	    options.print_usage();
	}

	PostgresDatabaseManipulator manipulator = new PostgresDatabaseManipulator();

	// determine the action that needs to be performed
	if (Configuration.action.equals("dropalltables")) {
	    manipulator.dropAllTables();
	}
	else if (Configuration.action.equals("addtables")) {
	    manipulator.addSpecifiedTables();
	}	
	else if (Configuration.action.equals("droptables")) {
	    manipulator.dropSpecifiedTables();
	}	
	else {
	    System.out.println("Action not recognized");
	}
    }
}