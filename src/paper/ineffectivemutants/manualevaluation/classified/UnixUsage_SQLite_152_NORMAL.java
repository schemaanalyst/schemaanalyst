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

public class UnixUsage_SQLite_152_NORMAL extends ManualAnalysisTestSuite {
	
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
	    return "UnixUsage";
	}
	
	protected String getDBMSName() {
	    return "SQLite";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 152;
	}
	
	protected int getLastMutantNumber() {
	    return 301;
	}

    @After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"USAGE_HISTORY\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"UNIX_COMMAND\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"TRANSCRIPT\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"USER_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"RACE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OFFICE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"COURSE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DEPT_INFO\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 =
			"INSERT INTO \"DEPT_INFO\"(" +
					"    \"DEPT_ID\", \"DEPT_NAME\"" +
					") VALUES (" +
					"    0, ''" +
					")";
	String statement2 =
			"INSERT INTO \"OFFICE_INFO\"(" +
					"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" +
					") VALUES (" +
					"    0, '', 0" +
					")";
	String statement3 =
			"INSERT INTO \"RACE_INFO\"(" +
					"    \"RACE_CODE\", \"RACE\"" +
					") VALUES (" +
					"    0, ''" +
					")";
	String statement4 =
			"INSERT INTO \"USER_INFO\"(" +
					"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" +
					") VALUES (" +
					"    '', '', '', '', 0, 0, 0, 0, '0', 0, '2000-01-01'" +
					")";

	String statement5 =
			"INSERT INTO \"USER_INFO\"(" +
					"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" +
					") VALUES (" +
					"    '', '', '', '', 0, 0, 0, 1, '0', 0, '2000-01-01'" +
					")";

	String statement6 =
			"INSERT INTO \"RACE_INFO\"(" +
					"    \"RACE_CODE\", \"RACE\"" +
					") VALUES (" +
					"    1, ''" +
					")";

	String statement7 =
			"INSERT INTO \"USER_INFO\"(" +
					"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" +
					") VALUES (" +
					"    '', '', '', '', 0, 0, 0, 0, '1', 0, '2000-01-01'" +
					")";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement2, statement3, statement4));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement3, statement5));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(1, 112, statement1, statement2, statement3, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(113, statement1, statement2, statement3, statement7), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(114, 147, statement1, statement2, statement3, statement5), SUCCESS);
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(148, 301, statement1, statement2, statement6, statement7), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

