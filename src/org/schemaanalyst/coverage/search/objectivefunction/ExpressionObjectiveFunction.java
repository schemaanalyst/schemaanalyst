package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.predicate.function.ExpressionFunction;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.ExpressionRowObjectiveFunctionFactory;

/**
 * Created by phil on 24/01/2014.
 */
public class ExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionFunction expressionFunction;

    public ExpressionObjectiveFunction(ExpressionFunction expressionFunction) {
        this.expressionFunction = expressionFunction;
    }

    @Override
    public ObjectiveValue evaluate(Row candidateSolution) {
        return new ExpressionRowObjectiveFunctionFactory(
                expressionFunction.getExpression(),
                expressionFunction.getSatisfy(),
                true).create().evaluate(candidateSolution);
    }
}
