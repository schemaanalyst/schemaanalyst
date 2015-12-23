package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class IsoFlav_R2Repaired_SQLite_76_NORMAL extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:manualanalysis");

		// create the statement
		statement = connection.createStatement();

		// enable FOREIGN KEY support
		statement.executeUpdate("PRAGMA foreign_keys = ON");
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
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 76;
	}
	
	protected int getLastMutantNumber() {
	    return 219;
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

	String statement1 = "INSERT INTO \"SYBN_DTL\"(\"AnalMeth_Rtg\") VALUES(NULL)";
	String statement2 = "INSERT INTO \"SYBN_DTL\" VALUES(NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL)";

	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement2));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 66, statement1), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(67, 80, statement2), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(81, statement1), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

