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

public class MozillaPermissions_HyperSQL_22_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "MozillaPermissions";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 22;
	}
	
	protected int getLastMutantNumber() {
	    return 30;
	}

	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"moz_hosts\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"moz_hosts\" VALUES(1, 'a', 'a', 1, 1, 1, 1, 1)";
	String statement2 = "INSERT INTO \"moz_hosts\" VALUES(1, 'a', 'a', 1, 1, 1, NULL, 1)";
	String statement3 = "INSERT INTO \"moz_hosts\" VALUES(1, 'a', 'a', 1, 1, 1, 2, 1)";
	String statement4 = "INSERT INTO \"moz_hosts\" VALUES(2, 'a', 'a', 1, 1, 1, 1, 1)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
		assertTrue(originalAndMutantHaveDifferentBehavior(statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 5, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(6, statement1, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(7, 13, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(14, statement1, statement4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(15, 30, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

