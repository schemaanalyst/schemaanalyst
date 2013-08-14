package org.schemaanalyst.mutation2;

import java.util.List;

import org.schemaanalyst.util.Duplicable;

public abstract class MutationPipeline<A extends Duplicable<A>> {

	protected A originalArtefact;
	
	public MutationPipeline(A originalArtefact) {
		this.originalArtefact = originalArtefact;
	}
	
	public abstract List<Mutant<A>> mutate();
	
}
