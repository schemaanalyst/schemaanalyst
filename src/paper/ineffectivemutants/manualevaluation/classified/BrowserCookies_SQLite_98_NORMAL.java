package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BrowserCookies_SQLite_98_NORMAL extends ManualAnalysisTestSuite {

	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:BrowserCookies");

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
		return "BrowserCookies";
	}

	protected String getDBMSName() {
		return "SQLite";
	}

	protected int getMutantNumberBeingEvaluated() {
		return 98;
	}

	protected int getLastMutantNumber() {
		return 132;
	}

	@After
	protected void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"cookies\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"places\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO places VALUES('A', 'B', 'C', 1, 'D')";
	String statement2 = "INSERT INTO places VALUES('a', 'B', 'c', 2, 'd')";
	String statement3 = "INSERT INTO cookies VALUES(1, 'x', 'y', 0, 1, 1, 'A', 'B')";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement3));
	}

	@Test
	public void notEquivalent() throws SQLException {
		assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/
}

