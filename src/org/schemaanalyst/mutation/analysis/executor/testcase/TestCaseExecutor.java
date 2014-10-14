package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.DropStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.InsertStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Executes {@link TestCase}s with a given {@link Schema} and {@link DBMS}.</p>
 *
 * @author Chris J. Wright
 */
public class TestCaseExecutor {

    final protected static int TRANSACTION_SIZE = 100;
    final protected Schema schema;
    final protected List<Table> tables;
    final protected DatabaseInteractor databaseInteractor;
    final protected SQLWriter sqlWriter;
    final protected DBMS dbms;

    /**
     * Construct an executor.
     *
     * @param schema The schema
     * @param dbms The DBMS
     * @param databaseInteractor The interactor for the DBMS
     */
    public TestCaseExecutor(Schema schema, DBMS dbms, DatabaseInteractor databaseInteractor) {
        this.schema = schema;
        tables = schema.getTablesInOrder();
        this.dbms = dbms;
        sqlWriter = dbms.getSQLWriter();
        this.databaseInteractor = databaseInteractor;
    }

    public void executeCreates() throws CreateStatementException {
        List<String> createStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createStatements) {
            Integer result = databaseInteractor.executeUpdate(statement);
            if (result < 0) {
                throw new CreateStatementException("Failed, result was: " + result, statement);
            }
        }
    }

    public void executeCreatesInTransaction() throws CreateStatementException {
        List<String> createStatements = sqlWriter.writeCreateTableStatements(schema);
        Integer result = databaseInteractor.executeCreatesAsTransaction(createStatements, TRANSACTION_SIZE);
        if (result < 0) {
            throw new CreateStatementException("Failed, result was: " + result, "(Executed in transaction)");
        }
    }

    public void executeDrops() throws DropStatementException {
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            Integer result = databaseInteractor.executeUpdate(statement);
            if (result < 0) {
                throw new DropStatementException("Failed, result was: " + result, statement);
            }
        }
    }

    public void executeDropsInTransaction() throws DropStatementException {
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        Integer result = databaseInteractor.executeDropsAsTransaction(dropTableStatements, TRANSACTION_SIZE);
        if (result < 0) {
            throw new DropStatementException("Failed, result was: " + result, "(Executed in transaction)");
        }
    }

    public void executeInserts(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : tables) {
            if (stateTables.contains(table)) {
                List<Row> rows = data.getRows(table);
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    Integer result = databaseInteractor.executeUpdate(statement);
                    if (result != 1) {
                        throw new InsertStatementException("Failed, result was: " + result, statement);
                    }
                }
            }
        }
    }

    public void executeInsertsInTransaction(Data data) {
        List<Table> stateTables = data.getTables();
        for (Table table : tables) {
            if (stateTables.contains(table)) {
                List<Row> rows = data.getRows(table);
                List<String> statements = new ArrayList<>();
                for (Row row : rows) {
                    String statement = sqlWriter.writeInsertStatement(row);
                    statements.add(statement);
                }
                Integer result = databaseInteractor.executeUpdatesAsTransaction(statements);
                if (result != 1) {
                    throw new InsertStatementException("Failed, result was: " + result, "(Executed in transaction)");
                }
            }
        }
    }

    /**
     * Execute a {@link TestCase} with the {@link Schema} and {@link DBMS} given
     * in the constructor.
     *
     * @param testCase The test case
     * @return The result obtained
     */
    public TestCaseResult executeTestCase(TestCase testCase) {
        TestCaseResult result;
        try {
            executeDrops();
            executeCreates();
            executeInserts(testCase.getState());
            executeInserts(testCase.getData());
            executeDrops();
            result = TestCaseResult.SuccessfulTestCaseResult;
        } catch (StatementException ex) {
            result = new TestCaseResult(ex);
        }
        return result;
    }

    /**
     * Execute a {@link TestCase} with the {@link Schema} and {@link DBMS} given
     * in the constructor.
     *
     * @param testCase The test case
     * @param expectedResult The expected result, if one is known
     * @return The result obtained
     */
    public TestCaseResult executeTestCase(TestCase testCase, TestCaseResult expectedResult) {
        TestCaseResult result;
        try {
            executeDropsInTransaction();
            executeCreatesInTransaction();
            executeInsertsInTransaction(testCase.getState());
            if (expectedResult.wasSuccessful()) {
                executeInsertsInTransaction(testCase.getData());
            } else {
                executeInserts(testCase.getData());
            }
            executeDropsInTransaction();
            result = TestCaseResult.SuccessfulTestCaseResult;
        } catch (StatementException ex) {
            result = new TestCaseResult(ex);
        }
        return result;
    }
}
