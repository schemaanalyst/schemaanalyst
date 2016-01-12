package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Supplies column pairs from
 * from a {@link org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint}.
 * @author Phil McMinn
 *
 */
public class ForeignKeyColumnSupplier extends
        SolitaryComponentSupplier<ForeignKeyConstraint, List<Pair<Column>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void putComponentBackInDuplicate(List<Pair<Column>> columnPairs) {
        List<Column> columns = new ArrayList<>();
        List<Column> referenceColumns = new ArrayList<>();

        for (Pair<Column> columnPair : columnPairs) {
            columns.add(columnPair.getFirst());
            referenceColumns.add(columnPair.getSecond());
        }

        currentDuplicate.setColumns(columns);
        currentDuplicate.setReferenceColumns(referenceColumns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Pair<Column>> getComponent(ForeignKeyConstraint foreignKeyConstraint) {
        List<Column> columns = foreignKeyConstraint.getColumns();
        List<Column> referenceColumns = foreignKeyConstraint.getReferenceColumns();

        List<Pair<Column>> pairedColumns = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            pairedColumns.add(new Pair<>(columns.get(i), referenceColumns.get(i)));
        }

        return pairedColumns;
    }
}
