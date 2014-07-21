package org.schemaanalyst.testgeneration.criterion.predicate;

import java.util.LinkedHashSet;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class ComposedPredicate extends Predicate {

    protected LinkedHashSet<Predicate> predicates;

    public ComposedPredicate() {
        predicates = new LinkedHashSet<>();
    }

    public void addPredicate(Predicate predicate) {
        predicates.add(predicate);
    }

    public boolean contains(Predicate predicate) {
        return predicates.contains(predicate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComposedPredicate other = (ComposedPredicate) o;

        if (predicates.size() != other.predicates.size()) {
            return false;
        }

        for (Predicate predicate : predicates) {
            if (!other.contains(predicate)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return predicates != null ? predicates.hashCode() : 0;
    }
}
