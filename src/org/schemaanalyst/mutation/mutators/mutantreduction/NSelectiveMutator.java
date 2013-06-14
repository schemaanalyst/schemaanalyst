/*
 */
package org.schemaanalyst.mutation.mutators.mutantreduction;

import casestudy.Cloc;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;

/**
 * Similar to Offut1993 style of selective mutation, when the n most productive
 * mutators are omitted. Note that all mutations are still generated, so this is
 * not the most efficient implementation, but guarantees to select the correct
 * mutators to omit/include.
 *
 * It is important to consider that the selection is applied on a
 * per-table-mutated basis. That is, the operators removed are the n most
 * productive for mutations applied to each table.
 *
 * @author chris
 */
public class NSelectiveMutator extends Mutator {

    private int n;
    private Mutator[] mutators;

    public NSelectiveMutator(int n, Mutator... mutators) {
        if (mutators.length == 0) {
            throw new IllegalArgumentException("Provided input 'mutators' must"
                    + " have a minimum length of 1. Current length: 0");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Provided input 'limit' must be"
                    + " greater than or equal to 0. Current value: " + n);
        }
        this.mutators = mutators;
        this.n = n;
    }

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        SortedMap<List<Schema>, Mutator> ranks = new TreeMap<>(new Comparator<List<Schema>>() {
            @Override
            public int compare(List<Schema> o1, List<Schema> o2) {
                if (o1.size() < o2.size()) {
                    return 1;
                } else if (o1.size() > o2.size()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        for (Mutator mutator : mutators) {
            ArrayList<Schema> mutatorMutants = new ArrayList<>();
            mutator.produceMutants(table, mutatorMutants);
            ranks.put(mutatorMutants, mutator);
        }
        int rank = 1;
        for (Iterator<List<Schema>> iter = ranks.keySet().iterator(); iter.hasNext();) {
            List<Schema> list = iter.next();
            System.out.println("Rank: " + rank + " Size: " + list.size());
            if (rank > n) {
                mutants.addAll(list);
            }
            rank++;
        }
    }
}
