package org.schemaanalyst.mutation.equivalence;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;

/**
 * An {@link EquivalenceReducer} takes a list of mutants and removes
 * any that are found to be equivalent to other mutants or the original
 * in some respect.
 * 
 * @author Phil McMinn
 *
 * @param <A> The class of the artefact being mutated.
 */
public abstract class EquivalenceReducer<A> {

	/**
	 * Produce a reduced list of mutants based on the notion of
	 * equivalence implemented by the {@link EquivalenceReducer}. 
	 * 
	 * @param mutants the list of mutants to be reduced.
	 * @return the list of reduced mutants.
	 */
	public abstract List<Mutant<A>> reduce(List<Mutant<A>> mutants);
}
