/*
 */
package org.schemaanalyst.mutation.analysis.util;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.datageneration.ConstraintCovererFactory;
import org.schemaanalyst.datageneration.ConstraintGoal;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.TestCase;
import org.schemaanalyst.datageneration.TestSuite;
import org.schemaanalyst.datageneration.search.SearchConstraintCoverer;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
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
    /**
     * If a report should be written containing costs, if possible.
     */
    @Parameter(valueAsSwitch = "true")
    protected boolean writeReport = false;
    /**
     * Where to write the costs report.
     */
    @Parameter
    protected String reportLocation = "results/generationCosts.dat";
    private StopWatch stopWatch;
    

    @Override
    public List<MixedPair<String,Boolean>> getInserts() {
        List<MixedPair<String,Boolean>> insertStms = new ArrayList<>();
        DataGenerator<ConstraintGoal> dataGenerator = constructDataGenerator(schema, dbms);
        stopWatch = new StopWatch();
        stopWatch.start();
        TestSuite<ConstraintGoal> testSuite = dataGenerator.generate();
        stopWatch.stop();
        int satisfyInserts = 0;
        int negateInserts = 0;
        int unknownInserts = 0;
        int evaluations = 0;
        int restarts = 0;
        for (TestCase<ConstraintGoal> testCase : testSuite.getUsefulTestCases()) {
            Boolean satisfying = null;
            if (!testCase.getCoveredElements().isEmpty()) {
                satisfying = testCase.getCoveredElements().get(0).getSatisfy();
            }
            for (String stmt : sqlWriter.writeInsertStatements(schema, testCase.getData())) {
                MixedPair<String, Boolean> pair = new MixedPair<>(stmt, satisfying);
                insertStms.add(pair);
                if (satisfying == null) {
                    unknownInserts++;
                } else if (satisfying) {
                    satisfyInserts++;
                } else {
                    negateInserts++;
                }
            }
        }
        for (TestCase<ConstraintGoal> testCase : testSuite.getTestCases()) {
            evaluations += testCase.getNumEvaluations();
            restarts += testCase.getNumRestarts();
        }
        if (writeReport) {
            writeReport(insertStms, satisfyInserts, negateInserts, unknownInserts, evaluations, restarts);
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
        return ConstraintCovererFactory.instantiate(datagenerator, schema, dbms, randomprofile, randomseed, maxevaluations, satisfyrows, negaterows);
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

    private void writeReport(List<MixedPair<String,Boolean>> insertStmts, int satisfyInserts, int negateInserts, int unknownInserts, int numEvaluations, int numRestarts) {
        CSVResult result = new CSVResult();
        result.addValue("casestudy", casestudy);
        result.addValue("dataGenerator", this.datagenerator);
        result.addValue("seed", randomseed);
        result.addValue("randomprofile", randomprofile);
        result.addValue("satisfyrows", satisfyrows);
        result.addValue("negaterows", negaterows);
        result.addValue("evaluations", numEvaluations);
        result.addValue("restarts", numRestarts);
        result.addValue("inserts", insertStmts.size());
        result.addValue("satisfyinserts", satisfyInserts);
        result.addValue("negateinserts", negateInserts);
        result.addValue("unknowninserts", unknownInserts);
        result.addValue("timetaken", stopWatch.getTime());
        CSVWriter writer = new CSVFileWriter(reportLocation, ",");
        writer.write(result);
    }
}
