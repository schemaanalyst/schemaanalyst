package org.schemaanalyst.mutation.redundancy;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;

import java.util.List;

/**
 * <p>
 * A {@link RedundantMutantRemover} takes a list of mutants and removes
 * any that are found to be equivalent to other mutants or the original
 * in some respect.
 * </p>
 * 
 * @author Phil McMinn
 *
 * @param <A> The class of the artefact being mutated.
 */
public abstract class RedundantMutantRemover<A> extends MutantRemover<A> {

	/**
	 * Produce a reduced list of mutants based on the notion of
	 * equivalence implemented by the {@link RedundantMutantRemover}. 
	 * 
	 * @param mutants the list of mutants to be reduced.
	 * @return the list of reduced mutants.
	 */
    @Override
	public abstract List<Mutant<A>> removeMutants(List<Mutant<A>> mutants);
}
