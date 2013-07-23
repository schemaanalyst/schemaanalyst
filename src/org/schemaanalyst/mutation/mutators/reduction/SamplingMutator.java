/*
 */
package org.schemaanalyst.mutation.mutators.reduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * A wrapper for one or more mutators, which randomly selects mutants from the
 * pool of mutants created by those mutators, up to a provided limit.
 *
 * It is important to consider that the selection is applied on a
 * per-table-mutated basis. That is, the sample is taken for each table mutated.
 *
 * @author chris
 */
public class SamplingMutator extends Mutator {

    private int limit;
    private Mutator[] mutators;

    /**
     * Constructor.
     *
     * @param limit The number of mutants to return
     * @param mutators The mutators to apply
     */
    public SamplingMutator(int limit, Mutator... mutators) {
        this.limit = limit;
        this.mutators = mutators;
        if (mutators.length == 0) {
            throw new IllegalArgumentException("Provided input 'mutators' must"
                    + " have a minimum length of 1. Current length: 0");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Provided input 'limit' must be"
                    + " greater than or equal to 1. Current value: " + limit);
        }
    }

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        List<Schema> allMutants = new ArrayList<>();
        for (Mutator mutator : mutators) {
            mutator.produceMutants(table, allMutants);
        }
        Collections.shuffle(allMutants);
        mutants.addAll(allMutants.subList(0, limit));
    }
}
