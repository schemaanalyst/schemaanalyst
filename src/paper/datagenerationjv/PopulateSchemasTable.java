package paper.datagenerationjv;

import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.List;

import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 15/08/2014.
 */
public class PopulateSchemasTable {

    protected ResultsDatabase resultsDatabase;

    public void populate(String resultsDatabaseFileName) {
        resultsDatabase = new ResultsDatabase(resultsDatabaseFileName);

        List<String> schemaNames = resultsDatabase.getNames("schemas");
        for (String schemaName : schemaNames) {

            Schema schema = instantiateSchema(schemaName);



        }
    }
}
