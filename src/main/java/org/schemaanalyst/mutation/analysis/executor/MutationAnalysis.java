package org.schemaanalyst.mutation.analysis.executor;

import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.mutation.analysis.executor.technique.TechniqueFactory;
import org.schemaanalyst.mutation.analysis.executor.testcase.DeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.DeletingTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
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
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.util.monitoring.Timing;

/**
 * An alternative implementation of mutation analysis, using the
 * {@link org.schemaanalyst.mutation.analysis.technique.Original} technique,
 * inserting data from a {@link TestSuite}.
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class MutationAnalysis extends Runner {

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
     * Whether to output live mutant data.
     */
    @Parameter("Whether to output mutant data.")
    protected boolean outputMutants = false;
    /**
     * Whether to output detailed live mutant data.
     */
    @Parameter("Whether to output mutant data (detailed report).")
    protected boolean outputMutantsDetailed = false;
    /**
     * Whether to output mutant data (detailed report v2).
     */
    @Parameter("Whether to output mutant data (detailed report v2).")
    protected boolean outputMutantsDetailed_v2 = false;
    /**
     * Which mutation analysis technique to use.
     */
    @Parameter("Which mutation analysis technique to use.")
    protected String technique = "original";
    /**
     * Whether to use transactions with this technique (if possible).
     */
    @Parameter("Whether to use transactions with this technique (if possible).")
    protected boolean useTransactions = false;
    /**
     * The location of the input test suite to load, which will be used instead
     * of generating a new test suite.
     */
    @Parameter("The location of the input test suite to load, which will be used"
            + " instead of generating a new test suite.")
    protected String inputTestSuite = null;
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

    private static final Logger LOGGER = Logger.getLogger(MutationAnalysis.class.getName());

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
                return instantiateTestSuite();
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
        result.addValue("criterion", inputTestSuite == null ? criterion : "NA");
        result.addValue("datagenerator", inputTestSuite == null ? dataGenerator : "NA");
        result.addValue("randomseed", randomseed);
        result.addValue("testsuitefile", inputTestSuite == null ? "NA" : Paths.get(inputTestSuite).getFileName());
        result.addValue("coverage", inputTestSuite == null ? generationReport.coverage() : "NA");
        //TODO: Include the coverage according to the comparison coverage criterion
        result.addValue("evaluations", inputTestSuite == null ? generationReport.getNumDataEvaluations(false) : "NA");
        result.addValue("tests", suite.getTestCases().size());
        //TODO: Include the number of insert statements
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("scorenumerator", analysisResult.getKilled().size());
        result.addValue("scoredenominator", mutants.size());
        result.addValue("technique", technique);
        result.addValue("transactions", useTransactions);
        result.addValue("testgenerationtime", testGenerationTime.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationTime.getTime());
        result.addValue("originalresultstime", originalResultsTime.getTime());
        result.addValue("mutationanalysistime", mutationAnalysisTime.getTime());
        result.addValue("timetaken", totalTime.getTime());

        //new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);
        // Changed by Abdullah from newmutationanalysis.dat --> mutationanalysis.dat
        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "mutationanalysis.dat").write(result);

        if (printLive) {
            for (Mutant<Schema> mutant : analysisResult.getLive()) {
                System.out.println("Alive: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
            }
        }

        if (outputMutants) {
            writeMutantReport(analysisResult);
        }

        if (outputMutantsDetailed) {
            writeDetailedMutantReport(analysisResult);
        }

        if (outputMutantsDetailed_v2) {
            writeDetailedMutantReport_v2(analysisResult);
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
        return TechniqueFactory.instantiate(technique, schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }

    /**
     * Generates the test suite according to the algorithm and criterion.
     *
     * @return The test suite
     */
    private TestSuite instantiateTestSuite() {
        if (inputTestSuite == null) {
            return generateTestSuite();
        } else {
            return loadTestSuite();
        }
    }

    private TestSuite generateTestSuite() {
        // Initialise from factories
        final DataGenerator dataGen = DataGeneratorFactory.instantiate(dataGenerator, randomseed, 100000, schema);
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

        // Ensure the test suite contains no warnings
        verifyTestSuite(testSuite);

        return testSuite;
    }

    private void verifyTestSuite(TestSuite testSuite) {
        org.schemaanalyst.testgeneration.TestCaseExecutor executor = new org.schemaanalyst.testgeneration.TestCaseExecutor(
                schema,
                dbms,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(testSuite);

        int numWarnings = 0;
        for (TestCase testCase : testSuite.getTestCases()) {
            Boolean result = testCase.getTestRequirement().getResult();
            Boolean dbmsResult = testCase.getLastDBMSResult();
            if (result != null && result != dbmsResult) {
                numWarnings++;
            }
        }
        if (numWarnings != 0) {
            throw new RuntimeException(String.format("TestSuite contains %s unexpected warnings", numWarnings));
        }
    }

    private TestSuite loadTestSuite() {
        try {
            FileInputStream fis = new FileInputStream(inputTestSuite);
            try (ObjectInputStream in = new ObjectInputStream(fis)) {
                TestSuite testSuite = (TestSuite) in.readObject();
                return testSuite;
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
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

    private void writeMutantReport(AnalysisResult analysisResult) {
        CSVFileWriter writer = new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "mutantreport.dat");
        UUID identifier = UUID.randomUUID();

        Map<String, Integer> aliveCount = new HashMap<>();
        Map<String, Integer> killedCount = new HashMap<>();

        for (Mutant<Schema> mutant : analysisResult.getLive()) {
            final String operator = mutant.getSimpleDescription();
            int count = 1;
            if (aliveCount.containsKey(operator)) {
                count = 1 + aliveCount.get(operator);
            }
            aliveCount.put(operator, count);
        }

        for (Mutant<Schema> mutant : analysisResult.getKilled()) {
            final String operator = mutant.getSimpleDescription();
            int count = 1;
            if (killedCount.containsKey(operator)) {
                count = 1 + killedCount.get(operator);
            }
            killedCount.put(operator, count);
        }

        Set<String> keyset = new HashSet<>(aliveCount.keySet().size() + killedCount.keySet().size());
        keyset.addAll(aliveCount.keySet());
        keyset.addAll(killedCount.keySet());

        for (String operator : keyset) {
            Integer alive = aliveCount.get(operator);
            Integer killed = killedCount.get(operator);
            alive = alive == null ? 0 : alive;
            killed = killed == null ? 0 : killed;

            CSVResult mResult = new CSVResult();
            mResult.addValue("identifier", identifier);
            mResult.addValue("dbms", databaseConfiguration.getDbms());
            mResult.addValue("casestudy", casestudy);
            mResult.addValue("criterion", inputTestSuite == null ? criterion : "NA");
            mResult.addValue("datagenerator", inputTestSuite == null ? dataGenerator : "NA");
            mResult.addValue("randomseed", randomseed);
            mResult.addValue("testsuitefile", inputTestSuite == null ? "NA" : Paths.get(inputTestSuite).getFileName());

            mResult.addValue("operator", operator);
            mResult.addValue("alive", alive);
            mResult.addValue("killed", killed);

            writer.write(mResult);
        }
    }

    private void writeDetailedMutantReport(AnalysisResult analysisResult) {
        CSVFileWriter writer = new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "detailedmutantreport_old.dat");
        UUID identifier = UUID.randomUUID();

        // Get a single collection of all mutants
        List<Mutant<Schema>> killed = analysisResult.getKilled();
        List<Mutant<Schema>> alive = analysisResult.getLive();
        ArrayList<Mutant<Schema>> mutants = new ArrayList<>(killed.size() + alive.size());
        mutants.addAll(killed);
        mutants.addAll(alive);

        // Sort into a predictable ordering
        Collections.sort(mutants, new Comparator<Mutant<Schema>>() {
            @Override
            public int compare(Mutant<Schema> o1, Mutant<Schema> o2) {
                if (o2.hashCode() > o1.hashCode()) {
                    return -1;
                } else if (o1.hashCode() > o2.hashCode()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // Build sets of killed/alive for quick lookups
        Set<Mutant<Schema>> killedSet = new HashSet<>(killed);

        for (int id = 0; id < mutants.size(); id++) {
            Mutant<Schema> mutant = mutants.get(id);
            CSVResult mResult = new CSVResult();
            mResult.addValue("identifier", identifier);
            mResult.addValue("mutant", id + 1);
            mResult.addValue("dbms", databaseConfiguration.getDbms());
            mResult.addValue("casestudy", casestudy);
            mResult.addValue("criterion", inputTestSuite == null ? criterion : "NA");
            mResult.addValue("datagenerator", inputTestSuite == null ? dataGenerator : "NA");
            mResult.addValue("randomseed", randomseed);
            mResult.addValue("testsuitefile", inputTestSuite == null ? "NA" : Paths.get(inputTestSuite).getFileName());
            mResult.addValue("operator", mutant.getSimpleDescription());
            mResult.addValue("killed", killedSet.contains(mutant) ? "true" : "false");
            writer.write(mResult);
        }
    }

    private void writeDetailedMutantReport_v2(AnalysisResult analysisResult) {
        CSVFileWriter writer = new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "detailedmutantreport.dat");
        UUID identifier = UUID.randomUUID();

        // Get a single collection of all mutants
        List<Mutant<Schema>> killed = analysisResult.getKilled();
        List<Mutant<Schema>> alive = analysisResult.getLive();
        ArrayList<Mutant<Schema>> mutants = new ArrayList<>(killed.size() + alive.size());
        mutants.addAll(killed);
        mutants.addAll(alive);

        Collections.sort(mutants, new Comparator<Mutant<Schema>>() {
            @Override
            public int compare(Mutant<Schema> o1, Mutant<Schema> o2) {
                int i1 = o1.getIdentifier();
                int i2 = o2.getIdentifier();
                if (i1 < i2) {
                    return -1;
                } else if (i1 > i2) {
                    return 1;
                } else {
                    LOGGER.log(Level.SEVERE, "Mutant list contains two mutants "
                            + "with matching identifiers. The ordering of contents"
                            + " of 'detailedmutantreport_v2.dat' may become "
                            + "unpredictable as a result.");
                    return 0;
                }
            }
        });

        // Build sets of killed/alive for quick lookups
        Set<Mutant<Schema>> killedSet = new HashSet<>(killed);

        for (Mutant<Schema> mutant : mutants) {
            CSVResult mResult = new CSVResult();
            mResult.addValue("identifier", identifier);
            mResult.addValue("mutant", mutant.getIdentifier());
            mResult.addValue("dbms", databaseConfiguration.getDbms());
            mResult.addValue("casestudy", casestudy);
            mResult.addValue("criterion", inputTestSuite == null ? criterion : "NA");
            mResult.addValue("datagenerator", inputTestSuite == null ? dataGenerator : "NA");
            mResult.addValue("randomseed", randomseed);
            mResult.addValue("testsuitefile", inputTestSuite == null ? "NA" : Paths.get(inputTestSuite).getFileName());
            mResult.addValue("operator", mutant.getSimpleDescription());
            mResult.addValue("killed", killedSet.contains(mutant) ? "true" : "false");
            writer.write(mResult);
        }
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new MutationAnalysis().run(args);
    }

}
