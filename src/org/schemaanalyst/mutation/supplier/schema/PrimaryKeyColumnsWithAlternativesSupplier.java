package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.util.Pair;

public class PrimaryKeyColumnsWithAlternativesSupplier extends TableIntermediaryIteratingSupplier<Pair<List<Column>>> {

    @Override
    protected Pair<List<Column>> getComponentFromIntermediary(Schema schema, Table table) {
        PrimaryKeyConstraint primaryKeyConstraint = 
                schema.getPrimaryKeyConstraint(table);

        List<Column> constraintColumns = (primaryKeyConstraint == null) ? new ArrayList<Column>()
                : primaryKeyConstraint.getColumns();
        List<Column> alternativeColumns = new ArrayList<>();

        for (Column tableColumn : table.getColumns()) {
            if (!constraintColumns.contains(tableColumn)) {
                alternativeColumns.add(tableColumn);
            }
        }

        return new Pair<>(constraintColumns, alternativeColumns);
    }

    @Override
    public void putComponentBackInIntermediary(Table table, Pair<List<Column>> columnListPair) {
        // the alternativeColumns (columnListPair.getSecond()) are ignored here
        List<Column> constraintColumns = columnListPair.getFirst();

        if (constraintColumns.size() == 0) {
            currentDuplicate.removePrimaryKeyConstraint(table);
        } else {
            if (currentDuplicate.hasPrimaryKeyConstraint(table)) {
                PrimaryKeyConstraint primaryKeyConstraint = currentDuplicate
                        .getPrimaryKeyConstraint(table);
                primaryKeyConstraint.setColumns(constraintColumns);
            } else {
                currentDuplicate.createPrimaryKeyConstraint(table,
                        constraintColumns);
            }
        }
    }
}
