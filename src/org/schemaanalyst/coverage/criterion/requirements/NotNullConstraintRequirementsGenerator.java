package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.clause.ClauseFactory;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 05/02/2014.
 */
public class NotNullConstraintRequirementsGenerator extends RequirementsGenerator {

    private Column column;

    public NotNullConstraintRequirementsGenerator(Schema schema, NotNullConstraint constraint) {
        this(schema, constraint, true);
    }

    public NotNullConstraintRequirementsGenerator(Schema schema, NotNullConstraint constraint, boolean generateFullPredicate) {
        super(schema, constraint, generateFullPredicate);
        this.column = constraint.getColumn();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        // NOT NULL is TRUE requirement (NOT NULL)
        addTrueRequirement(requirements);

        // NOT NULL is FALSE requirement (NULL)
        addFalseRequirement(requirements);

        return requirements;
    }

    private void addTrueRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test " + column + " is NOT NULL for " + constraint);
        predicate.setColumnNullStatus(table, column, false);
        requirements.add(predicate);
    }

    private void addFalseRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test " + column + " is NULL for " + constraint);
        predicate.setColumnNullStatus(table, column, true);
        requirements.add(predicate);
    }
}
