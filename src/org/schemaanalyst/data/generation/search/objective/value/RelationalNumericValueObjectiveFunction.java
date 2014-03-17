package org.schemaanalyst.data.generation.search.objective.value;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.search.objective.DistanceObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunctionException;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.logic.RelationalOperator;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

public class RelationalNumericValueObjectiveFunction extends RelationalValueObjectiveFunction<NumericValue> {

    public static final BigDecimal K = ONE;    
    
    public RelationalNumericValueObjectiveFunction(RelationalOperator op, boolean allowNull) {
        super(op, allowNull);
    }    
    
    @Override
    protected ObjectiveValue computeObjectiveValue(NumericValue lhs, NumericValue rhs) {
        DistanceObjectiveValue objVal = new DistanceObjectiveValue(description);
        BigDecimal lhsValue = lhs.get();
        BigDecimal rhsValue = rhs.get();
        BigDecimal distance = null;

        switch (op) {
            case EQUALS:
                if (lhsValue.equals(rhsValue)) {
                    distance = ZERO;
                } else {
                    distance = lhsValue.subtract(rhsValue).abs().add(K);
                }
                break;

            case NOT_EQUALS:
                if (!lhsValue.equals(rhsValue)) {
                    distance = ZERO;
                } else {
                    distance = K;
                }
                break;

            case GREATER:
                if (lhsValue.compareTo(rhsValue) > 0) {
                    distance = ZERO;
                } else {
                    distance = rhsValue.subtract(lhsValue).add(K);
                }
                break;

            case GREATER_OR_EQUALS:
                if (lhsValue.compareTo(rhsValue) >= 0) {
                    distance = ZERO;
                } else {
                    distance = rhsValue.subtract(lhsValue).add(K);
                }
                break;

            case LESS:
                if (lhsValue.compareTo(rhsValue) < 0) {
                    distance = ZERO;
                } else {
                    distance = lhsValue.subtract(rhsValue).add(K);
                }
                break;

            case LESS_OR_EQUALS:
                if (lhsValue.compareTo(rhsValue) <= 0) {
                    distance = ZERO;
                } else {
                    distance = lhsValue.subtract(rhsValue).add(K);
                }
                break;

            default:
                throw new ObjectiveFunctionException("Unknown relational operator " + op);
        }

        objVal.setValueUsingDistance(distance);
        return objVal;
    }
}
