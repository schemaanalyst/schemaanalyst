package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class Examination_Postgres_56_NORMAL extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "gkapfham", "postgres");

		// tell Postgres to always persist the data right away
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
	    return "Examination";
	}
	
	protected String getDBMSName() {
	    return "Postgres";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 56;
	}
	
	protected int getLastMutantNumber() {
	    return 114;
	}
	
	@After
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"Examlog\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Exam\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO \"Examlog\"(\"lkey\", \"examtimeUpdate\") VALUES(1, '2000-1-1')";
	String statement2 = "INSERT INTO \"Examlog\"(\"lkey\", \"examtimeUpdate\") VALUES(1, NULL)";
    String statement3 = "INSERT INTO \"Exam\"(\"ekey\") VALUES(0)";
    String statement4 = "INSERT INTO \"Examlog\" VALUES(0, 0, 0, 'a', 'a', 'a', 'a', 0, 0, 0, 0, 'a', '2000-1-1', '2000-1-1', '2000-1-1')";
    String statement5 = "INSERT INTO \"Examlog\" VALUES(0, 0, 0, 'a', 'a', 'a', 'a', 0, 0, 0, 0, 'a', '2000-1-1', '2000-1-2', '2000-1-1')";
    String statement6 = "INSERT INTO \"Examlog\"(\"lkey\", \"examtimeUpdate\") VALUES(2, '2000-1-1')";


	@Test
	public void notImpaired() throws SQLException {
	    assertTrue(insertToMutant(statement1));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    assertTrue(originalAndMutantHaveDifferentBehavior(statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
	    assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorTo(37, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(38, statement3, statement1), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(39, 43, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(44, 57, statement3, statement4, statement5), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(58, statement3, statement4, statement4), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(59, 70, statement3, statement4, statement5), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(71, 72, statement1, statement6), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(73, 79, statement2), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(80, 91, statement1), SUCCESS);
        assertEquals(mutantAndOtherMutantsHaveDifferentBehaviorFrom(92, statement3, statement4, statement5), SUCCESS);

	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

