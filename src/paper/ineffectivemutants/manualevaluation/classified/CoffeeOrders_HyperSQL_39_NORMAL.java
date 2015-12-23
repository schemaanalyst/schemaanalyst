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

public class CoffeeOrders_HyperSQL_39_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "CoffeeOrders";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 39;
	}
	
	protected int getLastMutantNumber() {
	    return 56;
	}

    @After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"order_items\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"orders\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"customers\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"salespeople\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"coffees\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"coffees\" VALUES(1, 'A', 1)";
	String statement2 = "INSERT INTO \"salespeople\" VALUES(1, 'A', 'A', 1)";
    String statement3 = "INSERT INTO \"customers\" VALUES(1, 'A', 'A', NULL, 'A', 'A')";
    String statement4 = "INSERT INTO \"orders\" VALUES(1, 1, 1)";
    String statement5 = "INSERT INTO \"order_items\" VALUES(1, 1, 1, 1)";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement2, statement3, statement4, statement5));
	}

	@Test
	public void notEquivalent() throws SQLException {
        assertTrue(originalAndMutantHaveDifferentBehavior(statement3));
	}

	@Test
	public void notRedundant() throws SQLException {
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

