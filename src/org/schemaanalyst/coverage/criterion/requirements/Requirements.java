package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/03/2014.
 */
public class Requirements {

    private ArrayList<Predicate> predicates;

    public Requirements() {
        this.predicates = new ArrayList<>();
    }

    public List<Predicate> getPredicates() {
        return new ArrayList<>(predicates);
    }

    public void addPredicate(Predicate predicateToAdd) {
        for (Predicate predicate : predicates) {
            if (predicate.equals(predicateToAdd)) {
                // Debug code
                // System.out.println(predicate.getPurposes());
                // System.out.println(predicate);
                // System.out.println(predicateToAdd.getPurposes());
                // System.out.println(predicateToAdd);
                // System.out.println();
                predicate.addPurposes(predicateToAdd.getPurposes());
                return;
            }
        }
        predicates.add(predicateToAdd);
    }

    public void addPredicates(List<Predicate> predicates) {
        for (Predicate predicate : predicates) {
            addPredicate(predicate);
        }
    }

    public void addPredicates(Requirements requirements) {
        addPredicates(requirements.getPredicates());
    }

    public int size() {
        return predicates.size();
    }
}
