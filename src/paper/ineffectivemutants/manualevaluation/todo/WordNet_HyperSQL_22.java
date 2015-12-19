package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class WordNet_HyperSQL_22 extends ManualAnalysisTestSuite {
	
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
	    return "WordNet";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 22;
	}
	
	protected int getLastMutantNumber() {
	    return 101;
	}
	
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"word\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"synset\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"sense\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"semlinkref\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"sample\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"linkdef\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"lexlinkref\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"categorydef\"");
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

