package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Supplies columns from a
 * {@link org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint},
 * along with alternative columns from the same table (not used in the
 * <tt>UNIQUE</tt> constraint) on which the constraint is defined.
 *
 * @author Phil McMinn
 *
 */
public class UniqueColumnsWithAlternativesSupplier extends
        SolitaryComponentSupplier<UniqueConstraint, Pair<List<Column>>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void putComponentBackInDuplicate(Pair<List<Column>> columnListPair) {
        // Use set to ensure no duplicates
        Set<Column> set = new LinkedHashSet<>(columnListPair.getFirst());
        List<Column> columns = new ArrayList<>(set);
        currentDuplicate.setColumns(columns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pair<List<Column>> getComponent(UniqueConstraint uniqueConstraint) {
        List<Column> columns = uniqueConstraint.getColumns();
        List<Column> alternatives = new ArrayList<>();

        for (Column column : uniqueConstraint.getTable().getColumns()) {
            if (!columns.contains(column)) {
                alternatives.add(column);
            }
        }
        return new Pair<>(columns, alternatives);
    }

}
