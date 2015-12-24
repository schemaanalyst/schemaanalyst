package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class FrenchTowns_HyperSQL_9_NORMAL extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:mem:/database;hsqldb.write_delay=false");

		// tell HyperSQL to always persist the data right away
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
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 9;
	}
	
	protected int getLastMutantNumber() {
	    return 98;
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

	String statement1 = "INSERT INTO \"Regions\" VALUES(1, 'b', 'a', 'a' )";
	String statement2 = "INSERT INTO \"Departments\" VALUES(1, 'b', 'b', 'b', 'b')";
	String statement3 = "INSERT INTO \"Towns\" VALUES(1, 'a', 'a', 'b', 'a')";
	String statement4 = "INSERT INTO \"Towns\" VALUES(1, 'c', 'c', 'c', 'c')";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2, statement3));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement3));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(2, statement1, statement2, statement4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(3, statement1, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

