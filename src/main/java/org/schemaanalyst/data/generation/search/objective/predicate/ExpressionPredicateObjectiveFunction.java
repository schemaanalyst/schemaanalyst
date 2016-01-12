package org.schemaanalyst.data.generation.search.objective.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.row.ExpressionRowObjectiveFunctionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ExpressionPredicate;

import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class ExpressionPredicateObjectiveFunction extends ObjectiveFunction<Data> {

    private ExpressionPredicate expressionPredicate;

    public ExpressionPredicateObjectiveFunction(ExpressionPredicate expressionPredicate) {
        this.expressionPredicate = expressionPredicate;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = expressionPredicate.toString();
        List<Row> rows = data.getRows(expressionPredicate.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

            for (Row row : rows) {
                objVal.add(
                        new ExpressionRowObjectiveFunctionFactory(
                                expressionPredicate.getExpression(),
                                expressionPredicate.getTruthValue(),
                                true).create().evaluate(row)
                );
            }

            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }
}
