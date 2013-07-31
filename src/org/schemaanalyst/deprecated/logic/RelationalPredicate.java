package org.schemaanalyst.deprecated.logic;

import org.schemaanalyst.logic.RelationalOperatorException;
import org.schemaanalyst.logic.RelationalOperator;

public class RelationalPredicate<T extends Comparable<T>> {

    protected T lhs, rhs;
    protected RelationalOperator operator;    
    
    public RelationalPredicate(T lhs, RelationalOperator operator, T rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
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
                throw new RelationalOperatorException("Unknown relational operator " + this);
        }
    }
    

    @Override
    public String toString() {
        return lhs + " " + operator + " " + rhs;
    }    
}
