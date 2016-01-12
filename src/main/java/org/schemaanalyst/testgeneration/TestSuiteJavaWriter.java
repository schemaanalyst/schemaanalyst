package org.schemaanalyst.testgeneration;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.hypersql.HyperSQLDBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.util.IndentableStringBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 08/02/2014.
 */
public class TestSuiteJavaWriter {

    private Schema schema;
    private DBMS dbms;
    private TestSuite testSuite;

    private SQLWriter sqlWriter;

    private IndentableStringBuilder code;
    private int testCaseNumber;

    private boolean addSAComments;

    public TestSuiteJavaWriter(Schema schema, DBMS dbms, TestSuite testSuite, boolean addSAComments) {
        this.schema = schema;
        this.dbms = dbms;
        this.testSuite = testSuite;
        this.addSAComments = addSAComments;
        sqlWriter = dbms.getSQLWriter();
    }

    public String writeTestSuite(String packageName, String className) {
        code = new IndentableStringBuilder();
        testCaseNumber = 1;

        writeClassHeader(packageName, className);
        writeBeforeClassMethod();
        writeBeforeTestMethod();

        for (TestCase testCase : testSuite.getTestCases()) {
            writeTestCase(testCase);
            testCaseNumber++;
        }

        writeAfterClassMethod();
        writeClassFooter();

        return code.toString();
    }

    private void writeClassHeader(String packageName, String className) {
        code.appendln("package " + packageName + ";");
        code.appendln();
        code.appendln("import java.sql.Connection;");
        code.appendln("import java.sql.DriverManager;");
        code.appendln("import java.sql.SQLException;");
        code.appendln("import java.sql.Statement;");
        code.appendln();
        code.appendln("import org.junit.AfterClass;");
        code.appendln("import org.junit.BeforeClass;");
        code.appendln("import org.junit.Before;");
        code.appendln("import org.junit.Test;");
        code.appendln("import static org.junit.Assert.assertEquals;");
        code.appendln("import static org.junit.Assert.fail;");
        code.appendln();
        code.appendln("public class " + className + " {");
        code.appendln(1);

        // TODO -- would be nicer to replace this with a visitor pattern
        if (dbms instanceof SQLiteDBMS) {
            // TO DO -- take these from config files or interactor classes ?
            code.appendln("private static final String JDBC_CLASS = \"org.sqlite.JDBC\";");
            code.appendln("private static final String CONNECTION_URL = \"jdbc:sqlite:" + schema.getName() + "\";");
        } else if (dbms instanceof HyperSQLDBMS) {
            // TO DO -- take these from config files or interactor classes ?
            code.appendln("private static final String JDBC_CLASS = \"org.hsqldb.jdbc.JDBCDriver\";");
            code.appendln("private static final String CONNECTION_URL = \"jdbc:hsqldb:mem:/database;hsqldb.write_delay=false\";");
        }

        code.appendln();
        code.appendln("private static Connection connection;");
        code.appendln("private static Statement statement;");
    }

    private void writeBeforeClassMethod() {
        code.appendln(1);
        code.appendln("@BeforeClass");
        code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

        // DBMS specific code for creating a connection
        code.appendln(2, "// load the JDBC driver and create the connection and statement object used by this test suite");
        code.appendln("Class.forName(JDBC_CLASS);");
        code.appendln("connection = DriverManager.getConnection(CONNECTION_URL);");
        code.appendln("statement = connection.createStatement();");

        // TODO -- would be nicer to replace this with a visitor pattern
        if (dbms instanceof SQLiteDBMS) {
            code.appendln();
            code.appendln("// enable FOREIGN KEY support");
            code.appendln(writeExecuteUpdate("PRAGMA foreign_keys = ON") + ";");
        }

        code.appendln();
        code.appendln("// drop the tables for this database (if they exist)");
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            code.appendln(writeExecuteUpdate(statement) + ";");
        }

