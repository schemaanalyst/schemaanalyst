package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

/**
 * Created by phil on 28/02/2014.
 */
public abstract class PredicateChecker extends Checker {

    public abstract Predicate getPredicate();

    public abstract String getInfo();
}
