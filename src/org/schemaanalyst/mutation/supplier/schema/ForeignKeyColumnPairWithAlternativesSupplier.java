/*
 */
package org.schemaanalyst.mutation.supplier.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

/**
 * <p>
 *
 * </p>
 *
 * @author Chris J. Wright
 */
public class ForeignKeyColumnPairWithAlternativesSupplier extends IteratingSupplier<ForeignKeyConstraint, Pair<List<Pair<Column>>>> {

    Queue<List<Pair<Column>>> queue = new LinkedList<>();
    
    @Override
    protected List<Pair<List<Pair<Column>>>> getComponents(ForeignKeyConstraint fkey) {
        List<Pair<List<Pair<Column>>>> components = new ArrayList<>();
        for (Pair<Column> pair : fkey.getColumnPairs()) {
            List<Pair<Column>> original = Arrays.asList(pair);
            queue.add(original);
            List<Pair<Column>> alternatives = getAlternatives(fkey, pair);
            components.add(new Pair<>(original, alternatives));
        }
        return components;
    }

    private List<Pair<Column>> getAlternatives(ForeignKeyConstraint fkey, Pair<Column> originalPair) {
        List<Pair<Column>> alternatives = new ArrayList<>();
        Column local = originalPair.getFirst();
        Column reference = originalPair.getSecond();
        Table localTable = fkey.getTable();
        Table referenceTable = fkey.getReferenceTable();
        for (Column localReplacement : localTable.getColumns()) {
            for (Column referenceReplacement : referenceTable.getColumns()) {
                if (!referenceReplacement.equals(reference) || !localReplacement.equals(local)) {
                    if (localReplacement.getDataType().equals(referenceReplacement.getDataType())) {
                        alternatives.add(new Pair<>(localReplacement, referenceReplacement));
                    }
                }
            }
        }
        return alternatives;
    }

    @Override
    public void putComponentBackInDuplicate(Pair<List<Pair<Column>>> component) {
        List<Pair<Column>> oldPairs = currentDuplicate.getColumnPairs();
        List<Pair<Column>> newPairs = component.getFirst();
        for (Pair<Column> newPair : newPairs) {
            for (Iterator<Pair<Column>> it = oldPairs.iterator(); it.hasNext();) {
                Pair<Column> pair = it.next();
                // Needed to get rid of keys such as '(a, b) REFERENCES (d, d)',
                // prioritising the new key pair over the old key pair
                if (pair.getFirst().equals(newPair.getFirst()) || pair.getSecond().equals(newPair.getSecond())) {
                    it.remove();
                }
            }
        }
        List<Pair<Column>> pairs = new ArrayList<>();
        pairs.addAll(oldPairs);
        pairs.addAll(newPairs);
        currentDuplicate.setColumnPairs(pairs);
    }
}
