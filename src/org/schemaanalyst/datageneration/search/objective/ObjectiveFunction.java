package org.schemaanalyst.datageneration.search.objective;

public abstract class ObjectiveFunction<T> {

	public abstract ObjectiveValue evaluate(T candidateSolution);
	
}
