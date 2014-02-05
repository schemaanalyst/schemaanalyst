package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.*;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 05/02/2014.
 */
public class ConstraintCoverage extends Criterion {

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            RequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, table, primaryKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            RequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, table, uniqueConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            RequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, table, foreignKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            CheckConstraintRequirementsGenerator generator = new CheckConstraintRequirementsGenerator(schema, table, checkConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            NotNullConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, table, notNullConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        return requirements;
    }
}
