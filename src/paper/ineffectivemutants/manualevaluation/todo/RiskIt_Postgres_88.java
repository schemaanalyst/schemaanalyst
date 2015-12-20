package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RiskIt_Postgres_88 extends ManualAnalysisTestSuite {
	
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
	    return "RiskIt";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 88;
	}
	
	protected int getLastMutantNumber() {
	    return 209;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ziptable\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"youth\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"wage\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"stateabbv\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"migration\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"job\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"occupation\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"investment\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"industry\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"geo\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"employmentstat\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"education\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"userrecord\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"\" VALUES( )";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

