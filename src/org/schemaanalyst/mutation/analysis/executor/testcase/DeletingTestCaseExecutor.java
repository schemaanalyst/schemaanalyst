package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.sqlrepresentation.Schema;

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

}
