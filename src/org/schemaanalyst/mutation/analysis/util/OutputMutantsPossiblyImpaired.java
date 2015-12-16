package org.schemaanalyst.mutation.analysis.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.VirtualTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.VirtualTestSuiteResult;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy dbms")
public class OutputMutantsPossiblyImpaired extends Runner {

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
    protected String mutationPipeline = "AllOperatorsNormalisedWithClassifiers";
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
     * The name of the DBMS to use.
     */
    @Parameter("The name of the DBMS to use.")
    protected String dbms;
    /**
     * The instantiated DBMS.
     */
    protected DBMS dbmsInstance;

    private static final Logger LOGGER = Logger.getLogger(OutputMutantsPossiblyImpaired.class.getName());

    @Override
    protected void task() {
        // Instantiate fields from parameters
        instantiateParameters();

        // Generate test suite and mutants, apply mutation analysis technique
        final TestSuite suite = instantiateTestSuite();
        final List<Mutant<Schema>> mutants = generateMutants();
        final List<Mutant<Schema>> notCovered = findNotCovered(suite, mutants);
        
        System.out.println("mutants.size() = " + mutants.size());
        System.out.println("notCovered.size() = " + notCovered.size());
        for (Mutant<Schema> mutant : mutants) {
            System.out.println(mutant.getIdentifier() + ": " + mutant.getMutantType());
        }

        for (Mutant<Schema> mutant : notCovered) {
            System.out.println(mutant.getIdentifier() + ": " + mutant.toString());
        }
        
//        // Write results
//        CSVResult result = new CSVResult();
//        result.addValue("dbms", databaseConfiguration.getDbms());
//        result.addValue("casestudy", casestudy);
//        result.addValue("criterion", inputTestSuite == null ? criterion : "NA");
//        result.addValue("datagenerator", inputTestSuite == null ? dataGenerator : "NA");
//        result.addValue("randomseed", randomseed);
//        result.addValue("testsuitefile", inputTestSuite == null ? "NA" : Paths.get(inputTestSuite).getFileName());
//        result.addValue("coverage", inputTestSuite == null ? generationReport.coverage() : "NA");
//        //TODO: Include the coverage according to the comparison coverage criterion
//        result.addValue("evaluations", inputTestSuite == null ? generationReport.getNumDataEvaluations(false) : "NA");
//        result.addValue("tests", suite.getTestCases().size());
//        //TODO: Include the number of insert statements
//        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
//        result.addValue("scorenumerator", analysisResult.getKilled().size());
//        result.addValue("scoredenominator", mutants.size());
//        result.addValue("technique", "virtual");
//        result.addValue("transactions", "false");
//        result.addValue("testgenerationtime", testGenerationTime.getTime());
//        result.addValue("mutantgenerationtime", mutantGenerationTime.getTime());
//        result.addValue("originalresultstime", originalResultsTime.getTime());
//        result.addValue("mutationanalysistime", mutationAnalysisTime.getTime());
//        result.addValue("timetaken", totalTime.getTime())
//        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);
    }

    /**
     * Instantiates the DBMS class and schema.
     */
    private void instantiateParameters() {
        // Get the required DBMS class, writer and interactor
        dbmsInstance = DBMSFactory.instantiate(dbms);

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
        final TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schema, dbmsInstance).generateRequirements();

        // Filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // Construct generator
        final TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                testRequirements,
                dbmsInstance.getValueFactory(),
                dataGen
        );

        // Generate suite
        final TestSuite testSuite = generator.generate();

        // Ensure the test suite contains no warnings
        verifyTestSuite(testSuite);

        return testSuite;
    }

    private void verifyTestSuite(TestSuite testSuite) {
        org.schemaanalyst.testgeneration.TestCaseExecutor executor = new org.schemaanalyst.testgeneration.TestCaseExecutor(
                schema,
                dbmsInstance,
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

    private VirtualTestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        VirtualTestCaseExecutor caseExecutor = new VirtualTestCaseExecutor(schema, dbmsInstance);
        VirtualTestSuiteExecutor suiteExecutor = new VirtualTestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }

    private List<Mutant<Schema>> findNotCovered(TestSuite suite, List<Mutant<Schema>> mutants) {
        List<Mutant<Schema>> tablesNotCovered = new ArrayList<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            VirtualTestSuiteResult mutantResult = executeTestSuite(mutantSchema, suite);
            if(!aretablesCovered(mutant, mutantResult)) {
                tablesNotCovered.add(mutant);
            }
        }
        return tablesNotCovered;
    }

    private boolean aretablesCovered(Mutant<Schema> mutant, VirtualTestSuiteResult mutantResult) {
        Schema mutantSchema = mutant.getMutatedArtefact();
        LOGGER.log(Level.FINE, "mutant.getIdentifier() = {0}", mutant.getIdentifier());
        Set<Table> tables = new HashSet<>(mutantSchema.getTables());
        for (MixedPair<TestCase, VirtualTestCaseResult> pair : mutantResult.getResults()) {
            Data state = pair.getFirst().getState();
            Iterator<Boolean> iter = pair.getSecond().getSuccessful().iterator();
            for (Table table : state.getTables()) {
                List<Row> rows = state.getRows(table);
                for (Row row : rows) {
                    boolean accepted = iter.next();
                    LOGGER.log(Level.FINE, "{0} into {1} ({2})", new Object[]{row, table, accepted});
                    if (accepted) {
                        tables.remove(table);
                        if (tables.isEmpty()) {
                            LOGGER.log(Level.FINE, "All tables covered for mutant {0}", mutant.getIdentifier());
                            return true;
                        }
                    }
                }
            }
            Data data = pair.getFirst().getData();
            for (Table table : data.getTables()) {
                List<Row> rows = data.getRows(table);
                for (Row row : rows) {
                    boolean accepted = iter.next();
                    LOGGER.log(Level.FINE, "{0} into {1} ({2})", new Object[]{row, table, accepted});
                    if (accepted) {
                        tables.remove(table);
                        if (tables.isEmpty()) {
                            LOGGER.log(Level.FINE, "All tables covered for mutant {0}", mutant.getIdentifier());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }

    public static void main(String[] args) {
        new OutputMutantsPossiblyImpaired().run(args);
    }

}
