package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.NullColumnRequirementsGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;

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
