package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.coverage.criterion.requirements.*;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * Created by phil on 31/01/2014.
 */
public class RestrictedActiveClauseCoverage extends Criterion {
    @Override
    public Predicate generateInitialTablePredicate(Schema schema, Table table) {
        // TODO: refactor
        return new ValidNotNullRequirementsGenerator(schema, table).generateRequirements().getRequirements().get(0);
    }

    @Override
    public Requirements generateRemainingRequirements(Schema schema, Table table) {
        Requirements requirements = new Requirements();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, primaryKeyConstraint);
            Requirements primaryKeyConstraintRequirements = generator.generateRequirements();
            requirements.add(primaryKeyConstraintRequirements);
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, uniqueConstraint);
            Requirements uniqueConstraintRequirements = generator.generateRequirements();
            requirements.add(uniqueConstraintRequirements);
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, foreignKeyConstraint);
            Requirements foreignKeyConstraintRequirements = generator.generateRequirements();
            requirements.add(foreignKeyConstraintRequirements);
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints()) {
            ExpressionRequirementsGenerator generator = new ExpressionRequirementsGenerator(schema, table, checkConstraint);
            Requirements checkConstraintRequirements = generator.generateRequirements();
            requirements.add(checkConstraintRequirements);
        }

        // TO DO: cover not nulls.

        return requirements;
    }
}
