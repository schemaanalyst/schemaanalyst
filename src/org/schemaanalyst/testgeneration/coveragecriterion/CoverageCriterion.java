package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;

/**
 * Created by phil on 31/01/2014.
 */
public abstract class CoverageCriterion {

    protected String name;

    public CoverageCriterion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Requirements generateRequirements(Schema schema) {
        Requirements requirements = new Requirements();
        for (Table table : schema.getTablesInOrder()) {
            requirements.addPredicates(generateRequirements(schema, table));
        }
        return requirements;
    }

    public abstract Requirements generateRequirements(Schema schema, Table table);

    public String toString() {
        return name;
    }
}
