package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 27/07/2014.
 */
public class ICMinimalConstraintSupplier extends ConstraintSupplier {

    @Override
    public List<Constraint> getConstraints(Schema schema, Table table) {

        List<Constraint> constraints = new ArrayList<>();
        List<NotNullConstraint> notNullConstraints = schema.getNotNullConstraints(table);

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            constraints.add(primaryKeyConstraint);

                List<Column> primaryKeyColumns = primaryKeyConstraint.getColumns();
                Iterator<NotNullConstraint> notNullConstraintsIterator = notNullConstraints.iterator();
                while (notNullConstraintsIterator.hasNext()) {
                    if (primaryKeyColumns.contains(notNullConstraintsIterator.next().getColumn())) {
                        notNullConstraintsIterator.remove();
                    }
                }

        }

        constraints.addAll(notNullConstraints);
        constraints.addAll(schema.getUniqueConstraints(table));
        constraints.addAll(schema.getForeignKeyConstraints(table));
        constraints.addAll(schema.getCheckConstraints(table));
        return constraints;
    }
}
