package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class FrenchTowns_Postgres_13_EQUIVALENT extends ManualAnalysisTestSuite {
	
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
	    return "FrenchTowns";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 13;
	}
	
	protected int getLastMutantNumber() {
	    return 106;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"Towns\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Departments\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Regions\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 =
			"INSERT INTO \"Regions\" VALUES(1, '1', '1', '1')";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void isEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1));
		// is equivalent -- added a PK to a UNIQUE column
	}

	// ENTER END VERDICT (delete as appropriate): equivalent

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