        code.appendln();
        code.appendln("// create the tables for this database ");
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
            code.appendln(writeExecuteUpdate(statement) + ";");
        }

        code.appendln(1, "}");
    }

    private void writeBeforeTestMethod() {
        code.appendln(1);
        code.appendln("@Before");
        code.appendln("public void clearTables() throws SQLException {");

        List<String> deleteFromTableStatements = sqlWriter.writeDeleteFromTableStatements(schema);
        for (String deleteFromTableStatement : deleteFromTableStatements) {
            code.appendln(2, writeExecuteUpdate(deleteFromTableStatement) + ";");
        }

        code.appendln(1, "}");
    }

    private void writeTestCase(TestCase testCase) {
        code.appendln(1);
        code.appendln("@Test");
        code.appendln("public void test" + testCaseNumber + "() throws SQLException {");

        code.setIndentLevel(2);
        TestRequirement testRequirement = testCase.getTestRequirement();
        for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
            code.appendln("// " + (addSAComments ? testRequirementDescriptor : testRequirementDescriptor.getMsg()));
        }
        if (addSAComments) {
            code.appendln("// " + testRequirement.getPredicate());
            code.appendln("// Result is: " + testRequirement.getResult());
        }

        Data state = testCase.getState();
        List<String> insertStatements = sqlWriter.writeInsertStatements(schema, state);
        if (insertStatements.size() > 0) {
            code.appendln();
            code.appendln("// prepare the database state");
            for (String statement : insertStatements) {
                code.appendln(2, "assertEquals(1, " + writeExecuteUpdate(statement) + ");");
            }
        }

        Data data = testCase.getData();
        List<Boolean> results = testCase.getDBMSResults();
        insertStatements = sqlWriter.writeInsertStatements(schema, data);
        Iterator<String> statementsIterator = insertStatements.iterator();
        Iterator<Boolean> resultsIterator = results.iterator();
        code.appendln();
        code.appendln("// execute INSERT statements for the test case");
        while (statementsIterator.hasNext()) {
            String statement = statementsIterator.next();
            boolean dbmsResult = resultsIterator.next();
            if (dbmsResult) {
                code.appendln("assertEquals(1, " + writeExecuteUpdate(statement) + ");");
            } else {
                code.appendln(2, "try {");

                code.setIndentLevel(3);
                code.appendln(writeExecuteUpdate(statement) + ";");
                code.appendln("fail(\"Expected constraint violation did not occur\");");

                code.appendln(2, "} catch (SQLException e) { /* expected exception thrown and caught */ }");
            }
        }
        code.appendln(1, "}");
    }

    private String writeExecuteUpdate(String sqlStatement) {
        return "statement.executeUpdate(" + formatSQLStatement(sqlStatement) + ")";
    }

    private String formatSQLStatement(String sqlStatement) {
        int indentLevel = code.getIndentLevel();
        if (sqlStatement.contains("\n")) {
            String indent = StringUtils.repeat("\t", indentLevel + 1);

            List<String> substatements = new ArrayList<>();
            for (String substring : StringUtils.split(sqlStatement, "\n")) {
                substatements.add("\n" + indent + formatOneLineStatement(substring));
            }
            return StringUtils.join(substatements, " + ");
        } else {
            return formatOneLineStatement(sqlStatement);
        }
    }

    private String formatOneLineStatement(String sqlStatement) {
        String tabsToSpaces = sqlStatement.replace("\t", "    ");
        return "\"" + StringEscapeUtils.escapeJava(tabsToSpaces) + "\"";
    }

    private void writeAfterClassMethod() {
        code.appendln(1);
        code.appendln("@AfterClass");
        code.appendln("public static void close() throws SQLException {");
        code.appendln(2, "if (connection != null) {");
        code.appendln(3, "connection.close();");
        code.appendln(2, "}");
        code.appendln(1, "}");
    }

    private void writeClassFooter() {
        code.appendln(0, "}");
    }
}
