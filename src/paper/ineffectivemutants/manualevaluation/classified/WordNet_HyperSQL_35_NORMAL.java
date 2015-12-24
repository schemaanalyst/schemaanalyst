package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class WordNet_HyperSQL_35_NORMAL extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:mem:/database;hsqldb.write_delay=false");

		// tell HyperSQL to always persist the data right away
		connection.setAutoCommit(true);
		// create the statement
		statement = connection.createStatement();
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	protected String getSchemaName() {
	    return "WordNet";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 35;
	}
	
	protected int getLastMutantNumber() {
	    return 101;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"word\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"synset\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"sense\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"semlinkref\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"sample\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"linkdef\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"lexlinkref\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"categorydef\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"sample\" VALUES(1, 1, '1')";
	String statement2 = "INSERT INTO \"sample\" VALUES(1, 1, '2')";
    String statement3 = "INSERT INTO \"sample\" VALUES(1, 2, '1')";
    String statement4 = "INSERT INTO \"sample\" VALUES(2, 1, '1')";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 4, statement1, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(5, statement1, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(6, 33, statement1, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(34, statement1, statement4), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(36, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

