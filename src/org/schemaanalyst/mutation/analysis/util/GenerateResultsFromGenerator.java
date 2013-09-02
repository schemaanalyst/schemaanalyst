/*
 */
package org.schemaanalyst.mutation.analysis.util;


import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.ConstraintCovererFactory;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.GoalReport;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchConstraintCoverer;
import org.schemaanalyst.datageneration.search.datainitialization.NoDataInitialization;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;

/**
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
    protected String datagenerator = "alternatingValue";

    @Override
    public List<String> getInserts() {
        List<String> insertStms = new ArrayList<>();
        DataGenerator dataGenerator = constructDataGenerator(schema, dbms);
        CoverageReport coverageReport = dataGenerator.generate();
        for (GoalReport goalReport : coverageReport.getSuccessfulGoalReports()) {
            List<String> stmts = sqlWriter.writeInsertStatements(schema, goalReport.getData());
            insertStms.addAll(stmts);
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
    private DataGenerator constructDataGenerator(Schema schema, DBMS dbms) {
        return ConstraintCovererFactory.alternatingValueDefaults(schema, dbms, randomprofile, randomseed, maxevaluations);
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
