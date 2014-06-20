package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;

import java.util.List;

public class DeletingTestCaseExecutor extends TestCaseExecutor {

    public DeletingTestCaseExecutor(Schema schema, DBMS dbms, DatabaseInteractor databaseInteractor) {
        super(schema, dbms, databaseInteractor);
    }

    private void executeDeletes() {
        List<String> deleteStatements = sqlWriter.writeDeleteFromTableStatements(schema);
        for (String delete : deleteStatements) {
            databaseInteractor.executeUpdate(delete);
        }
    }
    
    private void executeDeletesInTransaction() {
        List<String> deleteStatements = sqlWriter.writeDeleteFromTableStatements(schema);
        databaseInteractor.executeUpdatesAsTransaction(deleteStatements);
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
        TestCaseResult result;
        try {
            executeDeletesInTransaction();
            executeInsertsInTransaction(testCase.getState());
            if (expectedResult.wasSuccessful()) {
                
                executeInsertsInTransaction(testCase.getData());
            } else {
                executeInserts(testCase.getData());
            }
            executeDeletesInTransaction();
            result = TestCaseResult.SuccessfulTestCaseResult;
        } catch (StatementException ex) {
            result = new TestCaseResult(ex);
        }
        return result;
    }

}
