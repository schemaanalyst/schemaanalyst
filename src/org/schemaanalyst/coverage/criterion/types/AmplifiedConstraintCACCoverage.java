package org.schemaanalyst.coverage.criterion.types;

import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.requirements.*;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

/**
 * Created by phil on 31/01/2014.
 */
public class AmplifiedConstraintCACCoverage extends Criterion {

    public AmplifiedConstraintCACCoverage() {
        super("Amplified constraint CAC coverage");
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        Requirements requirements = new Requirements();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, primaryKeyConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, uniqueConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
            ConstraintRequirementsGenerator generator = new MultiColumnConstraintCACRequirementsGenerator(schema, foreignKeyConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (CheckConstraint checkConstraint : schema.getCheckConstraints(table)) {
            ConstraintRequirementsGenerator generator = new CheckConstraintCACRequirementsGenerator(schema, checkConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
            ConstraintRequirementsGenerator generator = new NotNullConstraintRequirementsGenerator(schema, notNullConstraint);
            requirements.addPredicates(generator.generateRequirements());
        }

        return requirements;
    }
}
