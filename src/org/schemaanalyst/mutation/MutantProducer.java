package org.schemaanalyst.mutation;

import java.util.List;

public abstract class MutantProducer<A> {
	
	public abstract List<Mutant<A>> mutate();	
}
