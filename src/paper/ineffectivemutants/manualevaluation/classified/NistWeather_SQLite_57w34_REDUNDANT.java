package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class NistWeather_SQLite_57w34_REDUNDANT extends ManualAnalysisTestSuite {
	
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
	    return "NistWeather";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 57;
	}
	
	protected int getLastMutantNumber() {
	    return 60;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"Stats\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Station\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

    String statement1 = "INSERT INTO \"Station\" VALUES(1, 'a', 'a', 0, 0)";
	String statement2 = "INSERT INTO \"Stats\" VALUES(1, 1, 80, 0)";
	String statement3 = "INSERT INTO \"Stats\" VALUES(1, 2, 80, 0)";
    String statement4 = "INSERT INTO \"Station\" VALUES(2, 'a', 'a', 0, 0)";
    String statement5 = "INSERT INTO \"Stats\" VALUES(1, 1, 90, 1)";
    String statement6 = "INSERT INTO \"Stats\" VALUES(2, 1, 80, 0)";
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
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 22, statement1, statement2, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(23, statement1, statement2, statement4, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(24, 33, statement1, statement2, statement3), SUCCESS);
        // cannot distinguish from 34
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(35, 40, statement1, statement2, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(41, 42, statement1, statement2, statement5), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(43, 58, statement1, statement2, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(59, statement1, statement2, statement4, statement6), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): redundant

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

