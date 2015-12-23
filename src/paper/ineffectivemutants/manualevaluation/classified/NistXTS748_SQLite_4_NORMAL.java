package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class NistXTS748_SQLite_4_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "NistXTS748";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 4;
	}
	
	protected int getLastMutantNumber() {
	    return 19;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"TEST12549\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"TEST12549\" VALUES(1, 1, -10)";
	String statement2 = "INSERT INTO \"TEST12549\" VALUES(1, 1, 10)";
	String statement3 = "INSERT INTO \"TEST12549\" VALUES(1, 1, 0)";
	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1));
	}

	@Test
	public void notRedundant() throws SQLException {
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, statement2), SUCCESS);
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(2, statement1), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(3, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(6, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(7, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal/equivalent/redundant/impaired

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

