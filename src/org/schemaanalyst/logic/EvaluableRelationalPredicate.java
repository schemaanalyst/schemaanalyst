package org.schemaanalyst.logic;

public class EvaluableRelationalPredicate<T extends Comparable<T>> extends RelationalPredicate<T> {

    public EvaluableRelationalPredicate(T lhs, RelationalOperator operator, T rhs) {
        super(lhs, operator, rhs);
    }

   public Boolean isSatisfied3VL() {
        if (lhs == null || rhs == null) {
            return null;
        }
        return isSatisfied();
    }

    public boolean isSatisfied() {
        int result = lhs.compareTo(rhs);

        switch (operator) {
            case EQUALS:
                return (result == 0);
            case GREATER:
                return (result > 0);
            case GREATER_OR_EQUALS:
                return (result >= 0);
            case LESS:
                return (result < 0);
            case LESS_OR_EQUALS:
                return (result <= 0);
            case NOT_EQUALS:
                return (result != 0);
            default:
                throw new LogicException("Unknown relational operator " + this);
        }
    }
}
