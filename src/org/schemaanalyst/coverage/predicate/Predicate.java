package org.schemaanalyst.coverage.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.predicate.function.Function;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private LinkedHashSet<Clause> clauses;

    public Predicate() {
        clauses = new LinkedHashSet<>();
    }

    public void addClause(Clause clause) {
        clauses.add(clause);
    }

    public void addClauses(List<Clause> clausesToAdd) {
        for (Clause clause : clausesToAdd) {
            addClause(clause);
        }
    }

    public void addClause(Function function) {
        addClause(null, function);
    }

    public void addClause(Constraint relatedConstraint, Function function) {
        addClause(new Clause(relatedConstraint, function));
    }

    public boolean removeClause(Constraint relatedConstraint) {
        boolean success = false;
        Iterator<Clause> it = clauses.iterator();
        while (it.hasNext()) {
            if (relatedConstraint.equals(it.next().getRelatedConstraint())) {
                it.remove();
                success = true;
            }
        }
        return success;
    }

    public String toString() {
        return StringUtils.join(clauses, " /\\ ");
    }
}
