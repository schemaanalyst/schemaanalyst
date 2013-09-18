package org.schemaanalyst.javawriter;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.IndentableStringBuilder;

import parsedcasestudy.Inventory;

public class TestSuiteJavaWriter {

	private Schema schema;
	private DBMS dbms;
	private SQLWriter sqlWriter;
	private List<Data> data; 
	
	private JavaWriter javaWriter;
	private IndentableStringBuilder code;
	
	public TestSuiteJavaWriter(Schema schema, DBMS dbms, List<Data> data) {
		this.schema = schema;
		this.dbms = dbms;
		this.data = data;
		
		sqlWriter = dbms.getSQLWriter();
	}
	
	public void writeTestSuite() {
		// initialise	
        javaWriter = new JavaWriter();
        code = new IndentableStringBuilder();
	    
		code.appendln("public class Test" + schema.getName() + "With" + dbms.getName() + "{");
		code.appendln(1);
		code.appendln("private static Connection connection;");
		code.appendln("private static Statement statement;");
		
		writeInitialiseMethod();
		writeDataAcceptedTestMethod();
		writeCloseMethod();
		
		code.appendln(0, "}");
		System.out.println(code);
	}
	
	private void writeInitialiseMethod() {
		code.appendln(1);
		code.appendln("@BeforeClass");
		code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

		// DBMS specific code for creating a connection
		code.appendln(2, "// load the SQLite JDBC driver");
		code.appendln("Class.forName(\"org.sqlite.JDBC\");");
		code.appendln("connection = DriverManager.getConnection(\"jdbc:sqlite:" + schema.getName() + ");");
		
		code.appendln();
		code.appendln("// create the statement used by this test suite");
		code.appendln("statement = connection.createStatement();");

        code.appendln();
		code.appendln("// drop and create tables for this database");        
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
        	code.appendln(writeExecuteUpdate(statement));
        }
        
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
        	code.appendln(writeExecuteUpdate(statement));
        }		
		
		// create table SQL code
		code.appendln(1, "}");		
	}
	
	private void writeDataAcceptedTestMethod() {
		code.appendln(1);
		code.appendln("@Test");
		code.appendln("public void testDataAccepted() throws ClassNotFoundException, SQLException {");

		boolean first = true;
		for (Table table : schema.getTables()) {
			boolean writeTypes = first;
			if (first) {
				first = false;
			} else {
				code.appendln();
			}			
			String selectSQLStatement = "SELECT COUNT(*) FROM " + table.getName();
			code.appendln(2, "// check number of rows inserted into " + table.getName());
			code.appendln((writeTypes ? "ResultSet " : "") + "rs = " + writeExecuteQuery(selectSQLStatement));
			code.appendln((writeTypes ? "ResultSet " : "") + "count = rs.getInt(1)");
			code.appendln("assertEquals(\"The number of rows inserted into " + table.getName() + " should be 2\", 2, count);");

			
			first = false;
		}
		
		code.appendln(1, "}");		
	}
	
	private void writeDataRejectedMethods() {
		
	}
	
	private String writeExecuteQuery(String sqlStatement) {
		return "statement.executeQuery(" + formatSQLStatement(sqlStatement) + ");";
	}	
	
	private String writeExecuteUpdate(String sqlStatement) {
		return "statement.executeUpdate(" + formatSQLStatement(sqlStatement) + ");";
	}
	
	private String formatSQLStatement(String sqlStatement) {
		if (sqlStatement.contains("\n")) {
			String[] substrings = StringUtils.split(sqlStatement, "\n");        	
        	String indent = StringUtils.repeat("\t", 3) + "\"";
        	String separator = "\" + \n" + indent;
        	
        	return "\n" + indent + StringUtils.join(substrings, separator) + "\"";			
		} else {
			return "\"" + sqlStatement + "\"";
		}
	}
	
	private void writeCloseMethod() {
		code.appendln(1);
		code.appendln("@AfterClass");
		code.appendln("public static void close() throws SQLException {");
		code.appendln(2, "if (connection != null) {");
		code.appendln(3, "connection.close();");
		code.appendln(2, "}");
		code.appendln(1, "}");		
	}
	
	public static void main(String[] args) {
		new TestSuiteJavaWriter(new Inventory(), new SQLiteDBMS(), null).writeTestSuite();;
	}
}
