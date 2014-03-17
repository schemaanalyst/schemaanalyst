package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class PredicateObjectiveFunction extends ObjectiveFunction<Data> {

    private static final int EXPRESSION_CLAUSE_WEIGHT = 1;
    private static final int MATCH_CLAUSE_WEIGHT = 1;
    private static final int NULL_CLAUSE_WEIGHT = 5;

    private Predicate predicate;
    private Data state;
    private List<ObjectiveFunction<Data>> objectiveFunctions;

    public PredicateObjectiveFunction(Predicate predicate, Data state) {

        this.predicate = predicate;
        this.state = state;
        this.objectiveFunctions = new ArrayList<>();

        ClauseVisitor visitor = new ClauseVisitor() {

            @Override
            public void visit(ExpressionClause clause) {
                for (int i=0; i < EXPRESSION_CLAUSE_WEIGHT; i++) {
                    objectiveFunctions.add(new ExpressionClauseObjectiveFunction(clause));
                }
            }

            @Override
            public void visit(MatchClause clause) {
                for (int i=0; i < MATCH_CLAUSE_WEIGHT; i++) {
                    objectiveFunctions.add(
                            new MatchClauseObjectiveFunction(
                                    clause,
                                    PredicateObjectiveFunction.this.state));
                }
            }

            @Override
            public void visit(NullClause clause) {
                for (int i=0; i < NULL_CLAUSE_WEIGHT; i++) {
                    objectiveFunctions.add(new NullClauseObjectiveFunction(clause));
                }
            }
        };

        for (Clause clause : predicate.getClauses()) {
            clause.accept(visitor);
        }
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue("Predicate " + predicate);

        for (ObjectiveFunction<Data> objFun : objectiveFunctions) {
            objVal.add(objFun.evaluate(data));
        }

        return objVal;
    }
}
