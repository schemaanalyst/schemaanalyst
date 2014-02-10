/*
 */
package org.schemaanalyst.mutation.analysis.util;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.MultiCriterion;
import org.schemaanalyst.coverage.criterion.types.AmplifiedConstraintCACCoverage;
import org.schemaanalyst.coverage.criterion.types.ConstraintCACCoverage;
import org.schemaanalyst.coverage.criterion.types.NullColumnCoverage;
import org.schemaanalyst.coverage.criterion.types.UniqueColumnCoverage;
import org.schemaanalyst.coverage.search.SearchBasedTestCaseGenerationAlgorithm;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.datageneration.search.SearchFactory;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.tuple.MixedPair;

/**
 * <p>
 * {@link Runner} for acquiring data from a specified data generator, testing it
 * against a non-mutant schema and persisting the results to file.
 * </p>
 *
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy criterion")
public class GenerateResultsFromCoverageGenerator extends GenerateResults {

    /**
     * The number of max evaluations.
     */
    @Parameter
    protected int maxevaluations = 100000;
    @Parameter
    protected long randomseed = 0;
    @Parameter("Whether to re-use test cases.")
    protected boolean reuse = true;
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion;

    @Override
    public List<MixedPair<String, Boolean>> getInserts() {
        final List<MixedPair<String, Boolean>> insertStms = new ArrayList<>();
        final Criterion constraintCoverage = instantiateCriterion(criterion);

        // Initialise test case generator
        final SearchBasedTestCaseGenerationAlgorithm testCaseGenerator
                = new SearchBasedTestCaseGenerationAlgorithm(
                        SearchFactory.avsDefaults(0L, 100000));

        // Generate tests
        boolean oneTestPerRequirement = !reuse;
        TestSuiteGenerator generator = new TestSuiteGenerator(
                schema,
                constraintCoverage,
                new SQLiteDBMS(),
                testCaseGenerator,
                oneTestPerRequirement
        );
        TestSuite testSuite = generator.generate();
        for (TestCase testCase : testSuite.getTestCases()) {
            boolean satisfying = testCase.satisfiesOriginalPredicate();
            // Add state inserts
            for (String insert : sqlWriter.writeInsertStatements(schema, testCase.getState())) {
                MixedPair<String, Boolean> pair = new MixedPair<>(insert, satisfying);
                insertStms.add(pair);
            }
            // Add test inserts
            for (String insert : sqlWriter.writeInsertStatements(schema, testCase.getData())) {
                MixedPair<String, Boolean> pair = new MixedPair<>(insert, satisfying);
                insertStms.add(pair);
            }
        }
        
        return insertStms;
    }

    protected Criterion instantiateCriterion(String criterion) {
        final Criterion result;
        switch (criterion) {
            case "constraint":
                result = new ConstraintCACCoverage();
                break;
            case "constraintnullunique":
                result = new MultiCriterion(
                        new ConstraintCACCoverage(),
                        new NullColumnCoverage(),
                        new UniqueColumnCoverage()
                );
                break;
            case "rac":
                result = new MultiCriterion(
                        new AmplifiedConstraintCACCoverage(),
                        new NullColumnCoverage(),
                        new UniqueColumnCoverage()
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown criterion: " + criterion);
        }
        return result;
    }

    public static void main(String[] args) {
        new GenerateResultsFromCoverageGenerator().run(args);
    }

    @Override
    protected void validateParameters() {
        // Do nothing
    }
}
