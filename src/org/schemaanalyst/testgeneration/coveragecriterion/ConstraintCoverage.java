package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.*;

/**
 * Created by phil on 24/02/2014.
 */
public class ConstraintCoverage extends CoverageCriterion {

    public ConstraintCoverage() {
        super("Constraint coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        Requirements requirements = new Requirements();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, primaryKeyConstraint, false);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, uniqueConstraint, false);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, foreignKeyConstraint, false);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            CheckConstraintRequirementsGenerator generator = new CheckConstraintRequirementsGenerator(schema, checkConstraint, false);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            NotNullConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, notNullConstraint, false);
            requirements.addPredicates(generator.generateRequirements());
        }

        return requirements;
    }
}
