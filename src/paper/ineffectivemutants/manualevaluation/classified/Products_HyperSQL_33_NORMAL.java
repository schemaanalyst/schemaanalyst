package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class Products_HyperSQL_33_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "Products";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 33;
	}
	
	protected int getLastMutantNumber() {
	    return 51;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"order_items\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"orders\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"products\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"products\" VALUES(1, 'a', 2, 1)";
	String statement2 = "INSERT INTO \"order_items\" VALUES(1, NULL, 1)";

    String statement3 = "INSERT INTO \"products\" VALUES(2, 'a', 2, 1)";
    String statement4 = "INSERT INTO \"order_items\" VALUES(2, NULL, 1)";
    String statement5 = "INSERT INTO \"order_items\" VALUES(2, NULL, 2)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1, statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 34, statement1, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(35, statement3, statement4, statement5), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(36, 51, statement1, statement2), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

