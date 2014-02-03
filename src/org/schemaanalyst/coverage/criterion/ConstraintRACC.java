package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.coverage.criterion.requirements.*;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.isNotNull;

/**
 * Created by phil on 31/01/2014.
 */
public class ConstraintRACC extends Criterion {

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        /*
        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, primaryKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, uniqueConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            MatchRequirementsGenerator generator = new MatchRequirementsGenerator(schema, table, foreignKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints()) {
            ExpressionRequirementsGenerator generator = new ExpressionRequirementsGenerator(schema, table, checkConstraint);
            requirements.addAll(generator.generateRequirements());
        }
        */

        return requirements;
    }
}
