package org.schemaanalyst.deprecated.datageneration.objective.constraint.checkcondition;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.OperandToValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.deprecated.datageneration.objective.value.ValueObjectiveFunction;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Table;

public class RelationalCheckConditionObjectiveFunction extends ObjectiveFunction<Data> {

    protected RelationalCheckCondition relation;
    protected Table table;
    protected Data state;
    protected String description;
    protected boolean goalIsToSatisfy, allowNull;

    public RelationalCheckConditionObjectiveFunction(RelationalCheckCondition relation,
            Table table,
            Data state,
            String description,
            boolean goalIsToSatisfy,
            boolean allowNull) {
        this.relation = relation;
        this.table = table;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        for (int i = 0; i < data.getNumRows(table); i++) {

            Value lhsValue = OperandToValue.convert(relation.getLHS(), data, i);
            Value rhsValue = OperandToValue.convert(relation.getRHS(), data, i);

            RelationalOperator op = relation.getOperator();
            if (!goalIsToSatisfy) {
                op = op.inverse();
            }

            objVal.add(evaluateRow(lhsValue, op, rhsValue, allowNull));
        }

        return objVal;
    }

    protected ObjectiveValue evaluateRow(Value lhsValue, RelationalOperator op, Value rhsValue, boolean evaluateNull) {
        if (evaluateNull) {
            MultiObjectiveValue rowObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");

            rowObjVal.add(evaluateRow(lhsValue, op, rhsValue, false));
            rowObjVal.add(NullValueObjectiveFunction.compute(lhsValue, true));
            rowObjVal.add(NullValueObjectiveFunction.compute(rhsValue, true));

            return rowObjVal;
        } else {
            return ValueObjectiveFunction.compute(lhsValue, op, rhsValue);
        }
    }
}
