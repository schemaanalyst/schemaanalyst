package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.CheckConstraintRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.ConstraintRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.MultiColumnConstraintRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.NotNullConstraintRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 05/02/2014.
 */
public class ConstraintCACCoverage extends Criterion {

    public ConstraintCACCoverage() {
        super("Constraint CAC coverage");
    }

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, primaryKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, uniqueConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintRequirementsGenerator(schema, foreignKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            CheckConstraintRequirementsGenerator generator = new CheckConstraintRequirementsGenerator(schema, checkConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            NotNullConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, notNullConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        return requirements;
    }
}
