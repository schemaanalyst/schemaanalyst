package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.CheckConstraintCACRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.ConstraintRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.MultiColumnConstraintCACRequirementsGenerator;
import org.schemaanalyst.coverage.criterion.requirements.NotNullConstraintRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class AmplifiedConstraintCACCoverage extends Criterion {

    public AmplifiedConstraintCACCoverage() {
        super("Amplified constraint CAC coverage");
    }

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, primaryKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, uniqueConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, foreignKeyConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            ConstraintRequirementsGenerator generator = new CheckConstraintCACRequirementsGenerator(schema, checkConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            ConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, notNullConstraint);
            requirements.addAll(generator.generateRequirements());
        }

        return requirements;
    }
}
