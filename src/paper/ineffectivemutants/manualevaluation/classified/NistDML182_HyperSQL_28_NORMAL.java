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

public class NistDML182_HyperSQL_28_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "NistDML182";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 28;
	}
	
	protected int getLastMutantNumber() {
	    return 96;
	}

	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ORDERS\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ID_CODES\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statementA = "INSERT INTO \"ID_CODES\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
	String statementA2 = "INSERT INTO \"ID_CODES\" VALUES(1,1,1,1,1,1,1,1,1,1,1,1,0,1,1)";

	String statementB = "INSERT INTO \"ORDERS\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, '', 0)";
	String statementB2 = "INSERT INTO \"ORDERS\" VALUES(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, '', 0)";
	String statementB3 = "INSERT INTO \"ORDERS\" VALUES(1,1,1,1,1,1,1,1,1,1,1,1,0,1,1, 'A', 1)";
	String statementB4 = "INSERT INTO \"ORDERS\" VALUES(1,1,1,1,1,1,1,1,1,1,1,1,NULL,1,1, 'A', 1)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statementA, statementB));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statementA, statementA2, statementB, statementB2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorTo(15, statementA, statementB, statementB2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(16, 91, statementA, statementA2, statementB, statementB3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(92, 92, statementA, statementA2, statementB, statementB4), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(93, statementA, statementA2, statementB, statementB3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

