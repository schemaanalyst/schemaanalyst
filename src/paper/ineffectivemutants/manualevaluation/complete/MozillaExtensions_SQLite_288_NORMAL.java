package paper.ineffectivemutants.manualevaluation.complete;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MozillaExtensions_SQLite_288_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "MozillaExtensions";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 288;
	}
	
	protected int getLastMutantNumber() {
	    return 360;
	}

    @After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"targetPlatform\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"targetApplication\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"locale_strings\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"locale\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"addon_locale\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"addon\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"addon\" VALUES(1, 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, 1, 1, 'a', 1, 2, 3, 4, 5, 6, 'a', 'a', 1, 2, 3, 4)";
	String statement2 = "INSERT INTO \"addon_locale\" VALUES(1, 'a', 'a')";
	String statement3 = "INSERT INTO \"locale\" VALUES(1, 'a', 'a', 'a', 'a')";
	String statement4 = "INSERT INTO \"locale_strings\" VALUES(1, 'a', 'a')";
	String statement5 = "INSERT INTO \"targetApplication\" VALUES(1, 'a', 'a', 'a')";
	String statement6 = "INSERT INTO \"targetPlatform\" VALUES(1, 'a', 'a')";

    String statement7 = "INSERT INTO \"addon\" VALUES(2, 'a', 'b', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, 1, 1, 'a', 1, 2, 3, 4, 6, 6, 'a', 'a', 1, 2, 3, 4)";
    String statement8 = "INSERT INTO \"addon\" VALUES(2, 'a', 'b', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, 1, 1, 1, 1, 1, 'a', 1, 2, 3, 4, 5, 6, 'a', 'a', 1, 2, 3, 4)";
    String statement9 = "INSERT INTO \"addon\" VALUES(2, 'b', 'b', 'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 2, 2, 2, 2, 2, 2, 'a', 2, 3, 4, 5, 5, 7, 'b', 'b', 2, 3, 4, 5)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2, statement3, statement4, statement5, statement6));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement7));
	}

	@Test
	public void notRedundant() throws SQLException {
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromFirstTo(188, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(189, 190, statement1, statement8), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(191, 211, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(212, 254, statement1, statement9), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(255, 256, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(257, 264, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(265, 266, statement1, statement8), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(267, 295, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(296, 297, statement1, statement8), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(298, 318, statement1, statement7), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFromTo(319, 328, statement1, statement9), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorToLastFrom(329, statement1, statement7), SUCCESS);
    }
	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/
}

