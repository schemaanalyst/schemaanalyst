package org.schemaanalyst.schema.parser;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.*;

import net.sf.jsqlparser.JSQLParserException;

import org.schemaanalyst.javawriter.*;
import org.schemaanalyst.configuration.LocationsConfiguration;


public class SchemaMapper {
	private Connection connection = null;
	private DatabaseMetaData metadata = null;
	private Schema schema;
	private String schemaName;
	private String dbEngine;
    /** The locations configuration field. */
    protected LocationsConfiguration locationsConfiguration;

	public SchemaMapper() {
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			System.err.println("There was an error getting the connection: " + e.getMessage());
		}

		try {
			metadata = connection.getMetaData();
		} catch (SQLException e) {
			System.err.println("There was an error getting the metadata: " + e.getMessage());
		}
	}
	
	public SchemaMapper(String dbEngine, String url, String database_name, String username, String password, String schemaName) {
		this.schemaName = schemaName;
		this.dbEngine = dbEngine;
		try {
			connection = DBConnection.getConnection(dbEngine, url, database_name, username, password);
		} catch (SQLException e) {
			System.err.println("There was an error getting the connection: " + e.getMessage());
		}

		try {
			metadata = connection.getMetaData();
		} catch (SQLException e) {
			System.err.println("There was an error getting the metadata: " + e.getMessage());
		}
	}

	/**
	 * Prints in the console the general metadata.
	 * 
	 * @throws SQLException
	 */
	public void printGeneralMetadata() throws SQLException {
		System.out.println("Database Product Name: " + metadata.getDatabaseProductName());
		System.out.println("Database Product Version: " + metadata.getDatabaseProductVersion());
		System.out.println("Logged User: " + metadata.getUserName());
		System.out.println("JDBC Driver: " + metadata.getDriverName());
		System.out.println("Driver Version: " + metadata.getDriverVersion());
		System.out.println("\n");
	}

	/**
	 * 
	 * @return Arraylist with the table's name
	 * @throws SQLException
	 */
	public ArrayList getTablesMetadata() throws SQLException {
		String table[] = { "TABLE" };
		ResultSet rs = null;
		ArrayList tables = null;
		// receive the Type of the object in a String array.
		rs = metadata.getTables(null, null, null, table);
		tables = new ArrayList();
		while (rs.next()) {
			tables.add(rs.getString("TABLE_NAME"));
		}
		return tables;
	}

	/**
	 * Prints in the console the columns metadata, based in the Arraylist of
	 * tables passed as parameter.
	 * 
	 * @param tables
	 * @throws SQLException
	 */
	public void getColumnsMetadata(ArrayList tables) throws SQLException {
		ResultSet rs = null;
		schema = new Schema(this.schemaName);
		// Print the columns properties of the actual table
		for (Object actualTable : tables) {
			Table t = new Table(actualTable.toString());
			schema.addTable(t);
			rs = metadata.getColumns(null, null, actualTable.toString(), null);
			while (rs.next()) {
				//String nullable = "";
				boolean nulling = true;
				if (rs.getBoolean("NULLABLE"))
					nulling = false;
				Column col = new Column(rs.getString("COLUMN_NAME"), this.getDataType(rs.getInt("DATA_TYPE"), rs.getString("TYPE_NAME"), rs.getInt("COLUMN_SIZE")));
				schema.getTable(t.toString()).addColumn(col);;
				if (nulling)
					schema.createNotNullConstraint(t, t.getColumn(col.getName()));
			}

			rs = metadata.getPrimaryKeys(null, null, actualTable.toString());
			List<Column> pks = new ArrayList<Column>();
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				System.out.println("PKs => " +columnName);
				pks.add(t.getColumn(columnName));
			}
			
			if (pks.size() > 0)
				schema.createPrimaryKeyConstraint(t, pks);
			
			boolean listUniqueIndex = true;
			rs = metadata.getIndexInfo(null, null, actualTable.toString(), listUniqueIndex, true);
			//List<Column> uni = new ArrayList<Column>();
			while (rs.next()) {
				String indexName = rs.getString("INDEX_NAME");
				String columnName = rs.getString("COLUMN_NAME");
				boolean uniqe = rs.getBoolean("NON_UNIQUE");

				if (indexName == null) {
					continue;
				}
				
				boolean itIsPK = false;
				
				if (schema.hasPrimaryKeyConstraint(t))
					if (schema.getPrimaryKeyConstraint(t).getColumns().contains(t.getColumn(columnName)))
						itIsPK = true;

				if (!uniqe && !itIsPK) {
					if (indexName == null || indexName.startsWith("sqlite_autoindex"))
						schema.createUniqueConstraint(t, t.getColumn(columnName));
					else
						schema.createUniqueConstraint(indexName,t, t.getColumn(columnName));
				}

			}			
			
		}
		
		// Sorting out FKS
		for (Object actualTable : tables) {
			Table t = schema.getTable(actualTable.toString());
			rs = metadata.getImportedKeys(null, null, actualTable.toString());
			while (rs.next()) {
				String pkTableName = rs.getString("PKTABLE_NAME");
				String pkColName = rs.getString("PKCOLUMN_NAME");
				//String fkTableName = rs.getString("FKTABLE_NAME");
				String fkColName = rs.getString("FKCOLUMN_NAME");
				//String fkname = rs.getString("FK_NAME");
				//String pkname = rs.getString("PK_NAME");

				schema.createForeignKeyConstraint(t, t.getColumn(fkColName), schema.getTable(pkTableName), schema.getTable(pkTableName).getColumn(pkColName));
			}

	        
		}
		if (dbEngine.equals("SQLite")) {
			for (Table table : schema.getTables()) {
			
			    Statement stmt = null;
		        stmt = connection.createStatement();
		        //System.out.println("SELECT * FROM sqlite_master WEHRE tbl_name = \""+ table.toString() +"\" and type = \"table\";");
		        rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE tbl_name = \""+ table.toString() +"\" and type = \"table\";");
		        
		        
				while (rs.next()) {
					CheckParser check = new CheckParser(table, rs.getString("sql"));
					try {
						check.printCheckStatments();
						List<CheckConstraint> checks = check.getChecks();
						for (CheckConstraint c : checks) {
							schema.addCheckConstraint(c);
						}
					} catch (JSQLParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		this.writeJavaSchema();
		/*
		for (Table t : schema.getTables()) {
			System.out.println(t);
			for (Column c : t.getColumns()) {
				System.out.println("\t" + c + " " + c.getDataType().toString());
			}
			System.out.println("\t" + schema.getNotNullConstraints(t));
			System.out.println("\t" + schema.getPrimaryKeyConstraint(t));
			System.out.println("\t" + schema.getUniqueConstraints(t));
			System.out.println("\t" + schema.getForeignKeyConstraints(t));
			System.out.println("\n");
		}
		*/
	}
	
	private DataType getDataType (int dataType, String datatypeDesc, Integer length) {
        switch (dataType) {
			case java.sql.Types.BOOLEAN:
		        return new BooleanDataType();
		
		    // *** CHARACTER STRING *** 
		    case java.sql.Types.CHAR:
		        return (length == null)
		                ? new SingleCharDataType()
		                : new CharDataType(length);
		
		    case java.sql.Types.VARCHAR:
		    	if (length >= 2000000000)
		    		return new TextDataType();
		    	else
		    		return new VarCharDataType(length);
		
	
		    // *** NUMERIC *** 	
		    case java.sql.Types.DECIMAL:
		        return new DecimalDataType();
		
		    case java.sql.Types.INTEGER:
		        return new IntDataType();
		
		    case java.sql.Types.SMALLINT:
		        return new SmallIntDataType();
		
		    case java.sql.Types.TINYINT:
		        return new TinyIntDataType();
		
		    // numeric
		    case java.sql.Types.NUMERIC:
		        return new NumericDataType();
		
		    case java.sql.Types.REAL:
		        return new RealDataType();
		
		    // *** TEMPORAL *** 
		    case java.sql.Types.DATE:
		        return new DateDataType();
		
		    case java.sql.Types.TIME:
		        return new TimeDataType();
		
		    case java.sql.Types.TIMESTAMP:
		        return new TimestampDataType();
		
		    default:
		        // Data type not supported
		    	if (datatypeDesc == "TEXT")
			        return new TextDataType();
		    	else if (datatypeDesc == "DATETIME")
		    		return new DateTimeDataType();
		    	else
		    		throw new IllegalArgumentException("not supported data type = " + datatypeDesc);
		}
	}
	
	
	public void writeJavaSchema() {
		//System.out.println(locationsConfiguration.getCaseStudyPackage());
		SchemaJavaWriter SJw = new SchemaJavaWriter(schema);
        String javaCode = SJw.writeSchema();
		System.out.println(javaCode);
		//
	}
	
}
