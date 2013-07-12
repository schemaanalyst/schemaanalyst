package org.schemaanalyst.datageneration.search.objective.constraint.checkcondition;

import static org.schemaanalyst.logic.RelationalOperator.GREATER;
import static org.schemaanalyst.logic.RelationalOperator.GREATER_OR_EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.LESS;
import static org.schemaanalyst.logic.RelationalOperator.LESS_OR_EQUALS;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.NullValueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.ValueObjectiveFunction;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.sqlrepresentation.Table;

public class BetweenCheckConditionObjectiveFunction extends ObjectiveFunction<Data> {

    protected BetweenCheckCondition between;
    protected Table table;
    protected Data state;
    protected String description;
    protected boolean goalIsToSatisfy, allowNull;

    public BetweenCheckConditionObjectiveFunction(BetweenCheckCondition between,
            Table table,
            Data state,
            String description,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.between = between;
        this.table = table;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
    }

    public ObjectiveValue evaluate(Data data) {
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        for (int i = 0; i < data.getNumRows(table); i++) {
            Value lowerValue = OperandToValue.convert(between.getLower(), data, i);
            Value upperValue = OperandToValue.convert(between.getUpper(), data, i);
            Value columnValue = OperandToValue.convert(between.getColumn(), data, i);

            objVal.add(evaluateRow(lowerValue, upperValue, columnValue, allowNull));
        }

        return objVal;
    }

    protected ObjectiveValue evaluateRow(Value lowerValue, Value upperValue, Value columnValue, boolean evaluateNull) {
        MultiObjectiveValue objVal;
        String description = "Evaluating row. Column Value: " + columnValue
                + " Lower Value: " + lowerValue
                + " Upper Value: " + upperValue
                + " Null allowed: " + evaluateNull;

        if (evaluateNull) {
            objVal = new BestOfMultiObjectiveValue(description);

            objVal.add(evaluateRow(lowerValue, upperValue, columnValue, false));
            objVal.add(NullValueObjectiveFunction.compute(columnValue, true));

            ObjectiveValue boundObjVal = goalIsToSatisfy
                    ? NullValueObjectiveFunction.compute(lowerValue, true)
                    : NullValueObjectiveFunction.compute(upperValue, true);

            objVal.add(boundObjVal);
        } else {
            if (goalIsToSatisfy) {
                objVal = new SumOfMultiObjectiveValue(description);
                objVal.add(ValueObjectiveFunction.compute(columnValue, GREATER_OR_EQUALS, lowerValue));
                objVal.add(ValueObjectiveFunction.compute(columnValue, LESS_OR_EQUALS, upperValue));
            } else {
                objVal = new BestOfMultiObjectiveValue(description);
                objVal.add(ValueObjectiveFunction.compute(columnValue, LESS, lowerValue));
                objVal.add(ValueObjectiveFunction.compute(columnValue, GREATER, upperValue));
            }
        }

        return objVal;
    }
}
