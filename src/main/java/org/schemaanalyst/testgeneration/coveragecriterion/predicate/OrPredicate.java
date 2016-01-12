package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by phil on 18/07/2014.
 */
public class OrPredicate extends ComposedPredicate {

    @Override
    public OrPredicate shallowDuplicate() {
        OrPredicate duplicate = new OrPredicate();
        duplicate.subPredicates = subPredicates;
        return duplicate;
    }

    @Override
    public void accept(PredicateVisitor predicateVisitor) {
        predicateVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + StringUtils.join(subPredicates, " \u2228 ") + ")";
    }
}
