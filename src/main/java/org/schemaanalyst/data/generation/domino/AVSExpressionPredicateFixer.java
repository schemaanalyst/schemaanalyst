package org.schemaanalyst.data.generation.domino;


import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.data.generation.search.objective.predicate.ExpressionPredicateObjectiveFunction;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;


/**
 * Created by phil on 27/02/2014.
 * Updated by Abdullah Summer/Fall 2017
 */
public class AVSExpressionPredicateFixer extends PredicateFixer {

    private ExpressionPredicateChecker expressionPredicateChecker;
    private AlternatingValueSearch avs;

    public AVSExpressionPredicateFixer(ExpressionPredicateChecker expressionPredicateChecker, AlternatingValueSearch avs) {
        this.expressionPredicateChecker = expressionPredicateChecker;
        this.avs = avs;
    }

    @Override
    public void attemptFix() {
        if (expressionPredicateChecker.getNonComplyingCells().size() > 0) {
            Data data = expressionPredicateChecker.getNonComplyingData();
            avs.setObjectiveFunction(
                    new ExpressionPredicateObjectiveFunction(expressionPredicateChecker.getPredicate()));
            avs.initialize();
            avs.search(data);
        }
    }
}
