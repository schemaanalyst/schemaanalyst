package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 18/07/2014.
 */
public class ExpressionPredicate extends Predicate {

    private Expression expression;
    private boolean truthValue;

    public ExpressionPredicate(Table table, Expression expression, boolean truthValue) {
        super(table);
        this.expression = expression;
        this.truthValue = truthValue;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean getTruthValue() {
        return truthValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExpressionPredicate other = (ExpressionPredicate) o;

        if (truthValue != other.truthValue) return false;
        if (!expression.equals(other.expression)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + expression.hashCode();
        result = 31 * result + (truthValue ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return (!truthValue ? "\u00AC" : "") + "Exp(" + table + ": " + expression.toString() + ")";
    }
}
