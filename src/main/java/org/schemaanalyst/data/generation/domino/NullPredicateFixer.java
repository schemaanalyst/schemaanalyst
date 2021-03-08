package org.schemaanalyst.data.generation.domino;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.NullPredicateChecker;

/**
 * Created by phil on 13/10/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
public class NullPredicateFixer extends PredicateFixer {

    private NullPredicateChecker nullPredicateChecker;

    public NullPredicateFixer(NullPredicateChecker nullPredicateChecker) {
        this.nullPredicateChecker = nullPredicateChecker;
    }

    @Override
    public void attemptFix() {
        boolean setToNull = nullPredicateChecker.getPredicate().getTruthValue();

        for (Cell cell : nullPredicateChecker.getNonComplyingCells()) {
            cell.setNull(setToNull);
        }
    }
}
