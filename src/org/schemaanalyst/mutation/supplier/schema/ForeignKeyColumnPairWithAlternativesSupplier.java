/*
 */
package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
public class ForeignKeyColumnPairWithAlternativesSupplier extends IteratingSupplier<ForeignKeyConstraint, Pair<List<Pair<Column>>>> {

    @Override
    protected List<Pair<List<Pair<Column>>>> getComponents(ForeignKeyConstraint fkey) {
        List<Pair<List<Pair<Column>>>> components = new ArrayList<>();
        for (Pair<Column> pair : fkey.getColumnPairs()) {
            List<Pair<Column>> original = Arrays.asList(pair);
            System.out.println("original: " + original);
            List<Pair<Column>> alternatives = getAlternatives(fkey, pair);
            System.out.println("alternatives: " + alternatives);
            components.add(new Pair<>(original, alternatives));
            System.out.println("new components: " + components);
        }
        deduplicate(components);
        return components;
    }

    // This version ensures that at least one column is the same
    private List<Pair<Column>> getAlternatives(ForeignKeyConstraint fkey, Pair<Column> originalPair) {
        List<Pair<Column>> alternatives = new ArrayList<>();
        Column local = originalPair.getFirst();
        Column reference = originalPair.getSecond();
        Table localTable = fkey.getTable();
        Table referenceTable = fkey.getReferenceTable();
        for (Column localReplacement : localTable.getColumns()) {
            for (Column referenceReplacement : referenceTable.getColumns()) {
                // If reference has changed and local hasn't, or local has changed and reference hasn't
                if ((referenceReplacement.equals(reference) && !localReplacement.equals(local))
                        || (!referenceReplacement.equals(reference) && localReplacement.equals(local))) {
                    if (localReplacement.getDataType().equals(referenceReplacement.getDataType())) {
                        Pair<Column> pair = new Pair<>(localReplacement, referenceReplacement);
                        System.out.println("existing pairs: " + alternatives);
                        System.out.println("new pair: " + pair);
                        alternatives.add(pair);
                        
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

    private List<Pair<List<Pair<Column>>>> deduplicate(List<Pair<List<Pair<Column>>>> components) {
        System.out.println("\nDeduplicate\n");
        for (Pair<List<Pair<Column>>> component : components) {
            Set<Column> localCols = new HashSet<>();
            Set<Column> refCols = new HashSet<>();
            Set<Pair<Column>> validPairs = new HashSet<>();
//            validPairs.add(null)
//            validPairs.addAll(component.getFirst());
//            for (Pair<Column> possiblePair : component.getSecond()) {
//                for (Pair<Column> validPair : validPairs) {
//                    
//                }
//            }
            System.out.println(component);
        }
        System.out.println();
        return null;
    }
}
