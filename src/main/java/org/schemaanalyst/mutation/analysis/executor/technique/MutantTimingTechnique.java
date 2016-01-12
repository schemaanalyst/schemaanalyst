package org.schemaanalyst.mutation.analysis.executor.technique;

import java.io.File;
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
import java.util.Objects;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.monitoring.Timing;

/**
 * <p>
 * The 'Original' mutation analysis approach, with no optimisations, with added
 * measurement of mutant timings.</p>
 *
 * @author Chris J. Wright
 */
public class MutantTimingTechnique extends OriginalTechnique {

    /**
     * A pseudo-unique identifier for each execution.
     */
    private static final String IDENTIFIER = RandomStringUtils.randomAlphanumeric(20).toLowerCase();
    /**
     * The location to output mutant timing results to.
     */
    private static final String OUTPUT_FILE = new LocationsConfiguration().getResultsDir() + File.separator + "mutanttiming.dat";
    /**
     * The writer for persisting results.
     */
    private static final CSVFileWriter OUTPUT_WRITER = new CSVFileWriter(OUTPUT_FILE);

    public MutantTimingTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }

    @Override
    public AnalysisResult analyse(final TestSuiteResult originalResults) {
        final AnalysisResult result = new AnalysisResult();
        for (final Mutant<Schema> mutant : mutants) {
            StopWatch timer = new StopWatch();
            boolean killed = Timing.timedTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    TestSuiteResult mutantResult = executeTestSuite(mutant.getMutatedArtefact(), testSuite, originalResults);
                    boolean killed = !Objects.equals(originalResults, mutantResult);
                    if (killed) {
                        result.addKilled(mutant);
                    } else {
                        result.addLive(mutant);
                    }
                    return killed;
                }
            }, timer);
            outputMutant(mutant, killed, timer.getTime());
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

    private void outputMutant(Mutant<Schema> mutant, boolean killed, long time) {
        CSVResult result = new CSVResult();
        result.addValue("identifier", IDENTIFIER);
        result.addValue("dbms", dbms.getName());
        result.addValue("schema", schema.getName());
        result.addValue("operator", mutant.getSimpleDescription());
        result.addValue("type", mutant.getMutantType());
        result.addValue("killed", killed);
        result.addValue("time", time);
        OUTPUT_WRITER.write(result);
    }

}
