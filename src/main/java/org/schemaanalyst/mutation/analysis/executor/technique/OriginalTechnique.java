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
 * <p>
 * The 'Original' mutation analysis approach, with no optimisations.</p>
 *
 * @author Chris J. Wright
 */
public class OriginalTechnique extends Technique {

    public OriginalTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        AnalysisResult result = new AnalysisResult();
        for (Mutant<Schema> mutant : mutants) {
            TestSuiteResult mutantResults = executeTestSuite(mutant.getMutatedArtefact(), testSuite, originalResults);
            if (!originalResults.equals(mutantResults)) {
                result.addKilled(mutant);
            } else {
                result.addLive(mutant);
            }
        }
        return result;
    }

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @param originalResults The expected results, if known
     * @return The execution results
     */
    private TestSuiteResult executeTestSuite(Schema schema, TestSuite suite, TestSuiteResult originalResults) {
        TestCaseExecutor caseExecutor = new DeletingTestCaseExecutor(schema, dbms, databaseInteractor);
        TestSuiteExecutor suiteExecutor = new DeletingTestSuiteExecutor();
        if (!useTransactions || originalResults == null) {
            return suiteExecutor.executeTestSuite(caseExecutor, suite);
        } else {
            return suiteExecutor.executeTestSuite(caseExecutor, suite, originalResults);
        }
    }

}
