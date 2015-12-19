package paper.ineffectivemutants.manualevaluation.complete;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class BankAccount_SQLite_24_NORMAL extends ManualAnalysisTestSuite {
	
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

	String statement1 = "INSERT INTO \"UserInfo\" VALUES(1, 1, 'a', 1 )";
	String statement2 = "INSERT INTO \"Account\" VALUES(1, 'a', 'a', 1, 1)";
	String statement3 = "INSERT INTO \"Account\" VALUES(1, 'b', 'a', 1, 1)";
	String statement4 = "INSERT INTO \"Account\" VALUES(2, 'a', 'a', 1, 1)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement3));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(1, 28, statement1, statement2, statement3), SUCCESS);
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(29, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(30, 32, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(33, statement1, statement2, statement4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(34, 54, statement1, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

