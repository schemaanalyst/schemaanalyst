package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.util.List;

/**
 * Created by phil on 27/07/2014.
 */
public abstract class IntegrityConstraintCriterion extends CoverageCriterion {

    protected TestRequirementIDGenerator testRequirementIDGenerator;
    protected Schema schema;
    protected TestRequirements testRequirements;
    protected ConstraintSupplier constraintSupplier;

    public IntegrityConstraintCriterion(Schema schema,
                                        TestRequirementIDGenerator testRequirementIDGenerator,
                                        ConstraintSupplier constraintSupplier) {
        this.schema = schema;
        this.testRequirementIDGenerator = testRequirementIDGenerator;
        this.constraintSupplier = constraintSupplier;
    }

    protected List<Constraint> getConstraints(Table table) {
        return constraintSupplier.getConstraints(schema, table);
    }
}
