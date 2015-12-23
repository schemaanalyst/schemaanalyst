package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class NistDML183_HyperSQL_19_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "NistDML183";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 19;
	}
	
	protected int getLastMutantNumber() {
	    return 20;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"S\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"T\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"T\" VALUES('1', '1', '1')";
	String statement2 = "INSERT INTO \"S\" VALUES('1', '1', '1')";
	String statement3 = "INSERT INTO \"S\" VALUES('1', NULL, '1')";
	String statement4 = "INSERT INTO \"T\" VALUES('2', '1', '2')";
	String statement5 = "INSERT INTO \"S\" VALUES('2', '1', '2')";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 5, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(6, 8, statement1, statement2, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(9, 17, statement1, statement2, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(18, statement1, statement2, statement4, statement5), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

