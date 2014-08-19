package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import java.io.Serializable;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class Predicate implements Serializable {

    private static final long serialVersionUID = 3508517475039358539L;

    public abstract void accept(PredicateVisitor visitor);

    public Predicate reduce() {
        return this;
    }

    public boolean isTriviallyInfeasible() {
        return false;
    }
}
