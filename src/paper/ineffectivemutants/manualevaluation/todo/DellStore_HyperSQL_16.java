package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

//import static org.junit.Assert.*;

public class DellStore_HyperSQL_16 extends ManualAnalysisTestSuite {
	
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
	    return "DellStore";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 16;
	}
	
	protected int getLastMutantNumber() {
	    return 156;
	}
	
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"reorder\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"products\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"orders\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"orderlines\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"inventory\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"customers\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"cust_hist\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"categories\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	// String statement1 = "INSERT INTO " " VALUES( )";
	// String statement2 = "INSERT INTO " " VALUES( )";
	// String statement3 = "INSERT INTO " " VALUES( )";
	// String statement4 = "INSERT INTO " " VALUES( )";
	// String statement5 = "INSERT INTO " " VALUES( )";


	@Test
	public void notImpaired() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(insertToMutant(statement1, ...));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(originalAndMutantHaveDifferentBehavior(statement1, ...));
	}

	@Test
	public void notRedundant() throws SQLException {
	    // ... or maybe it is ...
	    // assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, ...), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

