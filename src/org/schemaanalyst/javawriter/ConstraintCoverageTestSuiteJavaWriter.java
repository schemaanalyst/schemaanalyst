package org.schemaanalyst.javawriter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.ConstraintGoal;
import org.schemaanalyst.datageneration.TestCase;
import org.schemaanalyst.datageneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.IndentableStringBuilder;

public class ConstraintCoverageTestSuiteJavaWriter {

	private Schema schema;
	private SQLWriter sqlWriter; 
	private List<TestCase<ConstraintGoal>> usefulTestCases;
	private IndentableStringBuilder code;
	
	public ConstraintCoverageTestSuiteJavaWriter(Schema schema, DBMS dbms, TestSuite<ConstraintGoal> testSuite) {
		this.schema = schema;
		sqlWriter = dbms.getSQLWriter();
		usefulTestCases = testSuite.getUsefulTestCases();				
	}
	
	public String writeTestSuite(String packageName, String className) {
		// initialise	
        code = new IndentableStringBuilder();
	    
        code.appendln("package " + packageName + ";");
        code.appendln();
		code.appendln("import java.sql.Connection;");
		code.appendln("import java.sql.DriverManager;");
		code.appendln("import java.sql.ResultSet;");
		code.appendln("import java.sql.SQLException;");
		code.appendln("import java.sql.Statement;");
		code.appendln();
		code.appendln("import org.junit.AfterClass;");
		code.appendln("import org.junit.BeforeClass;");
		code.appendln("import org.junit.Test;");		
		code.appendln("import static org.junit.Assert.assertEquals;");        
		code.appendln();
		code.appendln("public class " + className + " {");
		code.appendln(1);
		code.appendln("private static Connection connection;");
		code.appendln("private static Statement statement;");
		
		writeInitialiseMethod();
		writeDataAcceptedTestMethod();
		writeDataRejectedMethods();
		writeCloseMethod();
		
		code.appendln(0, "}");
		return code.toString();
	}
	
	private void writeInitialiseMethod() {
		code.appendln(1);
		code.appendln("@BeforeClass");
		code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

		// DBMS specific code for creating a connection
		code.appendln(2, "// load the SQLite JDBC driver");
		code.appendln("Class.forName(\"org.sqlite.JDBC\");");
		code.appendln("connection = DriverManager.getConnection(\"jdbc:sqlite:" + schema.getName() + "\");");
		
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
		        
        if (usefulTestCases.size() > 0) {
        	TestCase<ConstraintGoal> dataAcceptedTestCase = usefulTestCases.get(0);
        	Data data = dataAcceptedTestCase.getData();
        	List<String> insertStatements = sqlWriter.writeInsertStatements(schema, data);
        	for (String statement : insertStatements) {
            	code.appendln(writeExecuteUpdate(statement));
            }		
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
			code.appendln((writeTypes ? "int " : "") + "count = rs.getInt(1);");
			code.appendln("assertEquals(\"The number of rows inserted into " + table.getName() + " should be 2\", 2, count);");
			
			first = false;
		}
		
		code.appendln(1, "}");		
	}
	
	private void writeDataRejectedMethods() {
				
		for (int i=1; i < usefulTestCases.size(); i++) {
			
			TestCase<ConstraintGoal> dataRejectedTestCase = usefulTestCases.get(i);
			Data data = dataRejectedTestCase.getData();
			List<ConstraintGoal> constraintGoals = dataRejectedTestCase.getCoveredElements();
			String methodName = "testDataRejected" + i;
			
			code.appendln(1);
			code.appendln("@Test(expected=SQLException.class)");
			code.appendln("public void " + methodName + "() throws SQLException {");
			
	    	List<String> insertStatements = sqlWriter.writeInsertStatements(schema, data);
	    	for (String statement : insertStatements) {	    		
	    		for (ConstraintGoal constraintGoal : constraintGoals) {
	    			code.appendln(2, formatConstraintGoalComment(constraintGoal));
	    		}	    		
	        	code.appendln(2, writeExecuteUpdate(statement));
	        }
	    	
	    	code.appendln(1, "}");
		}
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
	
	private String formatConstraintGoalComment(ConstraintGoal constraintGoal) {
		Constraint constraint = constraintGoal.getConstraint();
		return "// " + (constraintGoal.getSatisfy() ? "Satisfying" : " Violating" 
				+ " " + constraint + " on table " + constraint.getTable());
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
	
}
