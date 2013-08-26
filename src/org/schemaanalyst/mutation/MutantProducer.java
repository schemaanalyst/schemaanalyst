package org.schemaanalyst.mutation;

import java.util.List;

/**
 * Interface for anything producing mutants.
 * @author Phil McMinn
 *
 * @param <A> The class of the artefact being mutated.
 */
public interface MutantProducer<A> {
	
	/**
	 * Performs the mutation.
	 * @return A list of mutant objects.
	 */
	public List<Mutant<A>> mutate();
}
