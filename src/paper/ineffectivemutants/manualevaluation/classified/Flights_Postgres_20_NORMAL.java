package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class Flights_Postgres_20_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "Flights";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 20;
	}
	
	protected int getLastMutantNumber() {
	    return 48;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"FlightAvailable\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Flights\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"Flights\"(\"FLIGHT_ID\", \"SEGMENT_NUMBER\", \"MEAL\") VALUES('a', 1, 'B')";
	String statement2 = "INSERT INTO \"FlightAvailable\" VALUES('a', 1, '2000-1-1', 1, 1, 1)";
	String statement3 = "INSERT INTO \"FlightAvailable\" VALUES('a', 1, '2000-1-1', 2, 1, 1)";

	String statement4 = "INSERT INTO \"Flights\"(\"FLIGHT_ID\", \"SEGMENT_NUMBER\", \"MEAL\") VALUES('a', 2, 'B')";
	String statement5 = "INSERT INTO \"FlightAvailable\" VALUES('a', 2, '2000-1-1', 2, 1, 1)";

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
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 9, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(10, statement1, statement4, statement3, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(11, 15, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(16, 18, statement1, statement4, statement3, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(19, 48, statement1, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

