package org.schemaanalyst.coverage.predicate;

import org.schemaanalyst.coverage.predicate.function.Function;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

/**
 * Created by phil on 19/01/2014.
 */
public class Clause {

    private Function function;
    private Constraint underpinningConstraint;

    public Clause(Function function) {
        this(null, function);
    }

    public Clause(Constraint underpinningConstraint, Function function) {
        this.function = function;
        this.underpinningConstraint = underpinningConstraint;
    }

    public Constraint getUnderpinningConstraint() {
        return underpinningConstraint;
    }

    public String toString() {
        return function.toString();
    }
}