
package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.DropStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChecksTestCaseExecutor extends TestCaseExecutor {
    private static final Logger logger = Logger.getLogger(ChecksTestCaseExecutor.class.getName());

    public ChecksTestCaseExecutor(Schema schema, DBMS dbms, DatabaseInteractor databaseInteractor) {
        super(schema, dbms, databaseInteractor);
    }

    private void executeDeletes() {
        List<String> deleteStatements = sqlWriter.writeDeleteFromTableStatements(schema);
        for (String delete : deleteStatements) {
            databaseInteractor.executeUpdate(delete);
        }
    }
    
    @Override
    public void executeCreates() throws CreateStatementException {
        // Do nothing (should not be being called)
    }

    @Override
    public void executeDrops() throws DropStatementException {
        // Do nothing (should not be being called)
    }

    @Override
    public TestCaseResult executeTestCase(TestCase testCase) {
        TestCaseResult result;
        try {
            executeDeletes();
            executeInserts(testCase.getState());
            executeInserts(testCase.getData());
            executeDeletes();
            result = TestCaseResult.SuccessfulTestCaseResult;
        } catch (StatementException ex) {
            result = new TestCaseResult(ex);
        }
        return result;
    }

    @Override
    public TestCaseResult executeTestCase(TestCase testCase, TestCaseResult expectedResult) {
        throw new UnsupportedOperationException("Transactions cannot be used with ChecksTechnique");
    }
    
    public void setMutantId(int id) {
        logger.log(Level.INFO, "Switching to mutant: {0}", id);
        databaseInteractor.executeUpdate("UPDATE schemaanalyst_activemutant SET id = " + id);
    }
    
    
}
