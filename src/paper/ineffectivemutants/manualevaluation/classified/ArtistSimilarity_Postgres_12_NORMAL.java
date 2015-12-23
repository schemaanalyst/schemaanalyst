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

public class ArtistSimilarity_Postgres_12_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "ArtistSimilarity";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 12;
	}
	
	protected int getLastMutantNumber() {
	    return 12;
	}

    @After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"similarity\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"artists\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"artists\" VALUES('a')";
	String statement4 = "INSERT INTO \"artists\" VALUES('b')";

	String statement2 = "INSERT INTO \"similarity\" VALUES('a', 'a')";
	String statement3 = "INSERT INTO \"similarity\" VALUES('b', 'a')";
	String statement5 = "INSERT INTO \"similarity\" VALUES('a', NULL)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(2, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(3, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(4, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(5, statement1, statement2, statement4, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(6, statement1, statement2, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(7, 10, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(11, 12, statement1, statement2, statement4, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

