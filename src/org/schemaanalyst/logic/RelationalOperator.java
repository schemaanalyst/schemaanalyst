package org.schemaanalyst.logic;

public enum RelationalOperator {

    EQUALS,
    NOT_EQUALS,
    GREATER,
    LESS,
    GREATER_OR_EQUALS,
    LESS_OR_EQUALS;

    @Override
    public String toString() {
        if (this == EQUALS) {
            return "=";
        } else if (this == NOT_EQUALS) {
            return "!=";
        } else if (this == GREATER) {
            return ">";
        } else if (this == GREATER_OR_EQUALS) {
            return ">=";
        } else if (this == LESS) {
            return "<";
        } else if (this == LESS_OR_EQUALS) {
            return "<=";
        } else {
            throw new RelationalOperatorException("Unknown relational operator " + this);
        }
    }

    public static RelationalOperator getRelationalOperator(String string) {
        switch (string) {
            case "=":
                return EQUALS;
            case "!=":
                return NOT_EQUALS;
            case ">":
                return GREATER;
            case ">=":
                return GREATER_OR_EQUALS;
            case "<":
                return LESS;
            case "<=":
                return LESS_OR_EQUALS;
            default:
                throw new RelationalOperatorException("\"" + string + "\" is not a valid relational operator");
        }
    }

    public RelationalOperator inverse() {
        if (this == EQUALS) {
            return NOT_EQUALS;
        } else if (this == NOT_EQUALS) {
            return EQUALS;
        } else if (this == GREATER) {
            return LESS_OR_EQUALS;
        } else if (this == GREATER_OR_EQUALS) {
            return LESS;
        } else if (this == LESS) {
            return GREATER_OR_EQUALS;
        } else if (this == LESS_OR_EQUALS) {
            return GREATER;
        } else {
            throw new RelationalOperatorException("Unknown relational operator " + this);
        }
    }
}
