package org.schemaanalyst.testgeneration.coveragecriterion_old.requirements;

import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

/**
 * Created by phil on 05/02/2014.
 */
public class NotNullConstraintRequirementsGenerator extends ConstraintRequirementsGenerator {

    private Column column;

    public NotNullConstraintRequirementsGenerator(Schema schema, NotNullConstraint constraint) {
        this(schema, constraint, true);
    }

    public NotNullConstraintRequirementsGenerator(Schema schema, NotNullConstraint constraint, boolean generateFullPredicate) {
        super(schema, constraint, generateFullPredicate);
        this.column = constraint.getColumn();
    }

    @Override
    public Requirements generateRequirements() {
        Requirements requirements = new Requirements();

        // NOT NULL is TRUE requirement (NOT NULL)
        addTrueRequirement(requirements);

        // NOT NULL is FALSE requirement (NULL)
        addFalseRequirement(requirements);

        return requirements;
    }

    private void addTrueRequirement(Requirements requirements) {
        Predicate predicate = generatePredicate("Test " + column + " is NOT NULL for " + table + "'s " + constraint);
        predicate.setColumnNullStatus(table, column, false);
        requirements.addPredicate(predicate);
    }

    private void addFalseRequirement(Requirements requirements) {
        Predicate predicate = generatePredicate("Test " + column + " is NULL for " + table + "'s " + constraint);
        predicate.setColumnNullStatus(table, column, true);
        requirements.addPredicate(predicate);
    }
}
