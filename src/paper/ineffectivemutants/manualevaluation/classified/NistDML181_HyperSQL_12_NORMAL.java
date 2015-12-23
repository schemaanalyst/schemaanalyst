package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class NistDML181_HyperSQL_12_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "NistDML181";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 12;
	}
	
	protected int getLastMutantNumber() {
	    return 20;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ORDERS\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"LONG_NAMED_PEOPLE\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"ORDERS\" VALUES(NULL, NULL, 'A', 0)";

	String statement2 = "INSERT INTO \"LONG_NAMED_PEOPLE\" VALUES('a', 'b', 30)";
	String statement3 = "INSERT INTO \"ORDERS\" VALUES('a', 'b', NULL, 1)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement2, statement3));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 4, statement2, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(5, statement1, statement1), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(6, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal/equivalent/redundant/impaired

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

