package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.requirements.NullColumnRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 03/02/2014.
 */
public class NullColumnCoverage extends Criterion {

    public NullColumnCoverage() {
        super("Null column coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        return new NullColumnRequirementsGenerator(schema, table, false).generateRequirements();
    }
}
