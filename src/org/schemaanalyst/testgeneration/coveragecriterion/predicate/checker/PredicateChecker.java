package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.logic.predicate.clause.Clause;

/**
 * Created by phil on 28/02/2014.
 */
public abstract class PredicateChecker extends Checker {

    public abstract Clause getClause();

    public abstract String getInfo();
}
