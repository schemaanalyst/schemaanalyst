/*
 */

package org.schemaanalyst.mutation.reduction;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.*;

/**
 * <p>
 * Similar to Offut1993 style of selective mutation, when the n most productive
 * mutators are omitted.
 * </p>
 *
 * @author Chris J. Wright
 */
public class NSelectiveRemover<T> extends MutantRemover<T>{
    
    private int n;

    /**
     * Constructor to create a remover that will remove the mutants from the n 
     * most productive mutators.
     * 
     * @param n The number of mutators to remove
     */
    public NSelectiveRemover(int n) {
        this.n = n;
    }

    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        // Split by MutantProducer
        Map<MutantProducer, List<Mutant<T>>> mutantMap = new LinkedHashMap<>();
        for (Mutant<T> mutant : mutants) {
            MutantProducer mutantProducer = mutant.getMutantProducer();
            if (mutantMap.containsKey(mutantProducer)) {
                mutantMap.get(mutantProducer).add(mutant);
            } else {
                List<Mutant<T>> list = new ArrayList<>();
                list.add(mutant);
                mutantMap.put(mutantProducer, list);
            }
        }
        // Create ordered map
        TreeMap<Integer, MutantProducer> countMap = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<MutantProducer, List<Mutant<T>>> entry : mutantMap.entrySet()) {
            countMap.put(entry.getValue().size(), entry.getKey());
        }
        // Remove n most productive
        Iterator<Map.Entry<Integer, MutantProducer>> iterator = countMap.entrySet().iterator();
        for (int i = 0; i < n; i++) {
            if (iterator.hasNext()) {
                mutantMap.remove(iterator.next().getValue());
            }
        }
        // Create final mutant list
        List<Mutant<T>> result = new ArrayList<>();
        for (Map.Entry<MutantProducer, List<Mutant<T>>> entry : mutantMap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }
    
    
}
