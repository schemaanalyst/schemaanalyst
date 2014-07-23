package org.schemaanalyst.testgeneration.coveragecriterion_old;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.NullColumnRequirementsGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.Requirements;

/**
 * Created by phil on 03/02/2014.
 */
public class NullColumnCoverage extends CoverageCriterion {

    public NullColumnCoverage() {
        super("Null column coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        return new NullColumnRequirementsGenerator(schema, table, false).generateRequirements();
    }
}
