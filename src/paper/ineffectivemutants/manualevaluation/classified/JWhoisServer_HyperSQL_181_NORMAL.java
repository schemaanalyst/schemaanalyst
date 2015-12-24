package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class JWhoisServer_HyperSQL_181_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "JWhoisServer";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 181;
	}
	
	protected int getLastMutantNumber() {
	    return 184;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"country\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"nameserver\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"type\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"person\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"mntnr\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"domain\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"nameserver\" VALUES(1, 'a', 1)";
	String statement2 = "INSERT INTO \"nameserver\" VALUES(2, 'a', 2)";
    String statement3 = "INSERT INTO \"nameserver\" VALUES(1, 'b', 1)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void isRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 88, statement1, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(89, statement1, statement3), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(90, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal/equivalent/redundant/impaired

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

