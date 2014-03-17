package generatedtest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestBankAccount {
	
	private static final String JDBC_CLASS = "org.sqlite.JDBC";
	private static final String CONNECTION_URL = "jdbc:sqlite:BankAccount";

	private static Connection connection;
	private static Statement statement;
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName(JDBC_CLASS);
		connection = DriverManager.getConnection(CONNECTION_URL);
		statement = connection.createStatement();

		// drop the tables for this database (if they exist)
		statement.executeUpdate("DROP TABLE IF EXISTS \"Account\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"UserInfo\"");

		// create the tables for this database 
		statement.executeUpdate(
			"CREATE TABLE \"UserInfo\" (" + 
			"    \"card_number\"    INT    PRIMARY KEY," + 
			"    \"pin_number\"    INT    NOT NULL," + 
			"    \"user_name\"    VARCHAR(50)    NOT NULL," + 
			"    \"acct_lock\"    INT" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"Account\" (" + 
			"    \"id\"    INT    PRIMARY KEY," + 
			"    \"account_name\"    VARCHAR(50)    NOT NULL," + 
			"    \"user_name\"    VARCHAR(50)    NOT NULL," + 
			"    \"balance\"    INT," + 
			"    \"card_number\"    INT     REFERENCES \"UserInfo\" (\"card_number\")    NOT NULL" + 
			")");
	}
	
	@Before
	public void clearTables() throws SQLException {
		statement.executeUpdate("DELETE FROM \"Account\"");
		statement.executeUpdate("DELETE FROM \"UserInfo\"");
	}
	
	@Test
	public void test0() throws SQLException {
		// Test all equal except id for Account's PRIMARY KEY[id]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -34, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    27, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test1() throws SQLException {
		// Test all columns equal for Account's PRIMARY KEY[id]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    75, 0, '', 0" + 
			")"));
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test2() throws SQLException {
		// Test id is NULL for Account's PRIMARY KEY[id]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    NULL, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test3() throws SQLException {
		// Test all equal except card_number for Account's FOREIGN KEY[card_number]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, -75" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test4() throws SQLException {
		// Test all columns equal for Account's FOREIGN KEY[card_number]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test5() throws SQLException {
		// Test card_number is NULL for Account's FOREIGN KEY[card_number]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, NULL" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test6() throws SQLException {
		// Test account_name is NOT NULL for Account's NOT NULL(account_name)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test7() throws SQLException {
		// Test account_name is NULL for Account's NOT NULL(account_name)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, NULL, '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test8() throws SQLException {
		// Test user_name is NOT NULL for Account's NOT NULL(user_name)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test9() throws SQLException {
		// Test user_name is NULL for Account's NOT NULL(user_name)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', NULL, 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test10() throws SQLException {
		// Test card_number is NOT NULL for Account's NOT NULL(card_number)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test11() throws SQLException {
		// Test card_number is NULL for Account's NOT NULL(card_number)

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, NULL" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test12() throws SQLException {
		// Test id is NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    NULL, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test13() throws SQLException {
		// Test id is NOT NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test14() throws SQLException {
		// Test account_name is NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, NULL, '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test15() throws SQLException {
		// Test account_name is NOT NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test16() throws SQLException {
		// Test user_name is NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', NULL, 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test17() throws SQLException {
		// Test user_name is NOT NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test18() throws SQLException {
		// Test balance is NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', NULL, 0" + 
			")"));
	}
	
	@Test
	public void test19() throws SQLException {
		// Test balance is NOT NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test20() throws SQLException {
		// Test card_number is NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, NULL" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test21() throws SQLException {
		// Test card_number is NOT NULL for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test22() throws SQLException {
		// Test id is UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    25, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    9, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test23() throws SQLException {
		// Test id is NOT UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    98, 0, '', 0" + 
			")"));
		try {
			statement.executeUpdate(
				"INSERT INTO \"Account\"(" + 
				"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
				") VALUES (" + 
				"    0, '', '', 0, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test24() throws SQLException {
		// Test account_name is UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    33, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    46, 'synehq', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test25() throws SQLException {
		// Test account_name is NOT UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    6, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    21, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test26() throws SQLException {
		// Test user_name is UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    80, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    42, '', 'tppaxhl', 0, 0" + 
			")"));
	}
	
	@Test
	public void test27() throws SQLException {
		// Test user_name is NOT UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    13, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    -36, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test28() throws SQLException {
		// Test balance is UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -4, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    57, '', '', 27, 0" + 
			")"));
	}
	
	@Test
	public void test29() throws SQLException {
		// Test balance is NOT UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    64, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    45, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test30() throws SQLException {
		// Test card_number is UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    9, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    48, '', '', 0, 9" + 
			")"));
	}
	
	@Test
	public void test31() throws SQLException {
		// Test card_number is NOT UNIQUE for Account

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    0, '', '', 0, 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    1, 0, '', 0" + 
			")"));
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"Account\"(" + 
			"    \"id\", \"account_name\", \"user_name\", \"balance\", \"card_number\"" + 
			") VALUES (" + 
			"    -5, '', '', 0, 0" + 
			")"));
	}
	
	@Test
	public void test32() throws SQLException {
		// Test all equal except card_number for UserInfo's PRIMARY KEY[card_number]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -98, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test33() throws SQLException {
		// Test all columns equal for UserInfo's PRIMARY KEY[card_number]

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, 0, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test34() throws SQLException {
		// Test card_number is NULL for UserInfo's PRIMARY KEY[card_number]

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    NULL, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test35() throws SQLException {
		// Test pin_number is NOT NULL for UserInfo's NOT NULL(pin_number)

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test36() throws SQLException {
		// Test pin_number is NULL for UserInfo's NOT NULL(pin_number)

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, NULL, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test37() throws SQLException {
		// Test user_name is NOT NULL for UserInfo's NOT NULL(user_name)

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test38() throws SQLException {
		// Test user_name is NULL for UserInfo's NOT NULL(user_name)

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, 0, NULL, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test39() throws SQLException {
		// Test card_number is NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    NULL, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test40() throws SQLException {
		// Test card_number is NOT NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test41() throws SQLException {
		// Test pin_number is NULL for UserInfo

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, NULL, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test42() throws SQLException {
		// Test pin_number is NOT NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test43() throws SQLException {
		// Test user_name is NULL for UserInfo

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, 0, NULL, 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test44() throws SQLException {
		// Test user_name is NOT NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test45() throws SQLException {
		// Test acct_lock is NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', NULL" + 
			")"));
	}
	
	@Test
	public void test46() throws SQLException {
		// Test acct_lock is NOT NULL for UserInfo

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test47() throws SQLException {
		// Test card_number is UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    21, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test48() throws SQLException {
		// Test card_number is NOT UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate(
				"INSERT INTO \"UserInfo\"(" + 
				"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
				") VALUES (" + 
				"    0, 0, '', 0" + 
				")");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test49() throws SQLException {
		// Test pin_number is UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    82, -48, '', 0" + 
			")"));
	}
	
	@Test
	public void test50() throws SQLException {
		// Test pin_number is NOT UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -15, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test51() throws SQLException {
		// Test user_name is UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -30, 0, 'uabqpakvq', 0" + 
			")"));
	}
	
	@Test
	public void test52() throws SQLException {
		// Test user_name is NOT UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -37, 0, '', 0" + 
			")"));
	}
	
	@Test
	public void test53() throws SQLException {
		// Test acct_lock is UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -96, 0, '', -55" + 
			")"));
	}
	
	@Test
	public void test54() throws SQLException {
		// Test acct_lock is NOT UNIQUE for UserInfo

		// prepare the database state
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    0, 0, '', 0" + 
			")"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate(
			"INSERT INTO \"UserInfo\"(" + 
			"    \"card_number\", \"pin_number\", \"user_name\", \"acct_lock\"" + 
			") VALUES (" + 
			"    -96, 0, '', 0" + 
			")"));
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}

