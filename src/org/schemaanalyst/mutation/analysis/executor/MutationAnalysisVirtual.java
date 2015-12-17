
package org.schemaanalyst.mutation.analysis.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.VirtualTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.VirtualTestSuiteResult;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
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

/**
 * An alternative implementation of mutation analysis, using the
 * {@link VirtualTestSuiteExecutor}, inserting data from a {@link TestSuite}.
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy")
public class MutationAnalysisVirtual extends Runner {
    
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
     * The report produced when generating the test suite.
     */
    private TestSuiteGenerationReport generationReport;
    
    private static final Logger LOGGER = Logger.getLogger(MutationAnalysisVirtual.class.getName());
    
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
        final VirtualTestSuiteResult originalResults = Timing.timedTask(new Callable<VirtualTestSuiteResult>(){
            @Override
            public VirtualTestSuiteResult call() throws Exception {
                return executeTestSuite(schema, suite);
            }
        }, originalResultsTime);
        
        final AnalysisResult analysisResult = Timing.timedTask(new Callable<AnalysisResult>() {
            @Override
            public AnalysisResult call() throws Exception {
                return analyse(suite, mutants, originalResults);
            }
        },mutationAnalysisTime);
        
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
        result.addValue("technique", "virtual");
        result.addValue("transactions", "false");
        result.addValue("testgenerationtime", testGenerationTime.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationTime.getTime());
        result.addValue("originalresultstime", originalResultsTime.getTime());
        result.addValue("mutationanalysistime", mutationAnalysisTime.getTime());
        result.addValue("timetaken", totalTime.getTime());
        
        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);
    }
    
    /**
     * Instantiates the DBMS class, SQL writer and interactor.
     */
    protected void instantiateParameters() {
        // Get the required DBMS class, writer and interactor
        dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());

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
    protected TestSuite instantiateTestSuite() {
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
    protected List<Mutant<Schema>> generateMutants() {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }
    
    protected VirtualTestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        VirtualTestCaseExecutor caseExecutor = new VirtualTestCaseExecutor(schema, dbms);
        VirtualTestSuiteExecutor suiteExecutor = new VirtualTestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }
    
    protected AnalysisResult analyse(TestSuite suite, List<Mutant<Schema>> mutants, VirtualTestSuiteResult originalResult) {
        AnalysisResult result = new AnalysisResult();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            VirtualTestSuiteResult mutantResult = executeTestSuite(mutantSchema, suite);
            if (Objects.equals(originalResult, mutantResult)) {
                result.addLive(mutant);
            } else {
                result.addKilled(mutant);
            }
        }
        return result;
    }
    
    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new MutationAnalysisVirtual().run(args);
    }

}
