package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerOrder_Postgres_40_EQUIVALENT extends ManualAnalysisTestSuite {
	
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
	    return "CustomerOrder";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 40;
	}
	
	protected int getLastMutantNumber() {
	    return 96;
	}

	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_order_item\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_order\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_customer\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_user\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_role\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_product\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"db_category\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"db_category\" VALUES('1', '1', '1')";
	String statement2 = "INSERT INTO \"db_product\" VALUES('1', '1', '1', 0, '1', '1', '1')";
	String statement3 = "INSERT INTO \"db_product\" VALUES(NULL, '1', '1', 0, '1', '1', '1')";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void isEquivalent() throws SQLException {
		assertFalse(originalAndMutantHaveDifferentBehavior(statement1, statement2));
		assertFalse(originalAndMutantHaveDifferentBehavior(statement1, statement3));
	}

	// ENTER END VERDICT (delete as appropriate): equivalent

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

