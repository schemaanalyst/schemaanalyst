package org.schemaanalyst.datageneration.search.objective.row;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.CompoundExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public abstract class ComposedExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected CompoundExpression expression;
    protected List<ObjectiveFunction<Row>> subObjFuns;

    public ComposedExpressionObjectiveFunction(CompoundExpression expression,
                                               boolean goalIsToSatisfy,
                                               boolean allowNull) {
        this.expression = expression;
        subObjFuns = new ArrayList<>();
        for (Expression subexpression : expression.getSubexpressions()) {
            subObjFuns.add((new ExpressionObjectiveFunctionFactory(
                    subexpression, goalIsToSatisfy, allowNull)).create());
        }
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        MultiObjectiveValue objVal = instantiateMultiObjectiveValue();
        for (ObjectiveFunction<Row> objFun : subObjFuns) {
            objVal.add(objFun.evaluate(row));
        }
        return objVal;
    }

    protected abstract MultiObjectiveValue instantiateMultiObjectiveValue();
}
