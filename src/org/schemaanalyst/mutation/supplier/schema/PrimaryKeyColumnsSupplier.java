package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

public class PrimaryKeyColumnsSupplier extends TableIntermediaryIteratingSupplier<List<Column>> {

    @Override
    protected List<Column> getComponentFromIntermediary(Schema schema, Table table) {
        PrimaryKeyConstraint primaryKeyConstraint = 
                schema.getPrimaryKeyConstraint(table);

        return (primaryKeyConstraint == null) 
                ? new ArrayList<Column>()
                : primaryKeyConstraint.getColumns();

    }

    @Override
    public void putComponentBackInIntermediary(Table table, List<Column> columns) {
        if (columns.size() == 0) {
            currentDuplicate.removePrimaryKeyConstraint(table);
        } else {
            if (currentDuplicate.hasPrimaryKeyConstraint(table)) {
                PrimaryKeyConstraint primaryKeyConstraint = currentDuplicate
                        .getPrimaryKeyConstraint(table);
                primaryKeyConstraint.setColumns(columns);
            } else {
                currentDuplicate.createPrimaryKeyConstraint(table,
                        columns);
            }
        }
    }
}
