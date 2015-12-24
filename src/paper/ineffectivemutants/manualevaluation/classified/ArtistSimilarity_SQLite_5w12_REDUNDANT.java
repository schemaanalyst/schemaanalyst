package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ArtistSimilarity_SQLite_5w12_REDUNDANT extends ManualAnalysisTestSuite {
	
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
	    return "ArtistSimilarity";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 5;
	}
	
	protected int getLastMutantNumber() {
	    return 13;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"similarity\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"artists\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"artists\" VALUES('1')";
	String statement2 = "INSERT INTO \"similarity\" VALUES('1', NULL)";
    String statement3 = "INSERT INTO \"similarity\" VALUES(NULL, NULL)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement2));
	}

	@Test
	public void isRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 11, statement1, statement2, statement2), SUCCESS);
        // can't distinguish from 12 (add UNIQUE a column with a PK on it)
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(13, statement1, statement2, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): redundant

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

