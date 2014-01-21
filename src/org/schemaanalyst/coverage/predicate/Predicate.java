package org.schemaanalyst.coverage.predicate;

import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private LinkedHashMap<Constraint, Clause> clauses;

    public Predicate() {
        clauses = new LinkedHashMap<>();
    }

    public void addClause(Constraint constraint, Clause clause) {
        clauses.put(constraint, clause);
    }

    public Clause getClause(Constraint constraint) {
        return clauses.get(constraint);
    }
}
