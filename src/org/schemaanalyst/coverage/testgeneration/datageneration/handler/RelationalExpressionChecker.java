package org.schemaanalyst.coverage.testgeneration.datageneration.handler;

import org.schemaanalyst.data.CompoundValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.RelationalOperator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 26/02/2014.
 */
public class RelationalExpressionChecker {

    public static boolean check(Value lhs, RelationalOperator op, Value rhs, boolean allowNull) {
        if (lhs == null || rhs == null) {
            return allowNull;
        }

        if (!lhs.getClass().equals(rhs.getClass())) {
            throw new RelationalExpressionCheckException(
                    lhs + "(type + " + lhs.getClass().getSimpleName() + ") is of a different type to " +
                    rhs + "(type + " + rhs.getClass().getSimpleName() + ")");
        }

        if (lhs instanceof CompoundValue) {
            return checkCompoundValue((CompoundValue) lhs, op, (CompoundValue) rhs, allowNull);
        } else {
            return checkNumericValue((NumericValue) lhs, op, (NumericValue) rhs, allowNull);
        }
    }

    public static boolean checkCompoundValue(CompoundValue lhs, RelationalOperator op, CompoundValue rhs, boolean allowNull) {
        if (lhs == null || rhs == null) {
            return allowNull;
        }

        List<Value> lhsValues = lhs.getElements();
        List<Value> rhsValues = rhs.getElements();

        Iterator<Value> lhsIterator = lhsValues.iterator();
        Iterator<Value> rhsIterator = rhsValues.iterator();

        while (lhsIterator.hasNext() && rhsIterator.hasNext()) {
            Value nextLHSValue = lhsIterator.next();
            Value nextRHSValue = rhsIterator.next();

            if (!check(nextLHSValue, RelationalOperator.EQUALS, nextRHSValue, allowNull)) {
                return check(nextLHSValue, op, nextRHSValue, allowNull);
            }
        }

        // all the elements are equal to one another so far ...

        // one value has more elements than the other
        if (lhsIterator.hasNext() || rhsIterator.hasNext()) {

            switch (op) {
                case EQUALS:
                    return false;
                case NOT_EQUALS:
                    return true;
                case GREATER:
                case GREATER_OR_EQUALS:
                    return lhsIterator.hasNext();
                case LESS:
                case LESS_OR_EQUALS:
                    return rhsIterator.hasNext();
                default:
                    throw new RelationalExpressionCheckException("Unknown relational operator " + op);
            }
        }

        // the values have the same number of elements (and are equal)
        switch (op) {
            case EQUALS:
            case GREATER_OR_EQUALS:
            case LESS_OR_EQUALS:
                return true;
            case NOT_EQUALS:
            case GREATER:
            case LESS:
                return false;
            default:
                throw new RelationalExpressionCheckException("Unknown relational operator " + op);
        }
    }

    public static boolean checkNumericValue(NumericValue lhs, RelationalOperator op, NumericValue rhs, boolean allowNull) {
        if (lhs == null || rhs == null) {
            return allowNull;
        }

        BigDecimal lhsValue = lhs.get();
        BigDecimal rhsValue = rhs.get();

        switch (op) {
            case EQUALS:
                return lhsValue.equals(rhsValue);

            case NOT_EQUALS:
                return !lhsValue.equals(rhsValue);

            case GREATER:
                return lhsValue.compareTo(rhsValue) > 0;

            case GREATER_OR_EQUALS:
                return lhsValue.compareTo(rhsValue) >= 0;

            case LESS:
                return lhsValue.compareTo(rhsValue) < 0;

            case LESS_OR_EQUALS:
                return lhsValue.compareTo(rhsValue) <= 0;

            default:
                throw new RelationalExpressionCheckException("Unknown relational operator " + op);
        }

    }
}
