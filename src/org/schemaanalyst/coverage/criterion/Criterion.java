package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public abstract class Criterion {

    public List<Predicate> generateRequirements(Schema schema) {
        List<Predicate> requirements = new ArrayList<>();
        for (Table table : schema.getTablesInOrder()) {
            requirements.addAll(generateRequirements(schema, table));
        }
        return requirements;
    }

    public abstract List<Predicate> generateRequirements(Schema schema, Table table);
}
