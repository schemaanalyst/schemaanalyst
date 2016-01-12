package org.schemaanalyst.mutation.analysis.executor.alters.testcase;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.alters.sqlwriter.ConstraintlessSQLWriter;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;

import java.util.List;

/**
 * Executes a test case for ALTER-style mutation analysis
 *
 * @author Chris J. Wright
 */
public class AltersTestCaseExecutor {

    private DatabaseInteractor databaseInteractor;
    private SQLWriter sqlWriter;
    private ConstraintlessSQLWriter constraintlessWriter;
    private Schema schema;

    public AltersTestCaseExecutor(Schema schema, DatabaseInteractor databaseInteractor, SQLWriter sqlWriter) {
        this.constraintlessWriter = new ConstraintlessSQLWriter();
        this.databaseInteractor = databaseInteractor;
        this.schema = schema;
        this.sqlWriter = sqlWriter;
    }
    
    public void executeCreates() {
        List<String> createStmts = constraintlessWriter.writeCreateTableStatements(schema);
        for (String stmt : createStmts) {
            Integer stmtResult = databaseInteractor.executeUpdate(stmt);
            if (stmtResult < 0) {
                throw new CreateStatementException("Failed, result was: " + stmtResult, stmt);
            }
        }
    }
    
    public void executeDrops() {
        List<String> dropStmts = constraintlessWriter.writeDropTableStatements(schema);
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    public void executeInserts(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : schema.getTablesInOrder()) {
            if (stateTables.contains(table)) {
                List<Row> rows = data.getRows(table);
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    databaseInteractor.executeUpdate(statement);
                }
            }
        }
    }

    public void executeAlters(TestCase testCase, TestSuiteResult result) {
        List<String> alterStmts = constraintlessWriter.writeAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            Integer res = databaseInteractor.executeUpdate(stmt);
            if (res != 0) {
                result.add(testCase, new TestCaseResult(new StatementException("Failed, result was: " + res, stmt)));
            } else {
                result.add(testCase, TestCaseResult.SuccessfulTestCaseResult);
            }
        }
    }
    
    public void executeAlters(Schema schema, TestCase testCase, TestSuiteResult result) {
        List<String> alterStmts = constraintlessWriter.writeAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            Integer res = databaseInteractor.executeUpdate(stmt);
            if (res != 0) {
                result.add(testCase, new TestCaseResult(new StatementException("Failed, result was: " + res, stmt)));
            } else {
                result.add(testCase, TestCaseResult.SuccessfulTestCaseResult);
            }
        }
    }

    public void executeDeletes() {
        for (String stmt : sqlWriter.writeDeleteFromTableStatements(schema)) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    public void executeDropAlters() {
        List<String> alterStmts = constraintlessWriter.writeDropAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }
    
    public void executeDropAlters(Schema schema) {
        List<String> alterStmts = constraintlessWriter.writeDropAlterTableStatements(schema);
        for (String stmt : alterStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    public void executeTestCase(TestCase testCase, TestSuiteResult result) {
        // Insert the test data
        executeInserts(testCase.getState());
        executeInserts(testCase.getData());

        // Add the constraints
        executeAlters(testCase, result);

        // Drop the constraints
        executeDropAlters();

        // Delete the test data
        executeDeletes();
    }

}
