package paper.ineffectivemutants.manualevaluation.notimpaired;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class BookTown_SQLite_3 extends ManualAnalysisTestSuite {
	
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
	    return "BookTown";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 3;
	}
	
	protected int getLastMutantNumber() {
	    return 235;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"schedules\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"book_backup\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"alternate_stock\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"subjects\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"varchar(100)_sorting\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"distinguished_authors\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"editions\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"employees\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"favorite_books\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"stock_backup\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"book_queue\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"customers\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"shipments\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"money_example\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"daily_inventory\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"numeric_values\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"stock\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"my_list\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"states\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"authors\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"publishers\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"books\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"employees\" VALUES(100, 'a', 'a')";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1));
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

