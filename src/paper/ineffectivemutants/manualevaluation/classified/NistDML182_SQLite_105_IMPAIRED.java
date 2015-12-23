package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class NistDML182_SQLite_105_IMPAIRED extends ManualAnalysisTestSuite {
	
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
	    return "NistDML182";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 105;
	}
	
	protected int getLastMutantNumber() {
	    return 351;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ORDERS\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ID_CODES\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"ID_CODES\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
	String statement2 = "INSERT INTO \"ORDERS\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, '', 0)";

	@Test
	public void notImpaired() throws SQLException {
		assertFalse(insertToMutant(statement1, statement2));
	}

	// ENTER END VERDICT (delete as appropriate): impaired

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/
}

