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

public class TestFlights {
	
	private static final String JDBC_CLASS = "org.sqlite.JDBC";
	private static final String CONNECTION_URL = "jdbc:sqlite:Flights";

	private static Connection connection;
	private static Statement statement;
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName(JDBC_CLASS);
		connection = DriverManager.getConnection(CONNECTION_URL);
		statement = connection.createStatement();

		// drop the tables for this database (if they exist)
		statement.executeUpdate("DROP TABLE IF EXISTS FlightAvailable");
		statement.executeUpdate("DROP TABLE IF EXISTS Flights");

		// create the tables for this database 
		statement.executeUpdate(
			"CREATE TABLE Flights (" + 
			"	FLIGHT_ID	CHAR(6)	NOT NULL," + 
			"	SEGMENT_NUMBER	INT	NOT NULL," + 
			"	ORIG_AIRPORT	CHAR(3)," + 
			"	DEPART_TIME	TIME," + 
			"	DEST_AIRPORT	CHAR(3)," + 
			"	ARRIVE_TIME	TIME," + 
			"	MEAL	CHAR(1)	NOT NULL," + 
			"	PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER)," + 
			"	CONSTRAINT MEAL_CONSTRAINT CHECK (MEAL IN ('B', 'L', 'D', 'S'))" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE FlightAvailable (" + 
			"	FLIGHT_ID	CHAR(6)	NOT NULL," + 
			"	SEGMENT_NUMBER	INT	NOT NULL," + 
			"	FLIGHT_DATE	DATE	NOT NULL," + 
			"	ECONOMY_SEATS_TAKEN	INT," + 
			"	BUSINESS_SEATS_TAKEN	INT," + 
			"	FIRSTCLASS_SEATS_TAKEN	INT," + 
			"	CONSTRAINT FLTAVAIL_PK PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER)," + 
			"	CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER) REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)" + 
			")");
	}
	
	@Before
	public void clearTables() throws SQLException {
		statement.executeUpdate("DELETE FROM FlightAvailable");
		statement.executeUpdate("DELETE FROM Flights");
	}
	
	@Test
	public void test0() throws SQLException {
		// Test at least one column not equal for PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]
		// Test at least one column not equal for FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]
		// Test NOT NULL for FLIGHT_ID evaluating to TRUE (is NOT NULL)
		// Test NOT NULL for SEGMENT_NUMBER evaluating to TRUE (is NOT NULL)
		// Test NOT NULL for FLIGHT_DATE evaluating to TRUE (is NOT NULL)
		// Test at least one column not equal for PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]
		// Test MEAL IN ('B', 'L', 'D', 'S') evaluating to true
		// Test NOT NULL for FLIGHT_ID evaluating to TRUE (is NOT NULL)
		// Test NOT NULL for SEGMENT_NUMBER evaluating to TRUE (is NOT NULL)
		// Test NOT NULL for MEAL evaluating to TRUE (is NOT NULL)

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));
		assertEquals(1, statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0)"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('a', 0, '', '00:00:00', '', '00:00:00', 'B')"));
		assertEquals(1, statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('a', 0, '1000-01-01', 0, 0, 0)"));
	}
	
	@Test
	public void test1() throws SQLException {
		// Test all columns equal for PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));
		assertEquals(1, statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0)"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('a', 0, '', '00:00:00', '', '00:00:00', 'B')"));
		try {
			statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0)");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test2() throws SQLException {
		// Test all columns equal for FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));

		// execute INSERT statements for the test case
		assertEquals(1, statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0)"));
	}
	
	@Test
	public void test3() throws SQLException {
		// Test NOT NULL for FLIGHT_ID evaluating to FALSE (is NULL)

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES(NULL, 0, '1000-01-01', 0, 0, 0)");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test4() throws SQLException {
		// Test NOT NULL for SEGMENT_NUMBER evaluating to FALSE (is NULL)

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', NULL, '1000-01-01', 0, 0, 0)");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test5() throws SQLException {
		// Test NOT NULL for FLIGHT_DATE evaluating to FALSE (is NULL)

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, NULL, 0, 0, 0)");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test6() throws SQLException {
		// Test all columns equal for PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]

		// prepare the database state
		assertEquals(1, statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')"));

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'B')");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test7() throws SQLException {
		// Test MEAL IN ('B', 'L', 'D', 'S') evaluating to false

		// prepare the database state

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', '')");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test8() throws SQLException {
		// Test NOT NULL for FLIGHT_ID evaluating to FALSE (is NULL)

		// prepare the database state

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES(NULL, 0, '', '00:00:00', '', '00:00:00', 'B')");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test9() throws SQLException {
		// Test NOT NULL for SEGMENT_NUMBER evaluating to FALSE (is NULL)

		// prepare the database state

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', NULL, '', '00:00:00', '', '00:00:00', 'B')");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@Test
	public void test10() throws SQLException {
		// Test NOT NULL for MEAL evaluating to FALSE (is NULL)

		// prepare the database state

		// execute INSERT statements for the test case
		try {
			statement.executeUpdate("INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', NULL)");
			fail("Expected constraint violation did not occur");
		} catch (SQLException e) { /* expected exception thrown and caught */ }
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}

