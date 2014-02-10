package org.schemaanalyst.mutation.analysis.executor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.MultiCriterion;
import org.schemaanalyst.coverage.criterion.types.ConstraintCoverage;
import org.schemaanalyst.coverage.criterion.types.ConstraintRACCoverage;
import org.schemaanalyst.coverage.criterion.types.NullColumnCoverage;
import org.schemaanalyst.coverage.criterion.types.UniqueColumnCoverage;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
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
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy criterion")
public class MutationAnalysis extends Runner {

    @Parameter("The name of the schema to use.")
    protected String casestudy;
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;
    @Parameter
    protected int maxevaluations = 100000;
    @Parameter
    protected long randomseed = 0;
    @Parameter("Whether to re-use test cases.")
    protected boolean reuse = false;
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithRemovers";

    protected Schema schema;
    protected DBMS dbms;
    protected SQLWriter sqlWriter;
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
        List<Mutant<Schema>> killedMutants = new ArrayList<>();
        for (Mutant<Schema> mutant : mutants) {
            List<MixedPair<TestCase, TestCaseResult>> mutantResults = executeTestSuite(mutant.getMutatedArtefact(), suite);
            if (!originalResults.equals(mutantResults)) {
                killedMutants.add(mutant);
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
        result.addValue("mutationpipeline", mutationPipeline.replaceAll(",", "|"));
        result.addValue("scorenumerator", killed);
        result.addValue("scoredenominator", mutants.size());
        new CSVFileWriter(locationsConfiguration.getResultsDir() + File.separator + "newmutationanalysis.dat").write(result);
        
        // Print mutants (debug)
        for (Mutant<Schema> mutant : killedMutants) {
//            System.out.println("Killed: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
        }
        for (Mutant<Schema> mutant : liveMutants) {
            System.out.println("Alive: " + mutant.getSimpleDescription() + " (" + mutant.getDescription() + ")");
        }
    }

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

    private TestSuite generateTestSuite() {
        // Initialise test case generator
        final SearchBasedTestCaseGenerationAlgorithm testCaseGenerator
                = new SearchBasedTestCaseGenerationAlgorithm(
                        SearchFactory.avsDefaults(0L, 100000));
        boolean oneTestPerRequirement = !reuse;
        TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                instantiateCriterion(criterion),
                dbms,
                testCaseGenerator,
                oneTestPerRequirement
        );
        // Generate suite and return
        return generator.generate();
    }

    private List<MixedPair<TestCase, TestCaseResult>> executeTestSuite(Schema schema, TestSuite suite) {
        TestCaseExecutor caseExecutor = new TestCaseExecutor(schema, dbms, databaseInteractor);
        TestSuiteExecutor suiteExecutor = new TestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }

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

    protected static Criterion instantiateCriterion(String criterion) {
        final Criterion result;
        switch (criterion) {
            case "constraint":
                result = new ConstraintCoverage();
                break;
            case "constraintnullunique":
                result = new MultiCriterion(
                        new ConstraintCoverage(),
                        new NullColumnCoverage(),
                        new UniqueColumnCoverage()
                );
                break;
            case "rac":
                result = new ConstraintRACCoverage();
                break;
            case "racnullunique":
                result = new MultiCriterion(
                        new ConstraintRACCoverage(),
                        new NullColumnCoverage(),
                        new UniqueColumnCoverage()
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown criterion: " + criterion);
        }
        return result;
    }

}
