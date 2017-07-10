package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 24/07/2014.
 */
public abstract class ComposedPredicateObjectiveFunction extends ObjectiveFunction<Data> {

    private static final int AND_PREDICATE_WEIGHT = 1;
    private static final int EXPRESSION_PREDICATE_WEIGHT = 1;
    private static final int MATCH_PREDICATE_WEIGHT = 1;
    private static final int NULL_PREDICATE_WEIGHT = 5;
    private static final int OR_PREDICATE_WEIGHT = 1;

    public Data state;
    public Predicate predicate;
    private List<ObjectiveFunction<Data>> objectiveFunctions;
    private List<Integer> weights;

    public ComposedPredicateObjectiveFunction(ComposedPredicate predicate, final Data state) {
        this.predicate = predicate;
        this.state = state;
        objectiveFunctions = new ArrayList<>();
        weights = new ArrayList<>();

        for (Predicate subPredicate : predicate.getSubPredicates()) {
            objectiveFunctions.add(PredicateObjectiveFunctionFactory.createObjectiveFunction(subPredicate, state));
            weights.add(new PredicateVisitor() {
                int weight;

                int getWeight(Predicate predicate) {
                    predicate.accept(this);
                    return weight;
                }

                @Override
                public void visit(AndPredicate predicate) {
                    weight = AND_PREDICATE_WEIGHT;
                }

                @Override
                public void visit(ExpressionPredicate predicate) {
                    weight = EXPRESSION_PREDICATE_WEIGHT;
                }

                @Override
                public void visit(MatchPredicate predicate) {
                    weight = MATCH_PREDICATE_WEIGHT;
                }

                @Override
                public void visit(NullPredicate predicate) {
                    weight = NULL_PREDICATE_WEIGHT;
                }

                @Override
                public void visit(OrPredicate predicate) {
                    weight = OR_PREDICATE_WEIGHT;
                }
            }.getWeight(subPredicate));
        }
    }

    protected abstract MultiObjectiveValue createObjectiveValue(String description);

    @Override
    public ObjectiveValue evaluate(Data data) {

        MultiObjectiveValue objVal = createObjectiveValue("Predicate " + predicate);

        Iterator<ObjectiveFunction<Data>> objFunIt = objectiveFunctions.iterator();
        Iterator<Integer> weightsIt = weights.iterator();

        while (objFunIt.hasNext()) {
            ObjectiveFunction<Data> objFun = objFunIt.next();
            int weight = weightsIt.next();

            ObjectiveValue componentObjVal = objFun.evaluate(data);
            for (int i=0; i < weight; i++) {
                objVal.add(componentObjVal);
            }
        }

        return objVal;
    }

}
