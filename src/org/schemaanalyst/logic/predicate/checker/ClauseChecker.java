package org.schemaanalyst.logic.predicate.checker;

import org.schemaanalyst.logic.predicate.clause.Clause;

/**
 * Created by phil on 28/02/2014.
 */
public abstract class ClauseChecker<T extends Clause> extends Checker {

    public abstract T getClause();

    public abstract String getInfo();
}
