package org.schemaanalyst.mutation.analysis.executor;

import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.alters.technique.AltersTechniqueFactory;
import org.schemaanalyst.mutation.analysis.executor.alters.testcase.AltersTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.alters.testsuite.AltersTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerationReport;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.monitoring.Timing;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class MutationAnalysisAlters extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    /**
     * The coverage criterion to use to generate data.
     */
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion = "CondAICC";
    /**
     * The data generator to use.
     */
    @Parameter("The data generator to use.")
    protected String dataGenerator = "avsDefaults";
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
     * Which mutation analysis technique to use.
     */
    @Parameter("Which mutation analysis technique to use.")
    protected String technique = "alters";
    /**
     * Whether to use transactions with this technique (if possible).
     */
    @Parameter("Whether to use transactions with this technique (if possible).")
    protected boolean useTransactions = false;
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
    /**
     * The report produced when generating the test suite.
     */
    private TestSuiteGenerationReport generationReport;

    @Override
    protected void task() {
        // Instantiate fields from parameters
        instantiateParameters();

        // Start timing
        StopWatch totalTime = new StopWatch();
        StopWatch testGenerationTime = new StopWatch();
        StopWatch mutantGenerationTime = new StopWatch();
        StopWatch originalResultsTime = new StopWatch();
        StopWatch mutationAnalysisTime = new StopWatch();
        totalTime.start();

        // Generate test suite and mutants, apply mutation analysis technique
        final TestSuite suite = Timing.timedTask(new Callable<TestSuite>() {
            @Override
            public TestSuite call() throws Exception {
                return generateTestSuite();
            }
        }, testGenerationTime);
        final List<Mutant<Schema>> mutants = Timing.timedTask(new Callable<List<Mutant<Schema>>>() {
            @Override
            public List<Mutant<Schema>> call() throws Exception {
                return generateMutants();
            }
        }, mutantGenerationTime);
        final TestSuiteResult originalResults = Timing.timedTask(new Callable<TestSuiteResult>() {
            @Override
            public TestSuiteResult call() throws Exception {
                return executeTestSuite(schema, suite);
            }
        }, originalResultsTime);

        final Technique mutTechnique = instantiateTechnique(schema, mutants, suite, dbms, databaseInteractor);
        AnalysisResult analysisResult = Timing.timedTask(new Callable<AnalysisResult>() {
            @Override
            public AnalysisResult call() throws Exception {
                return mutTechnique.analyse(originalResults);
            }
        }, mutationAnalysisTime);

        // Stop timing
        totalTime.stop();

        // Write results
        CSVResult result = new CSVResult();
        result.addValue("dbms", databaseConfiguration.getDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("criterion", criterion);
        result.addValue("datagenerator", dataGenerator);
        result.addValue("coverage", generationReport.coverage());
        //TODO: Include the coverage according to the comparison coverage criterion
        result.addValue("evaluations", generationReport.getNumDataEvaluations(false));
        result.addValue("tests", suite.getTestCases().size());
        //TODO: Include the number of insert statements
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("scorenumerator", analysisResult.getKilled().size());
        result.addValue("scoredenominator", mutants.size());
        result.addValue("technique", "alters_" + technique);
        result.addValue("transactions", useTransactions);
        result.addValue("testgenerationtime", testGenerationTime.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationTime.getTime());
        result.addValue("originalresultstime", originalResultsTime.getTime());
        result.addValue("mutationanalysistime", mutationAnalysisTime.getTime());
        result.addValue("timetaken", totalTime.getTime());

        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);

        if (printLive) {
            for (Mutant<Schema> mutant : analysisResult.getLive()) {
                System.out.println("Alive: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
            }
        }
    }

    /**
     * Instantiates the DBMS class, SQL writer and interactor.
     */
    private void instantiateParameters() {
        // Get the required DBMS class, writer and interactor
        dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        sqlWriter = dbms.getSQLWriter();
        databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        // Get the required schema class
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Technique instantiateTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        return AltersTechniqueFactory.instantiate(technique, schema, mutants, testSuite, dbms, databaseInteractor, dataGenerator, criterion, randomseed);
    }

    /**
     * Generates the test suite according to the algorithm and criterion.
     *
     * @return The test suite
     */
    private TestSuite generateTestSuite() {
        // Initialise from factories
        final DataGenerator dataGen = DataGeneratorFactory.instantiate(dataGenerator, randomseed, 100000);
        final TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schema, dbms).generateRequirements();

        // Filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // Construct generator
        final TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                testRequirements,
                dbms.getValueFactory(),
                dataGen
        );

        // Generate suite
        final TestSuite testSuite = generator.generate();
        generationReport = generator.getTestSuiteGenerationReport();
        //TODO: Include the coverage according to the comparison coverage criterion

        return testSuite;
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

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    private TestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        AltersTestCaseExecutor testCaseExecutor = new AltersTestCaseExecutor(schema, databaseInteractor, sqlWriter);
        AltersTestSuiteExecutor testSuiteExecutor = new AltersTestSuiteExecutor();
        TestSuiteResult testSuiteResult = testSuiteExecutor.executeTestSuite(testCaseExecutor, suite);
        return testSuiteResult;
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new MutationAnalysisAlters().run(args);
    }
}
