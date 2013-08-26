package org.schemaanalyst.mutation.equivalence;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;

public abstract class EquivalenceReducer<A> {

	public abstract List<Mutant<A>> reduce(List<Mutant<A>> mutants);
}
