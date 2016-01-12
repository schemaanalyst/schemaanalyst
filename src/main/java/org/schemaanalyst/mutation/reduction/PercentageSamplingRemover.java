/*
 */
package org.schemaanalyst.mutation.reduction;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * A {@link MutantRemover} takes a list of mutants and randomly selects a given 
 * percentage of mutants to retain.
 * </p>
 * 
 * <p>
 * Note that for very large lists of mutants this may give poor performance, as 
 * the list must be shuffled.
 * </p>
 *
 * @author Chris J. Wright
 */
public class PercentageSamplingRemover<T> extends MutantRemover<T> {

    private final double percent;
    private final Random random;

    /**
     * Constructor with a specified random number generator, used to randomly 
     * select the elements.
     * 
     * @param percent The percentage to retain, out of 1
     * @param random 
     */
    public PercentageSamplingRemover(double percent, Random random) {
        this.percent = percent;
        this.random = random;
    }

    /**
     * Constructor with a default random number generator, used to randomly 
     * select the elements.
     * 
     * @param percent The percentage to retain, out of 1
     */
    public PercentageSamplingRemover(double percent) {
        this.percent = percent;
        this.random = new Random();
    }

    @Override
    public List<Mutant<T>> removeMutants(List<Mutant<T>> mutants) {
        int count = (int) Math.round(percent * mutants.size());
        return new SamplingRemover<T>(count, random).removeMutants(mutants);
    }
}
