package org.schemaanalyst.testgeneration.coveragecriterion_old;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.NullColumnRequirementsGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.Requirements;

/**
 * Created by phil on 24/02/2014.
 */
public class NullColumnCACCoverage extends CoverageCriterion {

    public NullColumnCACCoverage() {
        super("Null column CAC coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        return new NullColumnRequirementsGenerator(schema, table).generateRequirements();
    }
}
