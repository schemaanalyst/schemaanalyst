package org.schemaanalyst.javawriter;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.ConstraintGoal;
import org.schemaanalyst.datageneration.TestCase;
import org.schemaanalyst.datageneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.IndentableStringBuilder;

import parsedcasestudy.Inventory;

public class ConstraintCoverageTestSuiteJavaWriter {

	private Schema schema;
	private DBMS dbms;
	private SQLWriter sqlWriter;
	private TestSuite<ConstraintGoal> testSuite; 
	private List<TestCase<ConstraintGoal>> usefulTestCases;
	
	private JavaWriter javaWriter;
	private IndentableStringBuilder code;
	
	public ConstraintCoverageTestSuiteJavaWriter(Schema schema, DBMS dbms, TestSuite<ConstraintGoal> testSuite) {
		this.schema = schema;
		this.dbms = dbms;
		this.testSuite = testSuite;
		
		usefulTestCases = testSuite.getUsefulTestCases();		
		sqlWriter = dbms.getSQLWriter();
	}
	
	public void writeTestSuite() {
		// initialise	
        javaWriter = new JavaWriter();
        code = new IndentableStringBuilder();
	    
		code.appendln("public class " + schema.getName() + "ConstraintCoverage" + dbms.getName() + "{");
		code.appendln(1);
		code.appendln("private static Connection connection;");
		code.appendln("private static Statement statement;");
		
		writeInitialiseMethod();
		writeDataAcceptedTestMethod();
		writeDataRejectedMethods();
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
			code.appendln((writeTypes ? "ResultSet " : "") + "count = rs.getInt(1)");
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
			code.appendln("@Test");
			code.appendln("public void " + methodName + " throws SQLException {");
			
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
	
	public static void main(String[] args) {
		new ConstraintCoverageTestSuiteJavaWriter(new Inventory(), new SQLiteDBMS(), null).writeTestSuite();;
	}
}
