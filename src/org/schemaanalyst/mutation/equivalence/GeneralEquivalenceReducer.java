package org.schemaanalyst.mutation.equivalence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.schemaanalyst.mutation.Mutant;

public class GeneralEquivalenceReducer<A> extends EquivalenceReducer<A> {

	private A originalArtefact;
	
	public GeneralEquivalenceReducer(A originalArtefact) {
		this.originalArtefact = originalArtefact;
	}
	
	@Override
	public List<Mutant<A>> reduce(List<Mutant<A>> mutants) {
		Set<Mutant<A>> mutantSet = new HashSet<>(mutants);
		
		if (mutantSet.contains(originalArtefact)) {
			mutantSet.remove(originalArtefact);
		}
		
		return new ArrayList<Mutant<A>>();
	}
}
