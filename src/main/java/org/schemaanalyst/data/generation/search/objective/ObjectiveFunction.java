package org.schemaanalyst.data.generation.search.objective;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 14/03/2014.
 */
public abstract class ObjectiveFunction<T> {

    public abstract ObjectiveValue evaluate(T candidateSolution);
    
    public abstract Data getState();
    
    public abstract Predicate getpredicate();
}
