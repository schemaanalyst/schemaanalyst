package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

//import static org.junit.Assert.*;

public class BankAccount_SQLite_24 extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:manualanalysis");

		// create the statement
		statement = connection.createStatement();

		// enable FOREIGN KEY support
		statement.executeUpdate("PRAGMA foreign_keys = ON");
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	protected String getSchemaName() {
	    return "BankAccount";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 24;
	}
	
	protected int getLastMutantNumber() {
	    return 54;
	}
	
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"Account\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"UserInfo\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	// String statement1 = "INSERT INTO " " VALUES( )";
	// String statement2 = "INSERT INTO " " VALUES( )";
	// String statement3 = "INSERT INTO " " VALUES( )";
	// String statement4 = "INSERT INTO " " VALUES( )";
	// String statement5 = "INSERT INTO " " VALUES( )";


	@Test
	public void notImpaired() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(insertToMutant(statement1, ...));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(originalAndMutantHaveDifferentBehavior(statement1, ...));
	}

	@Test
	public void notRedundant() throws SQLException {
	    // ... or maybe it is ...
	    // assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, ...), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

