package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.ExpressionRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.MultiColumnConstraintRACRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class ConstraintRACCoverage extends Criterion {

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            MultiColumnConstraintRACRequirementsGenerator generator = new MultiColumnConstraintRACRequirementsGenerator(schema, table, primaryKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            MultiColumnConstraintRACRequirementsGenerator generator = new MultiColumnConstraintRACRequirementsGenerator(schema, table, uniqueConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            MultiColumnConstraintRACRequirementsGenerator generator = new MultiColumnConstraintRACRequirementsGenerator(schema, table, foreignKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            ExpressionRequirementsGenerator generator = new ExpressionRequirementsGenerator(schema, table, checkConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        return requirements;
    }
}
