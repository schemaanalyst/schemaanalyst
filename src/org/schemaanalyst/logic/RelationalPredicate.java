package org.schemaanalyst.logic;

public class RelationalPredicate<T> {

    protected T lhs, rhs;
    protected RelationalOperator operator;

    public RelationalPredicate(T lhs, RelationalOperator operator, T rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public RelationalPredicate(T lhs, String operator, T rhs) {
        this(lhs, RelationalOperator.getRelationalOperator(operator), rhs);
    }

    public T getLHS() {
        return lhs;
    }

    public RelationalOperator getOperator() {
        return operator;
    }

    public T getRHS() {
        return rhs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        @SuppressWarnings("unchecked")
        RelationalPredicate<T> other = (RelationalPredicate<T>) obj;
        if (lhs == null) {
            if (other.lhs != null) {
                return false;
            }
        } else if (!lhs.equals(other.lhs)) {
            return false;
        }

        if (operator != other.operator) {
            return false;
        }

        if (rhs == null) {
            if (other.rhs != null) {
                return false;
            }
        } else if (!rhs.equals(other.rhs)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }
}
