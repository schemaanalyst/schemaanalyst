package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class Predicate {

    public abstract void accept(PredicateVisitor visitor);

    public Predicate reduce() {
        return this;
    }

    public boolean isInfeasible() {
        return false;
    }
}
