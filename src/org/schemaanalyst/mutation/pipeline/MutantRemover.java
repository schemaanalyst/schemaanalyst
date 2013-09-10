/*
 */
package org.schemaanalyst.mutation.pipeline;

import java.util.List;
import org.schemaanalyst.mutation.Mutant;

/**
 * A {@link MutantRemover} takes a list of mutants and removes any not meeting
 * some criteria.
 *
 * @author Chris J. Wright
 */
public abstract class MutantRemover<A> {

    /**
	 * Produce a reduced list of mutants based on some criteria.
	 * 
	 * @param mutants the list of mutants to be reduced.
	 * @return the list of reduced mutants.
	 */
    public abstract List<Mutant<A>> removeMutants(List<Mutant<A>> mutants);
}