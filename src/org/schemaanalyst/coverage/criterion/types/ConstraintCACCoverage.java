package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.requirements.*;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

/**
 * Created by phil on 05/02/2014.
 */
public class ConstraintCACCoverage extends Criterion {

    public ConstraintCACCoverage() {
        super("Constraint CAC coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        Requirements requirements = new Requirements();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, primaryKeyConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, uniqueConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, foreignKeyConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            CheckConstraintRequirementsGenerator generator = new CheckConstraintRequirementsGenerator(schema, checkConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            NotNullConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, notNullConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        return requirements;
    }
}
