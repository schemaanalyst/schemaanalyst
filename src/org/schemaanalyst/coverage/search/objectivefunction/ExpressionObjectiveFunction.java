package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.ExpressionRowObjectiveFunctionFactory;

/**
 * Created by phil on 24/01/2014.
 */
public class ExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionClause expressionClause;

    public ExpressionObjectiveFunction(ExpressionClause expressionClause) {
        this.expressionClause = expressionClause;
    }

    @Override
    public ObjectiveValue evaluate(Row candidateSolution) {
        return new ExpressionRowObjectiveFunctionFactory(
                expressionClause.getExpression(),
                expressionClause.getSatisfy(),
                true).create().evaluate(candidateSolution);
    }
}
