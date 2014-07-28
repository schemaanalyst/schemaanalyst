package org.schemaanalyst.testgeneration.coveragecriterion_old;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.Requirements;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.UniqueColumnRequirementsGenerator;

/**
 * Created by phil on 24/02/2014.
 */
public class UniqueColumnCACCoverage extends CoverageCriterion {

    public UniqueColumnCACCoverage() {
        super("Unique column CAC coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        return new UniqueColumnRequirementsGenerator(schema, table).generateRequirements();
    }
}
