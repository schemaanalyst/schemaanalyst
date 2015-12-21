package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class Usda_Postgres_81 extends ManualAnalysisTestSuite {
	
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
	    return "Usda";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 81;
	}
	
	protected int getLastMutantNumber() {
	    return 201;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"weight\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"src_cd\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"nutr_def\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"nut_data\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"footnote\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"food_des\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"fd_group\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"deriv_cd\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"datsrcln\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"data_src\"");
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

	// ENTER END VERDICT (delete as appropriate): normal/equivalent/redundant/impaired

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

