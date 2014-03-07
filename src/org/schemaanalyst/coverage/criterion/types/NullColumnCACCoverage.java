package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.requirements.NullColumnRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 24/02/2014.
 */
public class NullColumnCACCoverage extends Criterion {

    public NullColumnCACCoverage() {
        super("Null column CAC coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        return new NullColumnRequirementsGenerator(schema, table).generateRequirements();
    }
}
