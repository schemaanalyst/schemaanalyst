package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 18/07/2014.
 */
public abstract class ComposedPredicate extends Predicate {

    protected LinkedHashSet<Predicate> subPredicates;

    public ComposedPredicate() {
        subPredicates = new LinkedHashSet<>();
    }

    public void addPredicate(Predicate subPredicate) {
        subPredicates.add(subPredicate);
    }

    public List<Predicate> getSubPredicates() {
        return new ArrayList<>(subPredicates);
    }

    public int numSubPredicates() {
        return subPredicates.size();
    }

    public boolean contains(Predicate predicate) {
        return subPredicates.contains(predicate);
    }

    public abstract ComposedPredicate shallowDuplicate();

    public Predicate reduce() {

        if (subPredicates.size() == 1) {
            return subPredicates.iterator().next();
        }

        ComposedPredicate duplicate = shallowDuplicate();
        duplicate.subPredicates = new LinkedHashSet<>();

        for (Predicate subPredicate : subPredicates) {
            Predicate reducedSubPredicate = subPredicate.reduce();

            if (reducedSubPredicate.getClass().equals(getClass())) {
                duplicate.subPredicates.addAll(((ComposedPredicate) reducedSubPredicate).getSubPredicates());
            } else {
                duplicate.subPredicates.add(reducedSubPredicate);
            }
        }

        return duplicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComposedPredicate other = (ComposedPredicate) o;

        if (subPredicates.size() != other.subPredicates.size()) {
            return false;
        }
        
        for (Predicate predicate : subPredicates) {
            if (!other.contains(predicate)) {
                return false;
            }
        }

        return true;
    }

    @Override
    // This has been modified from the auto-generated version to return a different hashcode depending on the subclass
    public int hashCode() {
        return (subPredicates != null)
                ? this.getClass().getName().hashCode() * subPredicates.hashCode()
                : 0;
    }
}
