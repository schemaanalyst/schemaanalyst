package org.schemaanalyst.mutation;

import java.util.List;

import org.schemaanalyst.util.Duplicable;

public abstract class MutationPipeline<A extends Duplicable<A>> {
	
	public abstract List<Mutant<A>> mutate();
	
}
