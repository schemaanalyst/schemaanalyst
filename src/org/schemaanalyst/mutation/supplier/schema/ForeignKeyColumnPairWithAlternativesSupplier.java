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
import java.util.Iterator;
import java.util.List;

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

    private boolean supplyByTypes;

    public ForeignKeyColumnPairWithAlternativesSupplier() {
        supplyByTypes = true;
    }

    public ForeignKeyColumnPairWithAlternativesSupplier(boolean supplyByTypes) {
        this.supplyByTypes = supplyByTypes;
    }

    @Override
    protected List<Pair<List<Pair<Column>>>> getComponents(ForeignKeyConstraint fkey) {
        List<Pair<List<Pair<Column>>>> components = new ArrayList<>();
        for (Pair<Column> pair : fkey.getColumnPairs()) {
            List<Pair<Column>> original = Arrays.asList(pair);
            List<Pair<Column>> alternatives = getAlternatives(fkey, pair);
            components.add(new Pair<>(original, alternatives));
        }
        components = deduplicate(fkey, components);
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
                // (PM: Ensures that only one thing is changed - could be two separate loops rather than a nested one.)
                if ((referenceReplacement.equals(reference) && !localReplacement.equals(local))
                        || (!referenceReplacement.equals(reference) && localReplacement.equals(local))) {

                    // should this pair be supplied?
                    boolean addPair =
                            !supplyByTypes ||
                                    (supplyByTypes && localReplacement.getDataType().equals(referenceReplacement.getDataType()));
                    if (addPair) {
                        Pair<Column> pair = new Pair<>(localReplacement, referenceReplacement);
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

    private List<Pair<List<Pair<Column>>>> deduplicate(ForeignKeyConstraint fkey, List<Pair<List<Pair<Column>>>> components) {
        List<Pair<List<Pair<Column>>>> result = new ArrayList<>();        
        List<List<Pair<Column>>> mutants = new ArrayList<>();
        for (Pair<List<Pair<Column>>> component : components) {
            Pair<Column> original = component.getFirst().get(0);
            for (Pair<Column> mutant : component.getSecond()) {
                // Apply the mutation
                List<Pair<Column>> constraints = new ArrayList<>();
                constraints.add(mutant);
                for (Pair<Column> columnPair : fkey.getColumnPairs()) {
                    if (!columnPair.equals(original) && !isPairDuplicate(columnPair, constraints)) {
                        constraints.add(columnPair);
                    }
                }
                ArrayList<Pair<Column>> replacements = new ArrayList<>();
                if (!mutants.contains(constraints)) {
                    mutants.add(constraints);
                    replacements.add(mutant);
                }
                result.add(new Pair<>(component.getFirst(), replacements));
            }
        }
        return result;
    }
    
    private static boolean isPairDuplicate(Pair<Column> possiblePair, List<Pair<Column>> constraints) {
        boolean found = false;
        for (Pair<Column> pair : constraints) {
            if (possiblePair.getFirst().equals(pair.getFirst()) || possiblePair.getSecond().equals(pair.getSecond())) {
                found = true;
                break;
            }
        }
        return found;
    }
}
