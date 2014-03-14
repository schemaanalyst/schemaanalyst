package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.UniqueColumnRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

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
