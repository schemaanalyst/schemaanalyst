package paper.ineffectivemutants.manualevaluation.notimpaired;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class CustomerOrder_SQLite_2 extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:manualanalysis");

		// create the statement
		statement = connection.createStatement();

		// enable FOREIGN KEY support
		statement.executeUpdate("PRAGMA foreign_keys = ON");
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
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 2;
	}
	
	protected int getLastMutantNumber() {
	    return 275;
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

	String statement1 = "INSERT INTO \"db_role\" VALUES('a')";
	String statement2 = "INSERT INTO \"db_user\" VALUES(1, 'a', 'a', 'a', 'a', 1)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement2));
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

