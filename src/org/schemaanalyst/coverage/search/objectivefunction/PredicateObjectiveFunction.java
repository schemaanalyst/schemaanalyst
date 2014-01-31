package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.*;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 31/01/2014.
 */
public class PredicateObjectiveFunction extends ObjectiveFunction<Row> {

    private Predicate predicate;
    private Data state;
    private List<ObjectiveFunction<Row>> objectiveFunctions;

    public PredicateObjectiveFunction(Predicate predicate, Data state) {

        this.predicate = predicate;
        this.state = state;
        this.objectiveFunctions = new ArrayList<>();

        ClauseVisitor visitor = new ClauseVisitor() {

            @Override
            public void visit(ExpressionClause clause) {
                objectiveFunctions.add(new ExpressionObjectiveFunction(clause));
            }

            @Override
            public void visit(MatchClause clause) {
                objectiveFunctions.add(
                        new MatchObjectiveFunction(
                                clause,
                                PredicateObjectiveFunction.this.state));
            }

            @Override
            public void visit(NullClause clause) {
                objectiveFunctions.add(new NullObjectiveFunction(clause));
            }
        };

        for (Clause clause : predicate.getClauses()) {
            clause.accept(visitor);
        }
    }

    @Override
    public ObjectiveValue evaluate(Row candidateSolution) {
        SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue("Predicate " + predicate);

        for (ObjectiveFunction<Row> objFun : objectiveFunctions) {
            objVal.add(objFun.evaluate(candidateSolution));
        }

        return objVal;
    }
}
