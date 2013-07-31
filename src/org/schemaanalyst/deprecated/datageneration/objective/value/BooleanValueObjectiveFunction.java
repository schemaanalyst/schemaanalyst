package org.schemaanalyst.deprecated.datageneration.objective.value;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.deprecated.logic.RelationalPredicate;
import org.schemaanalyst.logic.RelationalOperator;

public class BooleanValueObjectiveFunction extends
        ObjectiveFunction<RelationalPredicate<BooleanValue>> {

    @Override
    public ObjectiveValue evaluate(RelationalPredicate<BooleanValue> predicate) {
        BooleanValue lhs = predicate.getLHS();
        BooleanValue rhs = predicate.getRHS();
        RelationalOperator op = predicate.getOperator();

        ObjectiveValue objVal = new ObjectiveValue(lhs + " " + op + " " + rhs);

        switch (op) {
        case EQUALS:
            if (lhs.get() == rhs.get()) {
                objVal.setValueToOptimal();
            } else {
                objVal.setValueToWorst();
            }
            break;

        case NOT_EQUALS:
            if (lhs.get() != rhs.get()) {
                objVal.setValueToOptimal();
            } else {
                objVal.setValueToWorst();
            }
            break;

        default:
            throw new ObjectiveFunctionException("Cannot use " + op
                    + " with two boolean values");
        }

        return objVal;
    }

    public static ObjectiveValue compute(BooleanValue lhs,
            RelationalOperator op, BooleanValue rhs) {
        BooleanValueObjectiveFunction objFun = new BooleanValueObjectiveFunction();
        return objFun.evaluate(new RelationalPredicate<>(lhs, op, rhs));
    }
}
