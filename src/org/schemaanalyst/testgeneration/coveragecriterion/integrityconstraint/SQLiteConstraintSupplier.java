package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/07/2014.
 */
public class SQLiteConstraintSupplier extends ConstraintSupplier {
    @Override
    public List<Constraint> getConstraints(Schema schema, Table table) {
        List<Constraint> constraints = new ArrayList<>();

        // convert PRIMARY KEY constraints to UNIQUE constraints for SQLite, since this is their behaviour
        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            constraints.add(new UniqueConstraint(table, primaryKeyConstraint.getColumns()));
        }

        constraints.addAll(schema.getNotNullConstraints(table));
        constraints.addAll(schema.getUniqueConstraints(table));
        constraints.addAll(schema.getForeignKeyConstraints(table));
        constraints.addAll(schema.getCheckConstraints(table));
        return constraints;
    }
}
