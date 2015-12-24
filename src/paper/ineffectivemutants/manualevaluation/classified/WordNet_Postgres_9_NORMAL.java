package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class WordNet_Postgres_9_NORMAL extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "gkapfham", "postgres");

		// tell Postgres to always persist the data right away
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
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 9;
	}
	
	protected int getLastMutantNumber() {
	    return 107;
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

	String statement1 = "INSERT INTO \"sense\" VALUES(1, 1, 1, 1, 1, 1)";
	String statement2 = "INSERT INTO \"sense\" VALUES(1, 1, 1, 1, 1, 2)";
	String statement3 = "INSERT INTO \"sense\" VALUES(1, 2, 2, 2, 2, 1)";
	String statement4 = "INSERT INTO \"sense\" VALUES(1, 2, 1, 2, 2, 1)";
	String statement5 = "INSERT INTO \"sense\" VALUES(2, 1, 1, 1, 1, 1)";

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
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 38, statement1, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(39, statement1, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(40, 42, statement1, statement4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(43, statement1, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(44, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

