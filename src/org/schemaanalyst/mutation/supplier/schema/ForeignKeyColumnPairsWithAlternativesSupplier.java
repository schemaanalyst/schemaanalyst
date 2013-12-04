/*
 */
package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

/**
 * <p>
 * Supplies pairs of columns from a {@link ForeignKeyConstraint} with possible
 * alternative pairs of columns for that key.
 * </p>
 *
 * <p>
 * Note that this will not allow the creation of duplicated columns in a foreign
 * key, e.g. (a, b) references (b, c).
 * </p>
 *
 * @author Chris J. Wright
 */
public class ForeignKeyColumnPairsWithAlternativesSupplier extends IteratingSupplier<ForeignKeyConstraint, Pair<List<Pair<Column>>>> {

    @Override
    protected List<Pair<List<Pair<Column>>>> getComponents(ForeignKeyConstraint fkey) {
        List<Pair<List<Pair<Column>>>> components = new ArrayList<>();
        List<Pair<Column>> originals = new ArrayList<>();
        for (Pair<Column> pair : fkey.getColumnPairs()) {
            originals.addAll(Arrays.asList(pair));
        }
        List<Pair<Column>> alternatives = getAdditions(fkey);
        components.add(new Pair<>(originals, alternatives));
        return components;
    }

    private List<Pair<Column>> getAdditions(ForeignKeyConstraint fkey) {
        List<Pair<Column>> additions = new ArrayList<>();
        List<Column> localColumns = fkey.getColumns();
        List<Column> refColumns = fkey.getReferenceColumns();
        Table localTable = fkey.getTable();
        Table refTable = fkey.getReferenceTable();
        for (Column local : localTable.getColumns()) {
            if (!localColumns.contains(local)) {
                for (Column ref : refTable.getColumns()) {
                    if (!refColumns.contains(ref) && (local.getDataType().equals(ref.getDataType()))) {
                        additions.add(new Pair<>(local,ref));
                    }
                }
            }
        }
        return additions;
    }

    @Override
    public void putComponentBackInDuplicate(Pair<List<Pair<Column>>> component) {
        List<Pair<Column>> pairs = new ArrayList<>();
        pairs.addAll(component.getFirst());
        currentDuplicate.setColumnPairs(pairs);
    }
}
