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

public class TestInventory_SQLite {

	private static Connection connection;
	private static Statement statement;

	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		connection = DriverManager.getConnection("jdbc:sqlite:Inventory");
		statement = connection.createStatement();

		statement.executeUpdate("DROP TABLE IF EXISTS Inventory");
		statement.executeUpdate(
				"CREATE TABLE Inventory ("
				+ "id	INT	PRIMARY KEY, "
				+ "product	VARCHAR(50)	UNIQUE,"
				+ "quantity	INT,"
				+ "price	DECIMAL(18, 2))");

		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(0, '', 0, 0)");
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(-52, 'c', 0, 0)");
	}
	
	@Test 
	public void testSatisfyAll() throws SQLException {
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM Inventory");
		int count = rs.getInt(1);
		assertEquals("The number of rows inserted should be 2", 2, count);
	}
	
	@Test(expected=SQLException.class)
	public void testNegate_Inventory_PrimaryKey_id() throws SQLException {
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(0, 'ijyv', 0, 0)");
	}

	@Test(expected=SQLException.class)
	public void testNegate_Inventory_Unique_product() throws SQLException {
		statement.executeUpdate("INSERT INTO Inventory(id, product, quantity, price) VALUES(-46, '', 0, 0);");
	}	
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
