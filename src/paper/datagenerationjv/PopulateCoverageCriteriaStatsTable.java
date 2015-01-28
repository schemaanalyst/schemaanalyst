package paper.datagenerationjv;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.ArrayList;
import java.util.List;

import static paper.datagenerationjv.Instantiator.instantiateCoverageCriterion;
import static paper.datagenerationjv.Instantiator.instantiateDBMS;
import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 *
 * Expects a clean coverage_criteria_stats table.
 *
 * Created by phil on 13/08/2014.
 */
public class PopulateCoverageCriteriaStatsTable {

    protected ResultsDatabase resultsDatabase;

    public void populate(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);

        List<String> schemaNames = resultsDatabase.getNames("schemas");
        for (String schemaName : schemaNames) {
            List<String> coverageCriteriaNames = new ArrayList<>();
            coverageCriteriaNames.add("APC");
            coverageCriteriaNames.add("ICC");
            coverageCriteriaNames.add("AICC");
            coverageCriteriaNames.add("CondAICC");
            coverageCriteriaNames.add("ClauseAICC");
            coverageCriteriaNames.add("UCC");
            coverageCriteriaNames.add("AUCC");
            coverageCriteriaNames.add("NCC");
            coverageCriteriaNames.add("ANCC");
            for (String coverageCriterionName : coverageCriteriaNames) {
                List<String> dbmsNames = resultsDatabase.getNames("dbmses");
                for (String dbmsName : dbmsNames) {
                    populate(schemaName, coverageCriterionName, dbmsName);
                }
            }
        }
    }

    protected void populate(String schemaName, String coverageCriterionName, String dbmsName) {
        Schema schema = instantiateSchema(schemaName);
        DBMS dbms = instantiateDBMS(dbmsName);
        CoverageCriterion coverageCriterion = instantiateCoverageCriterion(coverageCriterionName, schema, dbms);

        TestRequirements testRequirements = coverageCriterion.generateRequirements();
        int numReqs = testRequirements.size();

        testRequirements.filterInfeasible();
        int numReqsMinusInfeasible = testRequirements.size();

        testRequirements.reduce();
        int numReqsMinusDuplicates = testRequirements.size();

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dbmsName + "\", "
                    +  numReqs + ", " + numReqsMinusInfeasible + ", " +  numReqsMinusDuplicates;

        String sql = "INSERT INTO coverage_criteria_stats VALUES(" + data + ")";

        System.out.println(sql);

        resultsDatabase.executeInsert(sql);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No results database file name provided");
            System.exit(1);
        }

        new PopulateCoverageCriteriaStatsTable().populate(args[0]);
    }
}
