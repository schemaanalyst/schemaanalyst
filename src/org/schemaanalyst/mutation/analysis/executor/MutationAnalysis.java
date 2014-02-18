package org.schemaanalyst.mutation.analysis.executor;

import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.criterion.types.CriterionFactory;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testcase.DeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.DeletingTestSuiteExecutor;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * An alternative implementation of mutation analysis, using the
 * {@link org.schemaanalyst.mutation.analysis.technique.Original} technique,
 * inserting data from a {@link TestSuite}.
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy criterion")
public class MutationAnalysis extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    /**
     * The name of the schema to use.
     */
    protected String casestudy;
    /**
     * The coverage criterion to use to generate data.
     */
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;
    /**
     * The maximum fitness evaluations when generating data.
     */
    @Parameter("The maximum fitness evaluations when generating data.")
    protected int maxevaluations = 100000;
    /**
     * The random seed.
     */
    @Parameter("The random seed.")
    protected long randomseed = 0;
    /**
     * Whether to re-use test cases.
     */
    @Parameter("Whether to re-use test cases.")
    protected boolean reuse = false;
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithRemovers";
    /**
     * Whether to print live mutants.
     */
    @Parameter("Whether to print live mutants.")
    protected boolean printLive = false;
    /**
     * Whether to use DELETE statements.
     */
    @Parameter("Whether to use DELETE statements.")
    protected boolean useDelete = false;
    /**
     * The instantiated schema.
     */
    protected Schema schema;
    /**
     * The instantiated DBMS.
     */
    protected DBMS dbms;
    /**
     * The writer for the DBMS.
     */
    protected SQLWriter sqlWriter;
    /**
     * The interactor for the DBMS.
     */
    protected DatabaseInteractor databaseInteractor;

    @Override
    protected void task() {
        // Instantiate fields from parameters
        instantiateParameters();

        // Generate test suite and apply to original schema
        TestSuite suite = generateTestSuite();
        List<MixedPair<TestCase, TestCaseResult>> originalResults = executeTestSuite(schema, suite);

        // Generate mutants and apply test suite
        List<Mutant<Schema>> mutants = generateMutants();
        int killed = 0;
        List<Mutant<Schema>> liveMutants = new ArrayList<>();
        for (Mutant<Schema> mutant : mutants) {
            List<MixedPair<TestCase, TestCaseResult>> mutantResults = executeTestSuite(mutant.getMutatedArtefact(), suite);
            if (!originalResults.equals(mutantResults)) {
                killed++;
            } else {
                liveMutants.add(mutant);
            }
        }

        // Write results
        CSVResult result = new CSVResult();
        result.addValue("dbms", databaseConfiguration.getDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("criterion", criterion);
        result.addValue("reuse", reuse);
        result.addValue("tests", suite.getNumTestCases());
        result.addValue("inserts", suite.getNumInserts());
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("scorenumerator", killed);
        result.addValue("scoredenominator", mutants.size());
        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);

        if (printLive) {
            for (Mutant<Schema> mutant : liveMutants) {
                System.out.println("Alive: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
            }
        }
    }

    /**
     * Instantiates the DBMS class, SQL writer and interactor.
     */
    private void instantiateParameters() {
        // Get the required DBMS class, writer and interactor
        try {
            dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        sqlWriter = dbms.getSQLWriter();
        databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        // Get the required schema class
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Generates the test suite according to the algorithm and criterion.
     *
     * @return The test suite
     */
    private TestSuite generateTestSuite() {
        // Initialise test case generator
        final SearchBasedTestCaseGenerationAlgorithm testCaseGenerator
                = new SearchBasedTestCaseGenerationAlgorithm(
                        SearchFactory.avsDefaults(0L, 100000));
        TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                CriterionFactory.instantiate(criterion),
                dbms,
                testCaseGenerator,
                reuse
        );
        // Generate suite and return
        return generator.generate();
    }

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    private List<MixedPair<TestCase, TestCaseResult>> executeTestSuite(Schema schema, TestSuite suite) {
        if (!useDelete) {
            TestCaseExecutor caseExecutor = new TestCaseExecutor(schema, dbms, databaseInteractor);
            TestSuiteExecutor suiteExecutor = new TestSuiteExecutor();
            return suiteExecutor.executeTestSuite(caseExecutor, suite);
        } else {
            TestCaseExecutor caseExecutor = new DeletingTestCaseExecutor(schema, dbms, databaseInteractor);
            TestSuiteExecutor suiteExecutor = new DeletingTestSuiteExecutor();
            return suiteExecutor.executeTestSuite(caseExecutor, suite);
        }
    }

    /**
     * Generates mutants of the instantiated schema using the named pipeline.
     *
     * @return The mutants
     */
    private List<Mutant<Schema>> generateMutants() {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new MutationAnalysis().run(args);
    }

}
