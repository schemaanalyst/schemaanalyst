
package org.schemaanalyst.mutation.analysis.executor.testcase;

import java.util.List;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;

public class ChecksTestCaseExecutor extends TestCaseExecutor {
    private final List<String> createStatements;
    private final int mutantId;

    public ChecksTestCaseExecutor(Schema schema, DBMS dbms, DatabaseInteractor databaseInteractor, List<String> createStatements, int mutantId) {
        super(schema, dbms, databaseInteractor);
        this.createStatements = createStatements;
        this.mutantId = mutantId;
    }

    @Override
    public void executeCreates() throws CreateStatementException {
        for (String stmt : createStatements) {
            Integer result = databaseInteractor.executeUpdate(stmt);
            if (result < 0) {
                throw new CreateStatementException("Failed, results was: " + result, stmt);
            }
        }
    }

    @Override
    public void executeInserts(Data data) {
        databaseInteractor.executeUpdate("UPDATE schemaanalyst_activemutant SET id = " + mutantId);
        super.executeInserts(data);
    }

    @Override
    public TestCaseResult executeTestCase(TestCase testCase, TestCaseResult expectedResult) {
        throw new UnsupportedOperationException("Transactions cannot be used with ChecksTechnique");
    }
    
    
}
