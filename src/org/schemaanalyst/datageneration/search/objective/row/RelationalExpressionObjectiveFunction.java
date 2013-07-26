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
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionObjectiveFunction extends ObjectiveFunction<Row> {

    private ExpressionEvaluator lhsEvaluator, rhsEvaluator;
    private RelationalOperator op;
    private boolean allowNull;
    
    public RelationalExpressionObjectiveFunction(RelationalExpression expression,
                                                 boolean goalIsToSatisfy,
                                                 boolean allowNull) {
        
        lhsEvaluator = new ExpressionEvaluator(expression.getLHS());
        rhsEvaluator = new ExpressionEvaluator(expression.getRHS());

        RelationalOperator op = expression.getRelationalOperator();
        this.op = goalIsToSatisfy ? op : op.inverse();

        this.allowNull = allowNull;       
    }

    @Override
    public ObjectiveValue evaluate(Row row) {
        Value lhsValue = lhsEvaluator.evaluate(row);
        Value rhsValue = rhsEvaluator.evaluate(row);        
        ObjectiveValue objVal = ValueObjectiveFunction.compute(lhsValue, op, rhsValue);
        
        if (allowNull) {
            MultiObjectiveValue multiObjVal = new BestOfMultiObjectiveValue();
            multiObjVal.add(NullValueObjectiveFunction.compute(lhsValue, true));
            multiObjVal.add(NullValueObjectiveFunction.compute(rhsValue, true));
            multiObjVal.add(objVal);
            objVal = multiObjVal;
        }         
        return objVal;
    }
}
