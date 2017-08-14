package org.schemaanalyst.data.generation.altarkiz;


import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.data.generation.search.objective.predicate.ExpressionPredicateObjectiveFunction;
import org.schemaanalyst.data.generation.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.ExpressionPredicateChecker;


/**
 * Created by phil on 27/02/2014.
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

            // TODO cap maxEvaluations if less than that amount available
            int maxEvaluations = 1000;

            TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                    new CounterTerminationCriterion(avs.getEvaluationsCounter(), maxEvaluations),
                    new OptimumTerminationCriterion<>(avs));

            avs.setTerminationCriterion(terminationCriterion);

            avs.setObjectiveFunction(
                    new ExpressionPredicateObjectiveFunction(expressionPredicateChecker.getPredicate()));

            avs.initialize();

            avs.search(data);
        }
    }
}
