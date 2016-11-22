package org.schemaanalyst.schema.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static String DB_URL = "jdbc:sqlite:/home/abdullah/workspace/SchemaAnalyst/casestudies/schemawithdata/BankAccount.db";
	private static String DB_USER = "";
	private static String DB_PASSWORD = "";
	private static Connection connection = null;

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		//System.err.println("The connection is successfully obtained");
		return connection;
	}
	
	public static Connection getConnection(String dbEngine, String url, String database_name, String username, String password) throws SQLException {
		try {
			String db_url = null;
			if (dbEngine.equals("Postgres")) {
				db_url = "jdbc:postgresql://"+ url +"/"+ database_name;
				connection = DriverManager.getConnection(db_url, username, password);
			} else if (dbEngine.equals("SQLite")) {
				db_url = "jdbc:sqlite:"+ url;
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(db_url);
			} else {
				System.out.println("Incorrect DB engine or not supported by SchemaAnalyst. The parameters you passed is the following :" );
				System.out.println("DBEngine : " + dbEngine);
				System.out.println("URL : " + url);
				System.out.println("DB name : " + database_name);
				System.out.println("Username : " + username);
				System.out.println("Passowrd : " + password);
				//throw new IllegalArgumentException("Incorrect DB engine or not supported by SchemaAnalyst. The parameter you passed is " + dbEngine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		//System.err.println("The connection is successfully obtained");
		return connection;
	}
}
