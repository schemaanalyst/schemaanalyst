package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.UniqueColumnRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.List;

/**
 * Created by phil on 24/02/2014.
 */
public class UniqueColumnCACCoverage extends Criterion {

    public UniqueColumnCACCoverage() {
        super("Unique column CAC coverage");
    }

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        return new UniqueColumnRequirementsGenerator(schema, table).generateRequirements();
    }
}
