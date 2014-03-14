package org.schemaanalyst._deprecated.datageneration.search.objective;

public abstract class ObjectiveFunction<T> {

    public abstract ObjectiveValue evaluate(T candidateSolution);
}
