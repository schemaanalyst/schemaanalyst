/*
 */
package org.schemaanalyst.mutation.analysis.util;


import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.datageneration.ConstraintCovererFactory;
import org.schemaanalyst.datageneration.ConstraintGoal;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.TestCase;
import org.schemaanalyst.datageneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
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
@RequiredParameters("casestudy")
public class GenerateResultsFromGenerator extends GenerateResults {

    /**
     * The number of max evaluations.
     */
    @Parameter
    protected int maxevaluations = 100000;
    /**
     * The number of satisfy rows.
     */
    @Parameter
    protected int satisfyrows = 2;
    /**
     * The number of negate rows.
     */
    @Parameter
    protected int negaterows = 1;
    /**
     * The random seed.
     */
    @Parameter
    protected long randomseed = 0;
    /**
     * The random profile.
     */
    @Parameter
    protected String randomprofile = "small";
    /**
     * The data generator.
     */
    @Parameter
    protected String datagenerator = "alternatingValueDefaults";

    @Override
    public List<MixedPair<String,Boolean>> getInserts() {
        List<MixedPair<String,Boolean>> insertStms = new ArrayList<>();
        DataGenerator<ConstraintGoal> dataGenerator = constructDataGenerator(schema, dbms);
        TestSuite<ConstraintGoal> testSuite = dataGenerator.generate();
        for (TestCase<ConstraintGoal> testCase : testSuite.getUsefulTestCases()) {
            Boolean satisfying = null;
            if (!testCase.getCoveredElements().isEmpty()) {
                satisfying = testCase.getCoveredElements().get(0).getSatisfy();
            }
            for (String stmt : sqlWriter.writeInsertStatements(schema, testCase.getData())) {
                MixedPair<String, Boolean> pair = new MixedPair<>(stmt, satisfying);
                insertStms.add(pair);
            }
        }
        return insertStms;
    }

    /**
     * Creates a data generator for the given Schema and ValueFactory.
     *
     * @param schema The schema in use.
     * @param dbms The DBMS in use.
     * @return The data generator.
     */
    private DataGenerator<ConstraintGoal> constructDataGenerator(Schema schema, DBMS dbms) {
        return ConstraintCovererFactory.instantiate(datagenerator, schema, dbms, randomprofile, randomseed, maxevaluations);
    }

    public static void main(String[] args) {
        new GenerateResultsFromGenerator().run(args);
    }

    @Override
    protected void validateParameters() {
        if (maxevaluations <= 0) {
            exitWithArgumentException("maxEvaluations must be > 0");
        }
        if (satisfyrows < 0) {
            exitWithArgumentException("satisfyRows must be >= 0");
        }
        if (negaterows < 0) {
            exitWithArgumentException("negateRows must be >= 0");
        }
        if (!randomprofile.equals("small") && !randomprofile.equals("large")) {
            exitWithArgumentException("randomProfile must be 'small' or 'large'");
        }
    }
}
