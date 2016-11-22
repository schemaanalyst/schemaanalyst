package org.schemaanalyst.schema.parser;

import java.sql.SQLException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class DBtoJavaSchema {
	
    @Parameter(names={"--dbEngine", "-d"}, description = "Database Engine Postgres or SQLite", required = true)
    private String dbEngine;
    @Parameter(names={"--url", "-url"}, description = "URL of the database (Postgres e.g. localhost:5432) or file path for SQLite", required = true)
    private String url;
    @Parameter(names={"--database_name", "-db"}, description = "Database name you want to connect to", required = true)
    private String database_name;
    @Parameter(names={"--username", "-u"}, description = "User name of the database if needed", required = false)
    private String username;
    @Parameter(names={"--password", "-p"}, description = "Password of the username", required = false)
    private String password;
    @Parameter(names={"--schemaName", "-s"}, description = "Outputed Schema name you want", required = true)
    private String schemaName;

	public static void main(String[] args) {
		
		DBtoJavaSchema main = new DBtoJavaSchema();
        new JCommander(main, args);
        main.run();
		
		/*
		try {
			SchemaMapper s = new SchemaMapper();
			s.printGeneralMetadata();
			// Print all the tables of the database scheme, with their names and
			// structure
			s.getColumnsMetadata(s.getTablesMetadata());
		} catch (SQLException e) {
			System.err.println("There was an error retrieving the metadata properties: " + e.getMessage());
		}
		*/
	}
	
	public void run() {
        //System.out.printf("%d %d", dbEngine, schemaName);
		try {
			// String dbEngine, String url, String database_name, String username, String password, String schemaName
			//System.out.println(dbEngine);
			SchemaMapper s = new SchemaMapper(dbEngine, url, database_name, username, password, schemaName);
			//s.printGeneralMetadata();
			// Print all the tables of the database scheme, with their names and
			// structure
			s.getColumnsMetadata(s.getTablesMetadata());
			//s.writeJavaSchema();
		} catch (SQLException e) {
			System.err.println("There was an error retrieving the metadata properties: " + e.getMessage());
		}
	}

}
