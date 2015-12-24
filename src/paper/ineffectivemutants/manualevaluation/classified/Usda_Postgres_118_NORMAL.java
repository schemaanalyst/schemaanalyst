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

public class Usda_Postgres_118_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return 118;
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

	String statement1 = "INSERT INTO \"data_src\" VALUES('id', 'author', 'title', 2000, 'journal', 'city', 'state', '1', '100')";
	String statement2 = "INSERT INTO \"datsrcln\" VALUES('no', 'no', 'id')";
	String statement3 = "INSERT INTO \"deriv_cd\" VALUES('a', 'a')";
	String statement4 = "INSERT INTO \"fd_group\" VALUES('a', 'a')";
	String statement5 = "INSERT INTO \"food_des\" VALUES('a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, 'a', 1, 1, 1, 1)";
	String statement6 = "INSERT INTO \"footnote\" VALUES('a', NULL, 'a', 'a', 'a')";
	String statement7 = "INSERT INTO \"nut_data\" VALUES('a', 'a', 1, 1, 1, 1, 'a', 'a', 'a', 1, 1, 1, 1, 1, 1, 'a', 'a')";
	String statement8 = "INSERT INTO \"nutr_def\" VALUES('a', 'a', 'a', 'a', 1, 1)";
	String statement9 = "INSERT INTO \"src_cd\" VALUES(1, 'a')";
	String statement10 = "INSERT INTO \"weight\" VALUES('a', 'a', 1, 'a', 1, 1, 1)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement2, statement3, statement4, statement5, statement6, statement7, statement8, statement9, statement10));
	}

	@Test
	public void notEquivalent() throws SQLException {
		assertTrue(originalAndMutantHaveDifferentBehavior(statement6));
	}

	@Test
	public void notRedundant() throws SQLException {
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement6), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

