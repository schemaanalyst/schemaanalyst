package generatedtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestUnixUsage {
	
	private static final String JDBC_CLASS = "org.sqlite.JDBC";
	private static final String CONNECTION_URL = "jdbc:sqlite:UnixUsage";

	private static Connection connection;
	private static Statement statement;
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName(JDBC_CLASS);
		connection = DriverManager.getConnection(CONNECTION_URL);
		statement = connection.createStatement();

		// drop the tables for this database (if they exist)
		statement.executeUpdate("DROP TABLE IF EXISTS \"USAGE_HISTORY\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"UNIX_COMMAND\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"TRANSCRIPT\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"USER_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"RACE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OFFICE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"COURSE_INFO\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DEPT_INFO\"");

		// create the tables for this database 
		statement.executeUpdate(
			"CREATE TABLE \"DEPT_INFO\" (" + 
			"    \"DEPT_ID\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"DEPT_NAME\"    VARCHAR(50)" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"COURSE_INFO\" (" + 
			"    \"COURSE_ID\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"COURSE_NAME\"    VARCHAR(50)," + 
			"    \"OFFERED_DEPT\"    INT     REFERENCES \"DEPT_INFO\" (\"DEPT_ID\")," + 
			"    \"GRADUATE_LEVEL\"    SMALLINT" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"OFFICE_INFO\" (" + 
			"    \"OFFICE_ID\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"OFFICE_NAME\"    VARCHAR(50)," + 
			"    \"HAS_PRINTER\"    SMALLINT" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"RACE_INFO\" (" + 
			"    \"RACE_CODE\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"RACE\"    VARCHAR(50)" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"USER_INFO\" (" + 
			"    \"USER_ID\"    VARCHAR(50)    PRIMARY KEY    NOT NULL," + 
			"    \"FIRST_NAME\"    VARCHAR(50)," + 
			"    \"LAST_NAME\"    VARCHAR(50)," + 
			"    \"SEX\"    VARCHAR(1)," + 
			"    \"DEPT_ID\"    INT     REFERENCES \"DEPT_INFO\" (\"DEPT_ID\")," + 
			"    \"OFFICE_ID\"    INT     REFERENCES \"OFFICE_INFO\" (\"OFFICE_ID\")," + 
			"    \"GRADUATE\"    SMALLINT," + 
			"    \"RACE\"    INT     REFERENCES \"RACE_INFO\" (\"RACE_CODE\")," + 
			"    \"PASSWORD\"    VARCHAR(50)    NOT NULL," + 
			"    \"YEARS_USING_UNIX\"    INT," + 
			"    \"ENROLL_DATE\"    DATE" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"TRANSCRIPT\" (" + 
			"    \"USER_ID\"    VARCHAR(50)     REFERENCES \"USER_INFO\" (\"USER_ID\")    NOT NULL," + 
			"    \"COURSE_ID\"    INT     REFERENCES \"COURSE_INFO\" (\"COURSE_ID\")    NOT NULL," + 
			"    \"SCORE\"    INT," + 
			"    PRIMARY KEY (\"USER_ID\", \"COURSE_ID\")" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"UNIX_COMMAND\" (" + 
			"    \"UNIX_COMMAND\"    VARCHAR(50)    PRIMARY KEY    NOT NULL," + 
			"    \"CATEGORY\"    VARCHAR(50)" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"USAGE_HISTORY\" (" + 
			"    \"USER_ID\"    VARCHAR(50)     REFERENCES \"USER_INFO\" (\"USER_ID\")    NOT NULL," + 
			"    \"SESSION_ID\"    INT," + 
			"    \"LINE_NO\"    INT," + 
			"    \"COMMAND_SEQ\"    INT," + 
			"    \"COMMAND\"    VARCHAR(50)" + 
			")");
	}
	
	@Before
	public void clearTables() throws SQLException {
		statement.executeUpdate("DELETE FROM \"USAGE_HISTORY\"");
		statement.executeUpdate("DELETE FROM \"UNIX_COMMAND\"");
		statement.executeUpdate("DELETE FROM \"TRANSCRIPT\"");
		statement.executeUpdate("DELETE FROM \"USER_INFO\"");
		statement.executeUpdate("DELETE FROM \"RACE_INFO\"");
		statement.executeUpdate("DELETE FROM \"OFFICE_INFO\"");
		statement.executeUpdate("DELETE FROM \"COURSE_INFO\"");
		statement.executeUpdate("DELETE FROM \"DEPT_INFO\"");
	}
	
	@Test
	public void test0() throws SQLException {
		// Test all equal except USER_ID for USAGE_HISTORY's FOREIGN KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USAGE_HISTORY\"(" + 
				"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
				") VALUES (" + 
				"    'a', 0, 0, 0, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test1() throws SQLException {
		// Test all columns equal for USAGE_HISTORY's FOREIGN KEY[USER_ID]
		// Test USER_ID is NOT NULL for USAGE_HISTORY's NOT NULL(USER_ID)
		// Test USER_ID is NOT NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test2() throws SQLException {
		// Test USER_ID is NULL for USAGE_HISTORY's FOREIGN KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USAGE_HISTORY\"(" + 
				"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
				") VALUES (" + 
				"    NULL, 0, 0, 0, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test3() throws SQLException {
		// Test USER_ID is NULL for USAGE_HISTORY's NOT NULL(USER_ID)
		// Test USER_ID is NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USAGE_HISTORY\"(" + 
				"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
				") VALUES (" + 
				"    NULL, 0, 0, 0, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test4() throws SQLException {
		// Test SESSION_ID is NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', NULL, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test5() throws SQLException {
		// Test SESSION_ID is NOT NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test6() throws SQLException {
		// Test LINE_NO is NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, NULL, 0, ''" + 
			")"));
	}
	
	@Test
	public void test7() throws SQLException {
		// Test LINE_NO is NOT NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test8() throws SQLException {
		// Test COMMAND_SEQ is NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, NULL, ''" + 
			")"));
	}
	
	@Test
	public void test9() throws SQLException {
		// Test COMMAND_SEQ is NOT NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test10() throws SQLException {
		// Test COMMAND is NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, NULL" + 
			")"));
	}
	
	@Test
	public void test11() throws SQLException {
		// Test COMMAND is NOT NULL for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test12() throws SQLException {
		// Test USER_ID is UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    'a', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test13() throws SQLException {
		// Test USER_ID is NOT UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test14() throws SQLException {
		// Test SESSION_ID is UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 1, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test15() throws SQLException {
		// Test SESSION_ID is NOT UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test16() throws SQLException {
		// Test LINE_NO is UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 1, 0, ''" + 
			")"));
	}
	
	@Test
	public void test17() throws SQLException {
		// Test LINE_NO is NOT UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test18() throws SQLException {
		// Test COMMAND_SEQ is UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 1, ''" + 
			")"));
	}
	
	@Test
	public void test19() throws SQLException {
		// Test COMMAND_SEQ is NOT UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test20() throws SQLException {
		// Test COMMAND is UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, 'a'" + 
			")"));
	}
	
	@Test
	public void test21() throws SQLException {
		// Test COMMAND is NOT UNIQUE for USAGE_HISTORY

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USAGE_HISTORY\"(" + 
			"    \"USER_ID\", \"SESSION_ID\", \"LINE_NO\", \"COMMAND_SEQ\", \"COMMAND\"" + 
			") VALUES (" + 
			"    '', 0, 0, 0, ''" + 
			")"));
	}
	
	@Test
	public void test22() throws SQLException {
		// Test all equal except UNIX_COMMAND for UNIX_COMMAND's PRIMARY KEY[UNIX_COMMAND]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    'a', ''" + 
			")"));
	}
	
	@Test
	public void test23() throws SQLException {
		// Test all columns equal for UNIX_COMMAND's PRIMARY KEY[UNIX_COMMAND]
		// Test UNIX_COMMAND is NOT UNIQUE for UNIX_COMMAND

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UNIX_COMMAND\"(" + 
				"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
				") VALUES (" + 
				"    '', ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test24() throws SQLException {
		// Test UNIX_COMMAND is NULL for UNIX_COMMAND's PRIMARY KEY[UNIX_COMMAND]

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UNIX_COMMAND\"(" + 
				"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test25() throws SQLException {
		// Test UNIX_COMMAND is NOT NULL for UNIX_COMMAND's NOT NULL(UNIX_COMMAND)
		// Test UNIX_COMMAND is NOT NULL for UNIX_COMMAND
		// Test UNIX_COMMAND is UNIQUE for UNIX_COMMAND

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));
	}
	
	@Test
	public void test26() throws SQLException {
		// Test UNIX_COMMAND is NULL for UNIX_COMMAND's NOT NULL(UNIX_COMMAND)
		// Test UNIX_COMMAND is NULL for UNIX_COMMAND

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UNIX_COMMAND\"(" + 
				"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test27() throws SQLException {
		// Test CATEGORY is NULL for UNIX_COMMAND

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', NULL" + 
			")"));
	}
	
	@Test
	public void test28() throws SQLException {
		// Test CATEGORY is NOT NULL for UNIX_COMMAND

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));
	}
	
	@Test
	public void test29() throws SQLException {
		// Test CATEGORY is UNIQUE for UNIX_COMMAND

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    'a', 'a'" + 
			")"));
	}
	
	@Test
	public void test30() throws SQLException {
		// Test CATEGORY is NOT UNIQUE for UNIX_COMMAND

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    '', ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UNIX_COMMAND\"(" + 
			"    \"UNIX_COMMAND\", \"CATEGORY\"" + 
			") VALUES (" + 
			"    'a', ''" + 
			")"));
	}
	
	@Test
	public void test31() throws SQLException {
		// Test all equal except USER_ID for TRANSCRIPT's PRIMARY KEY[USER_ID, COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    'a', 0, 0" + 
			")"));
	}
	
	@Test
	public void test32() throws SQLException {
		// Test all equal except COURSE_ID for TRANSCRIPT's PRIMARY KEY[USER_ID, COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 1, 0" + 
			")"));
	}
	
	@Test
	public void test33() throws SQLException {
		// Test all columns equal for TRANSCRIPT's PRIMARY KEY[USER_ID, COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test34() throws SQLException {
		// Test USER_ID is NULL for TRANSCRIPT's PRIMARY KEY[USER_ID, COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    NULL, 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test35() throws SQLException {
		// Test COURSE_ID is NULL for TRANSCRIPT's PRIMARY KEY[USER_ID, COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    '', NULL, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test36() throws SQLException {
		// Test all equal except USER_ID for TRANSCRIPT's FOREIGN KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    'a', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test37() throws SQLException {
		// Test all columns equal for TRANSCRIPT's FOREIGN KEY[USER_ID]
		// Test all columns equal for TRANSCRIPT's FOREIGN KEY[COURSE_ID]
		// Test USER_ID is NOT NULL for TRANSCRIPT's NOT NULL(USER_ID)
		// Test COURSE_ID is NOT NULL for TRANSCRIPT's NOT NULL(COURSE_ID)
		// Test USER_ID is NOT NULL for TRANSCRIPT
		// Test COURSE_ID is NOT NULL for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test38() throws SQLException {
		// Test USER_ID is NULL for TRANSCRIPT's FOREIGN KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    NULL, 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test39() throws SQLException {
		// Test all equal except COURSE_ID for TRANSCRIPT's FOREIGN KEY[COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    '', 1, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test40() throws SQLException {
		// Test COURSE_ID is NULL for TRANSCRIPT's FOREIGN KEY[COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    '', NULL, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test41() throws SQLException {
		// Test USER_ID is NULL for TRANSCRIPT's NOT NULL(USER_ID)
		// Test USER_ID is NULL for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    NULL, 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test42() throws SQLException {
		// Test COURSE_ID is NULL for TRANSCRIPT's NOT NULL(COURSE_ID)
		// Test COURSE_ID is NULL for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"TRANSCRIPT\"(" + 
				"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
				") VALUES (" + 
				"    '', NULL, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test43() throws SQLException {
		// Test SCORE is NULL for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, NULL" + 
			")"));
	}
	
	@Test
	public void test44() throws SQLException {
		// Test SCORE is NOT NULL for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test45() throws SQLException {
		// Test USER_ID is UNIQUE for TRANSCRIPT
		// Test COURSE_ID is UNIQUE for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    'a', 1, 0" + 
			")"));
	}
	
	@Test
	public void test46() throws SQLException {
		// Test USER_ID is NOT UNIQUE for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 1, 0" + 
			")"));
	}
	
	@Test
	public void test47() throws SQLException {
		// Test COURSE_ID is NOT UNIQUE for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    'a', 0, 0" + 
			")"));
	}
	
	@Test
	public void test48() throws SQLException {
		// Test SCORE is UNIQUE for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    'a', 0, 1" + 
			")"));
	}
	
	@Test
	public void test49() throws SQLException {
		// Test SCORE is NOT UNIQUE for TRANSCRIPT

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"TRANSCRIPT\"(" + 
			"    \"USER_ID\", \"COURSE_ID\", \"SCORE\"" + 
			") VALUES (" + 
			"    'a', 0, 0" + 
			")"));
	}
	
	@Test
	public void test50() throws SQLException {
		// Test all equal except USER_ID for USER_INFO's PRIMARY KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test51() throws SQLException {
		// Test all columns equal for USER_INFO's PRIMARY KEY[USER_ID]
		// Test USER_ID is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test52() throws SQLException {
		// Test USER_ID is NULL for USER_INFO's PRIMARY KEY[USER_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    NULL, '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test53() throws SQLException {
		// Test all equal except DEPT_ID for USER_INFO's FOREIGN KEY[DEPT_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    '', '', '', '', 1, 0, 0, 0, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test54() throws SQLException {
		// Test all columns equal for USER_INFO's FOREIGN KEY[DEPT_ID]
		// Test DEPT_ID is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test55() throws SQLException {
		// Test DEPT_ID is NULL for USER_INFO's FOREIGN KEY[DEPT_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', NULL, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test56() throws SQLException {
		// Test all equal except OFFICE_ID for USER_INFO's FOREIGN KEY[OFFICE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    '', '', '', '', 0, 1, 0, 0, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test57() throws SQLException {
		// Test all columns equal for USER_INFO's FOREIGN KEY[OFFICE_ID]
		// Test OFFICE_ID is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test58() throws SQLException {
		// Test OFFICE_ID is NULL for USER_INFO's FOREIGN KEY[OFFICE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, NULL, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test59() throws SQLException {
		// Test all equal except RACE for USER_INFO's FOREIGN KEY[RACE]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    '', '', '', '', 0, 0, 0, 1, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test60() throws SQLException {
		// Test all columns equal for USER_INFO's FOREIGN KEY[RACE]
		// Test RACE is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test61() throws SQLException {
		// Test RACE is NULL for USER_INFO's FOREIGN KEY[RACE]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, NULL, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test62() throws SQLException {
		// Test USER_ID is NOT NULL for USER_INFO's NOT NULL(USER_ID)
		// Test PASSWORD is NOT NULL for USER_INFO's NOT NULL(PASSWORD)
		// Test USER_ID is NOT NULL for USER_INFO
		// Test PASSWORD is NOT NULL for USER_INFO
		// Test USER_ID is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test63() throws SQLException {
		// Test USER_ID is NULL for USER_INFO's NOT NULL(USER_ID)
		// Test USER_ID is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    NULL, '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test64() throws SQLException {
		// Test PASSWORD is NULL for USER_INFO's NOT NULL(PASSWORD)
		// Test PASSWORD is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"USER_INFO\"(" + 
				"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
				") VALUES (" + 
				"    '', '', '', '', 0, 0, 0, 0, NULL, 0, '1000-01-01'" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test65() throws SQLException {
		// Test FIRST_NAME is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', NULL, '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test66() throws SQLException {
		// Test FIRST_NAME is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test67() throws SQLException {
		// Test LAST_NAME is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', NULL, '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test68() throws SQLException {
		// Test LAST_NAME is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test69() throws SQLException {
		// Test SEX is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', NULL, 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test70() throws SQLException {
		// Test SEX is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test71() throws SQLException {
		// Test DEPT_ID is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', NULL, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test72() throws SQLException {
		// Test OFFICE_ID is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, NULL, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test73() throws SQLException {
		// Test GRADUATE is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, NULL, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test74() throws SQLException {
		// Test GRADUATE is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test75() throws SQLException {
		// Test RACE is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, NULL, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test76() throws SQLException {
		// Test YEARS_USING_UNIX is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', NULL, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test77() throws SQLException {
		// Test YEARS_USING_UNIX is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test78() throws SQLException {
		// Test ENROLL_DATE is NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, NULL" + 
			")"));
	}
	
	@Test
	public void test79() throws SQLException {
		// Test ENROLL_DATE is NOT NULL for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test80() throws SQLException {
		// Test FIRST_NAME is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', 'a', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test81() throws SQLException {
		// Test FIRST_NAME is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test82() throws SQLException {
		// Test LAST_NAME is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', 'a', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test83() throws SQLException {
		// Test LAST_NAME is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test84() throws SQLException {
		// Test SEX is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', 'a', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test85() throws SQLException {
		// Test SEX is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test86() throws SQLException {
		// Test DEPT_ID is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 1, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test87() throws SQLException {
		// Test DEPT_ID is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test88() throws SQLException {
		// Test OFFICE_ID is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 1, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test89() throws SQLException {
		// Test OFFICE_ID is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test90() throws SQLException {
		// Test GRADUATE is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 1, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test91() throws SQLException {
		// Test GRADUATE is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test92() throws SQLException {
		// Test RACE is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 1, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test93() throws SQLException {
		// Test RACE is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test94() throws SQLException {
		// Test PASSWORD is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, 'a', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test95() throws SQLException {
		// Test PASSWORD is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test96() throws SQLException {
		// Test YEARS_USING_UNIX is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 1, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test97() throws SQLException {
		// Test YEARS_USING_UNIX is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test98() throws SQLException {
		// Test ENROLL_DATE is UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1001-01-01'" + 
			")"));
	}
	
	@Test
	public void test99() throws SQLException {
		// Test ENROLL_DATE is NOT UNIQUE for USER_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    '', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"USER_INFO\"(" + 
			"    \"USER_ID\", \"FIRST_NAME\", \"LAST_NAME\", \"SEX\", \"DEPT_ID\", \"OFFICE_ID\", \"GRADUATE\", \"RACE\", \"PASSWORD\", \"YEARS_USING_UNIX\", \"ENROLL_DATE\"" + 
			") VALUES (" + 
			"    'a', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01'" + 
			")"));
	}
	
	@Test
	public void test100() throws SQLException {
		// Test all equal except RACE_CODE for RACE_INFO's PRIMARY KEY[RACE_CODE]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
	}
	
	@Test
	public void test101() throws SQLException {
		// Test all columns equal for RACE_INFO's PRIMARY KEY[RACE_CODE]
		// Test RACE_CODE is NOT UNIQUE for RACE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"RACE_INFO\"(" + 
				"    \"RACE_CODE\", \"RACE\"" + 
				") VALUES (" + 
				"    0, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test102() throws SQLException {
		// Test RACE_CODE is NULL for RACE_INFO's PRIMARY KEY[RACE_CODE]

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"RACE_INFO\"(" + 
				"    \"RACE_CODE\", \"RACE\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test103() throws SQLException {
		// Test RACE_CODE is NOT NULL for RACE_INFO's NOT NULL(RACE_CODE)
		// Test RACE_CODE is NOT NULL for RACE_INFO
		// Test RACE_CODE is UNIQUE for RACE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
	}
	
	@Test
	public void test104() throws SQLException {
		// Test RACE_CODE is NULL for RACE_INFO's NOT NULL(RACE_CODE)
		// Test RACE_CODE is NULL for RACE_INFO

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"RACE_INFO\"(" + 
				"    \"RACE_CODE\", \"RACE\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test105() throws SQLException {
		// Test RACE is NULL for RACE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, NULL" + 
			")"));
	}
	
	@Test
	public void test106() throws SQLException {
		// Test RACE is NOT NULL for RACE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
	}
	
	@Test
	public void test107() throws SQLException {
		// Test RACE is UNIQUE for RACE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, 'a'" + 
			")"));
	}
	
	@Test
	public void test108() throws SQLException {
		// Test RACE is NOT UNIQUE for RACE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"RACE_INFO\"(" + 
			"    \"RACE_CODE\", \"RACE\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
	}
	
	@Test
	public void test109() throws SQLException {
		// Test all equal except OFFICE_ID for OFFICE_INFO's PRIMARY KEY[OFFICE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
	}
	
	@Test
	public void test110() throws SQLException {
		// Test all columns equal for OFFICE_INFO's PRIMARY KEY[OFFICE_ID]
		// Test OFFICE_ID is NOT UNIQUE for OFFICE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"OFFICE_INFO\"(" + 
				"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
				") VALUES (" + 
				"    0, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test111() throws SQLException {
		// Test OFFICE_ID is NULL for OFFICE_INFO's PRIMARY KEY[OFFICE_ID]

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"OFFICE_INFO\"(" + 
				"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
				") VALUES (" + 
				"    NULL, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test112() throws SQLException {
		// Test OFFICE_ID is NOT NULL for OFFICE_INFO's NOT NULL(OFFICE_ID)
		// Test OFFICE_ID is NOT NULL for OFFICE_INFO
		// Test OFFICE_ID is UNIQUE for OFFICE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
	}
	
	@Test
	public void test113() throws SQLException {
		// Test OFFICE_ID is NULL for OFFICE_INFO's NOT NULL(OFFICE_ID)
		// Test OFFICE_ID is NULL for OFFICE_INFO

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"OFFICE_INFO\"(" + 
				"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
				") VALUES (" + 
				"    NULL, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test114() throws SQLException {
		// Test OFFICE_NAME is NULL for OFFICE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, NULL, 0" + 
			")"));
	}
	
	@Test
	public void test115() throws SQLException {
		// Test OFFICE_NAME is NOT NULL for OFFICE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
	}
	
	@Test
	public void test116() throws SQLException {
		// Test HAS_PRINTER is NULL for OFFICE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', NULL" + 
			")"));
	}
	
	@Test
	public void test117() throws SQLException {
		// Test HAS_PRINTER is NOT NULL for OFFICE_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));
	}
	
	@Test
	public void test118() throws SQLException {
		// Test OFFICE_NAME is UNIQUE for OFFICE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, 'a', 0" + 
			")"));
	}
	
	@Test
	public void test119() throws SQLException {
		// Test OFFICE_NAME is NOT UNIQUE for OFFICE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
	}
	
	@Test
	public void test120() throws SQLException {
		// Test HAS_PRINTER is UNIQUE for OFFICE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 1" + 
			")"));
	}
	
	@Test
	public void test121() throws SQLException {
		// Test HAS_PRINTER is NOT UNIQUE for OFFICE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"OFFICE_INFO\"(" + 
			"    \"OFFICE_ID\", \"OFFICE_NAME\", \"HAS_PRINTER\"" + 
			") VALUES (" + 
			"    1, '', 0" + 
			")"));
	}
	
	@Test
	public void test122() throws SQLException {
		// Test all equal except COURSE_ID for COURSE_INFO's PRIMARY KEY[COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test123() throws SQLException {
		// Test all columns equal for COURSE_INFO's PRIMARY KEY[COURSE_ID]
		// Test COURSE_ID is NOT UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		try {
			statement.executeUpdate(
				"INSERT INTO \"COURSE_INFO\"(" + 
				"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
				") VALUES (" + 
				"    0, '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test124() throws SQLException {
		// Test COURSE_ID is NULL for COURSE_INFO's PRIMARY KEY[COURSE_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"COURSE_INFO\"(" + 
				"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
				") VALUES (" + 
				"    NULL, '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test125() throws SQLException {
		// Test all equal except OFFERED_DEPT for COURSE_INFO's FOREIGN KEY[OFFERED_DEPT]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"COURSE_INFO\"(" + 
				"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
				") VALUES (" + 
				"    0, '', 1, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test126() throws SQLException {
		// Test all columns equal for COURSE_INFO's FOREIGN KEY[OFFERED_DEPT]
		// Test OFFERED_DEPT is NOT NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test127() throws SQLException {
		// Test OFFERED_DEPT is NULL for COURSE_INFO's FOREIGN KEY[OFFERED_DEPT]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', NULL, 0" + 
			")"));
	}
	
	@Test
	public void test128() throws SQLException {
		// Test COURSE_ID is NOT NULL for COURSE_INFO's NOT NULL(COURSE_ID)
		// Test COURSE_ID is NOT NULL for COURSE_INFO
		// Test COURSE_ID is UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test129() throws SQLException {
		// Test COURSE_ID is NULL for COURSE_INFO's NOT NULL(COURSE_ID)
		// Test COURSE_ID is NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"COURSE_INFO\"(" + 
				"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
				") VALUES (" + 
				"    NULL, '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test130() throws SQLException {
		// Test COURSE_NAME is NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, NULL, 0, 0" + 
			")"));
	}
	
	@Test
	public void test131() throws SQLException {
		// Test COURSE_NAME is NOT NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test132() throws SQLException {
		// Test OFFERED_DEPT is NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', NULL, 0" + 
			")"));
	}
	
	@Test
	public void test133() throws SQLException {
		// Test GRADUATE_LEVEL is NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, NULL" + 
			")"));
	}
	
	@Test
	public void test134() throws SQLException {
		// Test GRADUATE_LEVEL is NOT NULL for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test135() throws SQLException {
		// Test COURSE_NAME is UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, 'a', 0, 0" + 
			")"));
	}
	
	@Test
	public void test136() throws SQLException {
		// Test COURSE_NAME is NOT UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test137() throws SQLException {
		// Test OFFERED_DEPT is UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 1, 0" + 
			")"));
	}
	
	@Test
	public void test138() throws SQLException {
		// Test OFFERED_DEPT is NOT UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test139() throws SQLException {
		// Test GRADUATE_LEVEL is UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 1" + 
			")"));
	}
	
	@Test
	public void test140() throws SQLException {
		// Test GRADUATE_LEVEL is NOT UNIQUE for COURSE_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    0, '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"COURSE_INFO\"(" + 
			"    \"COURSE_ID\", \"COURSE_NAME\", \"OFFERED_DEPT\", \"GRADUATE_LEVEL\"" + 
			") VALUES (" + 
			"    1, '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test141() throws SQLException {
		// Test all equal except DEPT_ID for DEPT_INFO's PRIMARY KEY[DEPT_ID]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
	}
	
	@Test
	public void test142() throws SQLException {
		// Test all columns equal for DEPT_INFO's PRIMARY KEY[DEPT_ID]
		// Test DEPT_ID is NOT UNIQUE for DEPT_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"DEPT_INFO\"(" + 
				"    \"DEPT_ID\", \"DEPT_NAME\"" + 
				") VALUES (" + 
				"    0, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test143() throws SQLException {
		// Test DEPT_ID is NULL for DEPT_INFO's PRIMARY KEY[DEPT_ID]

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"DEPT_INFO\"(" + 
				"    \"DEPT_ID\", \"DEPT_NAME\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test144() throws SQLException {
		// Test DEPT_ID is NOT NULL for DEPT_INFO's NOT NULL(DEPT_ID)
		// Test DEPT_ID is NOT NULL for DEPT_INFO
		// Test DEPT_ID is UNIQUE for DEPT_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
	}
	
	@Test
	public void test145() throws SQLException {
		// Test DEPT_ID is NULL for DEPT_INFO's NOT NULL(DEPT_ID)
		// Test DEPT_ID is NULL for DEPT_INFO

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"DEPT_INFO\"(" + 
				"    \"DEPT_ID\", \"DEPT_NAME\"" + 
				") VALUES (" + 
				"    NULL, ''" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test146() throws SQLException {
		// Test DEPT_NAME is NULL for DEPT_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, NULL" + 
			")"));
	}
	
	@Test
	public void test147() throws SQLException {
		// Test DEPT_NAME is NOT NULL for DEPT_INFO

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));
	}
	
	@Test
	public void test148() throws SQLException {
		// Test DEPT_NAME is UNIQUE for DEPT_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, 'a'" + 
			")"));
	}
	
	@Test
	public void test149() throws SQLException {
		// Test DEPT_NAME is NOT UNIQUE for DEPT_INFO

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    0, ''" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"DEPT_INFO\"(" + 
			"    \"DEPT_ID\", \"DEPT_NAME\"" + 
			") VALUES (" + 
			"    1, ''" + 
			")"));
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}

