package paper.ineffectivemutants.manualevaluation.complete;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class StudentResidence_SQLite_47_REDUNDANT_35 extends ManualAnalysisTestSuite {
	
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
	    return "StudentResidence";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 47;
	}
	
	protected int getLastMutantNumber() {
	    return 48;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"Student\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Residence\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"Student\" VALUES(1, 'a', 'a', NULL)";
	String statement2 = "INSERT INTO \"Student\" VALUES(2, 'b', 'a', NULL)";
	String statement3 = "INSERT INTO \"Student\" VALUES(1, 'b', NULL, NULL)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void isRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 34, statement1, statement2), SUCCESS);
		// redundant with respect to 35
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(36, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): redundant

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

