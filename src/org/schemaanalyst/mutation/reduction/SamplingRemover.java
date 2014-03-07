/*
 */

package org.schemaanalyst.mutation.reduction;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * A {@link MutantRemover} takes a list of mutants and randomly selects a given 
 * number of mutants to retain.
 * </p>
 * 
 * <p>
 * Note that for very large lists of mutants this may give poor performance, as 
 * the list must be shuffled.
 * </p>
 *
 * @author Chris J. Wright
 */
public class SamplingRemover<T> extends MutantRemover<T>{

    private int count;
    private Random random;

    /**
     * Constructor with a specified random number generator, used to randomly 
     * select the elements.
     * 
     * @param count
     * @param random 
     */
    public SamplingRemover(int count, Random random) {
        this.count = count;
        this.random = random;
    }

    /**
     * Constructor with a default random number generator, used to randomly 
     * select the elements.
     * 
     * @param count 
     */
    public SamplingRemover(int count) {
        this.count = count;
        this.random = new Random();
    }    

    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        if (count > mutants.size()) {
            return mutants;
        } else {
            Collections.shuffle(mutants, random);
            return new ArrayList<>(mutants.subList(0, count));
        }
    }
    
}
