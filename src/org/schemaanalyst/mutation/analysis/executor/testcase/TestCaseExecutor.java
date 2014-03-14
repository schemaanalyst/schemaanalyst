package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.testgeneration.TestCase;
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

import java.util.List;

/**
 * <p>Executes {@link TestCase}s with a given {@link Schema} and {@link DBMS}.</p>
 * 
 * @author Chris J. Wright
 */
public class TestCaseExecutor {

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

    public void executeDrops() throws DropStatementException {
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            Integer result = databaseInteractor.executeUpdate(statement);
            if (result < 0) {
                throw new DropStatementException("Failed, result was: " + result, statement);
            }
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
}
