package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;

import java.util.List;
import java.util.Map;

/**
 * Created by phil on 13/08/2014.
 */
public abstract class ComputeUsing {

    protected ResultsDatabase resultsDatabase;

    public void compute(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);

        Map<String, Schema> schemas = resultsDatabase.getSchemas();
        for (String schemaName : schemas.keySet()) {
            computeUsing(schemaName, schemas.get(schemaName));
        }
    }

    protected void computeUsing(String schemaName, Schema schema) {
        Map<String, CoverageCriterion> coverageCriteria = resultsDatabase.getCoverageCriteria(schema);
        for (String coverageCriterionName : coverageCriteria.keySet()) {
            computeUsing(schemaName, schema, coverageCriterionName, coverageCriteria.get(coverageCriterionName));
        }
    }

    protected void computeUsing(String schemaName, Schema schema, String coverageCriterionName, CoverageCriterion coverageCriterion) {
        List<String> dataGeneratorNames = resultsDatabase.getNames("data_generators");
        for (String dataGeneratorName : dataGeneratorNames) {
            computeUsing(schemaName, schema, coverageCriterionName, coverageCriterion, dataGeneratorName);
        }
    }

    protected void computeUsing(String schemaClassName, Schema schema, String coverageCriterionClassName, CoverageCriterion coverageCriterion, String dataGeneratorName) {
    }
}
