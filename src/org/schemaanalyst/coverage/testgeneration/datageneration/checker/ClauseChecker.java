package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.Clause;

/**
 * Created by phil on 28/02/2014.
 */
public abstract class ClauseChecker<T extends Clause> extends Checker {

    public abstract T getClause();

    public abstract String getDump();
}
