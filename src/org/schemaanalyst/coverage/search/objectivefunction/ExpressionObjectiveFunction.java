package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.row.ExpressionRowObjectiveFunctionFactory;

import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class ExpressionObjectiveFunction extends ObjectiveFunction<Data> {

    private ExpressionClause expressionClause;

    public ExpressionObjectiveFunction(ExpressionClause expressionClause) {
        this.expressionClause = expressionClause;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = expressionClause.toString();
        List<Row> rows = data.getRows(expressionClause.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

            for (Row row : rows) {
                objVal.add(
                        new ExpressionRowObjectiveFunctionFactory(
                                expressionClause.getExpression(),
                                expressionClause.getSatisfy(),
                                true).create().evaluate(row)
                );
            }

            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }
}
