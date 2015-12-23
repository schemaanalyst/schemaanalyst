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

public class NistDML182_SQLite_341w262_REDUNDANT extends ManualAnalysisTestSuite {
	
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
	    return 341;
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

	String statementA = "INSERT INTO \"ID_CODES\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
	String statementA2 = "INSERT INTO \"ID_CODES\" VALUES(1,1,1,1,1,1,0,1,1,1,1,1,1,1,1)";

	String statementB = "INSERT INTO \"ORDERS\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, '', 0)";
	String statementB2 = "INSERT INTO \"ORDERS\" VALUES(1,1,1,1,1,1,0,1,1,1,1,1,1,1,1, 'A', 1)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statementA, statementB));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statementA, statementB, statementB));
	}

	@Test
	public void isRedundant() throws SQLException {
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(0, 255, statementA, statementB, statementB), SUCCESS);
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(256, 261, statementA, statementB, statementA2, statementB2), SUCCESS);
		// I can't distinguish this one from 262 (PK on UNIQUE column)
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(263, statementA, statementB, statementA2, statementB2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): redundant with respect to 265

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

