
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testcase.DeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.DeletingTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.List;

/**
 * A dummy mutation analysis technique that only executes the original test suite.
 * 
 * @author Chris J. Wright
 */
public class DummyTechnique extends Technique {

    public DummyTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
    }
    
    @Override
    public AnalysisResult analyse(TestSuiteResult originalResult) {
        executeTestSuite(schema, testSuite);
        return new AnalysisResult();
    }
    
    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    private TestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
            TestCaseExecutor caseExecutor = new DeletingTestCaseExecutor(schema, dbms, databaseInteractor);
            TestSuiteExecutor suiteExecutor = new DeletingTestSuiteExecutor();
            return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }
    
}
