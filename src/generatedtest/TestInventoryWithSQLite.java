package generatedtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestInventoryWithSQLite {
	
	private static Connection connection;
	private static Statement statement;
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the SQLite JDBC driver
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:Inventory");

		// create the statement used by this test suite
		statement = connection.createStatement();

		// drop and create tables for this database
		statement.executeUpdate("DROP TABLE IF EXISTS Inventory");
		statement.executeUpdate(
			"CREATE TABLE Inventory (" + 
			"	id	INT	PRIMARY KEY," + 
			"	product	VARCHAR(50)	UNIQUE," + 
			"	quantity	INT," + 
			"	price	DECIMAL(18, 2)" + 
			")");
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(1, 'a', 0, 0)");
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(0, '', 0, 0)");
	}
	
	@Test
	public void testDataAccepted() throws ClassNotFoundException, SQLException {
		// check number of rows inserted into Inventory
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM Inventory");
		int count = rs.getInt(1);
		assertEquals("The number of rows inserted into Inventory should be 2", 2, count);
	}
	
	@Test(expected=SQLException.class)
	public void testDataRejected1() throws SQLException {
		//  Violating PRIMARY KEY[id] on table Inventory
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(0, NULL, 0, 0)");
	}
	
	@Test(expected=SQLException.class)
	public void testDataRejected2() throws SQLException {
		//  Violating UNIQUE[product] on table Inventory
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(-1, '', 0, 0)");
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}

