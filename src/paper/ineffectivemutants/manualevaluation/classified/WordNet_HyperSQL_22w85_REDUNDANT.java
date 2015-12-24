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

public class WordNet_HyperSQL_22w85_REDUNDANT extends ManualAnalysisTestSuite {
	
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
	    return 22;
	}
	
	protected int getLastMutantNumber() {
	    return 101;
	}

	@After
	public  void dropTables() throws SQLException {
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

	String statement1 = "INSERT INTO \"sample\" VALUES(1, 1, 'a')";
	String statement2 = "INSERT INTO \"sample\" VALUES(1, 2, 'a')";
	String statement3 = "INSERT INTO \"sample\" VALUES(1, 1, 'b')";
	String statement4 = "INSERT INTO \"sample\" VALUES(1, 2, 'b')";
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
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(35, statement1, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(36, 84, statement1, statement2), SUCCESS);
		// I can't DISTINGUISH 22 from 85
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(86, statement1, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(87, statement1, statement4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(88, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): redundant

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

