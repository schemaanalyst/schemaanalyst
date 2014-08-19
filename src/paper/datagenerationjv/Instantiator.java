package paper.datagenerationjv;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;

/**
 * Created by phil on 15/08/2014.
 */
public class Instantiator {

    public static Schema instantiateSchema(String schemaName) {
        try {
            return (Schema) Class.forName("parsedcasestudy."+schemaName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static CoverageCriterion instantiateCoverageCriterion(String coverageCriterionName, Schema schema, DBMS dbms) {
        return CoverageCriterionFactory.instantiateSchemaCriterion(coverageCriterionName, schema, dbms);
    }

    public static DBMS instantiateDBMS(String dbmsName) {
        return DBMSFactory.instantiate(dbmsName);
    }
}
