package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class IsoFlav_R2Repaired_HyperSQL_85_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "IsoFlav_R2";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 85;
	}
	
	protected int getLastMutantNumber() {
	    return 216;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"SYBN_DTL\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"NUTR_DEF\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ISFL_DAT\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"FOOD_DES\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DATSRCLN\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DATA_SRC\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"DATSRCLN\" VALUES('a', 'a', 1)";
	String statement2 = "INSERT INTO \"DATSRCLN\" VALUES(NULL, NULL, NULL)";
	String statement3 = "INSERT INTO \"DATSRCLN\" VALUES('b', 'a', 1)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement1));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 7, statement1, statement1), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(8, 10, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(11, 85, statement1, statement1), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(86, 87, statement1, statement3), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(88, statement1, statement1), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

