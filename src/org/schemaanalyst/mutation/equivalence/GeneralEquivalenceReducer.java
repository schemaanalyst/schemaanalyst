package org.schemaanalyst.mutation.equivalence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.schemaanalyst.mutation.Mutant;

/**
 * The {@link GeneralEquivalenceReducer} reduces the mutant list by 
 * removing mutants that are identical to eachother or the original
 * artefact, according to their equals method.  The {@link #reduce(List)}
 * method of this class should be called after all other {@link EquivalenceReducer}s
 * have been called, as a final sweep-up operation.
 * 
 * @author Phil McMinn.
 *
 * @param <A> The class of the artefact being mutated.
 */
public class GeneralEquivalenceReducer<A> extends EquivalenceReducer<A> {

	private A originalArtefact;
	
	/**
	 * Constructor.
	 * @param originalArtefact the original artefact that was mutated.
	 */
	public GeneralEquivalenceReducer(A originalArtefact) {
		this.originalArtefact = originalArtefact;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Mutant<A>> reduce(List<Mutant<A>> mutants) {
		Set<Mutant<A>> mutantSet = new HashSet<>(mutants);
		
		if (mutantSet.contains(originalArtefact)) {
			mutantSet.remove(originalArtefact);
		}
		
		return new ArrayList<>();
	}
}
