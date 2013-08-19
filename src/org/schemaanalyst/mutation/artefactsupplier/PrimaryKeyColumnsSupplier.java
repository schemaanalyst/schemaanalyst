package org.schemaanalyst.mutation.artefactsupplier;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class PrimaryKeyColumnsSupplier extends TableIteratingSupplier<List<Column>> {

    public PrimaryKeyColumnsSupplier(Schema schema) {
        super(schema);
    }

    @Override
    protected List<Column> getComponentFromIntermediary(Schema schema, Table table) {
        PrimaryKeyConstraint primaryKeyConstraint = 
                schema.getPrimaryKeyConstraint(table);

        return (primaryKeyConstraint == null) 
                ? new ArrayList<Column>()
                : primaryKeyConstraint.getColumns();

    }

    @Override
    public void putComponentBackInDuplicate(List<Column> columns) {
        if (!haveCurrent()) {
            throw new MutationException("Cannot put component back in duplicate when there is no current duplicate");
        }
        
        Table table = getDuplicateIntermediary();

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
