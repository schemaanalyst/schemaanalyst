package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

/**
 * Created by phil on 24/07/2014.
 */
public class PredicateObjectiveFunctionFactory {

    public static ObjectiveFunction<Data> createObjectiveFunction(Predicate predicate, final Data state) {

        return new PredicateVisitor() {
            ObjectiveFunction<Data> objFun;

            ObjectiveFunction<Data> create(Predicate predicate) {
                predicate.accept(this);
                return objFun;
            }

            @Override
            public void visit(AndPredicate predicate) {
                objFun = new AndPredicateObjectiveFunction(predicate, state);
            }

            @Override
            public void visit(ExpressionPredicate predicate) {
                objFun = new ExpressionPredicateObjectiveFunction(predicate);
            }

            @Override
            public void visit(MatchPredicate predicate) {
                objFun = new MatchPredicateObjectiveFunction(predicate, state);
            }

            @Override
            public void visit(NullPredicate predicate) {
                objFun = new NullPredicateObjectiveFunction(predicate);
            }

            @Override
            public void visit(OrPredicate predicate) {
                objFun = new OrPredicateObjectiveFunction(predicate, state);

            }
        }.create(predicate);
    }
}
