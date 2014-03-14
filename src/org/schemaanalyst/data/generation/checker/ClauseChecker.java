package org.schemaanalyst.data.generation.checker;

import org.schemaanalyst.testgeneration.coveragecriterion.clause.Clause;

/**
 * Created by phil on 28/02/2014.
 */
public abstract class ClauseChecker<T extends Clause> extends Checker {

    public abstract T getClause();

    public abstract String getInfo();
}
