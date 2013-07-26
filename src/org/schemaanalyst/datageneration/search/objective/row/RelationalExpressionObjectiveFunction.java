package org.schemaanalyst.datageneration.search.objective.row;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.value.ValueObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    protected ExpressionEvaluator lhs, rhs;
    protected RelationalOperator op;
    protected ValueObjectiveFunction valObjFun;
    protected boolean allowNull;
    
    public RelationalExpressionObjectiveFunction(RelationalExpression expression,
                                                 boolean goalIsToSatisfy,
                                                 boolean allowNull) {
        lhs = new ExpressionEvaluator(expression.getLHS());
        rhs = new ExpressionEvaluator(expression.getRHS());

        RelationalOperator op = expression.getRelationalOperator();
        this.op = goalIsToSatisfy ? op : op.inverse();

        this.allowNull = allowNull;
        
        valObjFun = new ValueObjectiveFunction();
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        Value lhsValue = lhs.evaluate(row);
        Value rhsValue = rhs.evaluate(row);
        
        ObjectiveValue relObjVal = 
                valObjFun.evaluate(new RelationalPredicate<>(lhsValue, op, rhsValue));
        
        if (allowNull) {
            MultiObjectiveValue multiObjVal =
                    new BestOfMultiObjectiveValue("Allowing for nulls");

            multiObjVal.add(relObjVal);
            multiObjVal.add(NullValueObjectiveFunction.compute(lhsValue, true));
            multiObjVal.add(NullValueObjectiveFunction.compute(rhsValue, true));

            return multiObjVal;
        } else {
            return relObjVal;
        }
    }
}
