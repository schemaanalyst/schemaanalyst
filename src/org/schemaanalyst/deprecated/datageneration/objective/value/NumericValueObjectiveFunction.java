package org.schemaanalyst.deprecated.datageneration.objective.value;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.ONE;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.datageneration.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

public class NumericValueObjectiveFunction extends ObjectiveFunction<RelationalPredicate<NumericValue>> {

    public static final BigDecimal K = ONE;

    @Override
    public ObjectiveValue evaluate(RelationalPredicate<NumericValue> predicate) {
        BigDecimal lhs = predicate.getLHS().get();
        RelationalOperator op = predicate.getOperator();
        BigDecimal rhs = predicate.getRHS().get();

        DistanceObjectiveValue objVal = new DistanceObjectiveValue(lhs + " " + op + " " + rhs);
        objVal.setValueUsingDistance(computeDistance(lhs, op, rhs));
        return objVal;
    }

    protected BigDecimal computeDistance(BigDecimal lhs, RelationalOperator op, BigDecimal rhs) {
        BigDecimal distance = null;

        switch (op) {
            case EQUALS:
                if (lhs.equals(rhs)) {
                    distance = ZERO;
                } else {
                    distance = lhs.subtract(rhs).abs().add(K);
                }
                break;

            case NOT_EQUALS:
                if (!lhs.equals(rhs)) {
                    distance = ZERO;
                } else {
                    distance = K;
                }
                break;

            case GREATER:
                if (lhs.compareTo(rhs) > 0) {
                    distance = ZERO;
                } else {
                    distance = rhs.subtract(lhs).add(K);
                }
                break;

            case GREATER_OR_EQUALS:
                if (lhs.compareTo(rhs) >= 0) {
                    distance = ZERO;
                } else {
                    distance = rhs.subtract(lhs).add(K);
                }
                break;

            case LESS:
                if (lhs.compareTo(rhs) < 0) {
                    distance = ZERO;
                } else {
                    distance = lhs.subtract(rhs).add(K);
                }
                break;

            case LESS_OR_EQUALS:
                if (lhs.compareTo(rhs) <= 0) {
                    distance = ZERO;
                } else {
                    distance = lhs.subtract(rhs).add(K);
                }
                break;

            default:
                throw new ObjectiveFunctionException("Unknown relational operator " + op);
        }

        return distance;
    }

    public static ObjectiveValue compute(NumericValue lhs, RelationalOperator op, NumericValue rhs) {
        NumericValueObjectiveFunction objFun = new NumericValueObjectiveFunction();
        return objFun.evaluate(new RelationalPredicate<>(lhs, op, rhs));
    }
}
