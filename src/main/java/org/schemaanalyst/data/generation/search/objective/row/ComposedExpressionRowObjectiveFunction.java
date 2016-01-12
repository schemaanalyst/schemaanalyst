package org.schemaanalyst.data.generation.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.generation.search.objective.MultiObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.sqlrepresentation.expression.CompoundExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public abstract class ComposedExpressionRowObjectiveFunction extends ObjectiveFunction<Row> {

    protected CompoundExpression expression;
    protected List<ObjectiveFunction<Row>> subObjFuns;
    protected boolean goalIsToSatisfy, allowNull;
    
    public ComposedExpressionRowObjectiveFunction(CompoundExpression expression,
                                               boolean goalIsToSatisfy,
                                               boolean allowNull) {
        this.expression = expression;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
        
        subObjFuns = new ArrayList<>();
        for (Expression subexpression : expression.getSubexpressions()) {
            subObjFuns.add((new ExpressionRowObjectiveFunctionFactory(
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
