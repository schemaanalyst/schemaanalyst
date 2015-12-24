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

public class NistDML182_Postgres_50_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "NistDML182";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 50;
	}
	
	protected int getLastMutantNumber() {
	    return 81;
	}

	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ORDERS\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ID_CODES\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"ID_CODES\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
	String statement2 = "INSERT INTO \"ID_CODES\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)";
	String statement3 = "INSERT INTO \"ID_CODES\" VALUES(1,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
	String statement4 = "INSERT INTO \"ORDERS\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement4));
	}

	@Test
	public void notEquivalent() throws SQLException {
		assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 50, statement1, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(51, 64, statement1, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(65, 81, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

