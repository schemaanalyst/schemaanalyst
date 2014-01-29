package org.schemaanalyst.coverage.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.predicate.function.Function;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private LinkedHashSet<Clause> clauses;
    private String purpose;

    public Predicate(String description) {
        this.purpose = description;
        clauses = new LinkedHashSet<>();
    }

    public void addClause(Clause clause) {
        clauses.add(clause);
    }

    public void addClause(Function function) {
        addClause(null, function);
    }

    public void addClause(Constraint underpinningConstraint, Function function) {
        addClause(new Clause(underpinningConstraint, function));
    }

    public boolean removeClause(Constraint underpinningConstraint) {
        boolean success = false;
        Iterator<Clause> it = clauses.iterator();
        while (it.hasNext()) {
            if (underpinningConstraint.equals(it.next().getUnderpinningConstraint())) {
                it.remove();
                success = true;
            }
        }
        return success;
    }

    public String getPurpose() {
        return purpose;
    }

    public String toString() {
        return StringUtils.join(clauses, " \u2227 ");
    }
}
