package org.schemaanalyst.coverage.criterion.clause;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 19/01/2014.
 */
public class ExpressionClause extends Clause {

    private Expression expression;
    private boolean satisfy;

    public ExpressionClause(Table table, Expression expression, boolean satisfy) {
        super(table);
        this.expression = expression;
        this.satisfy = satisfy;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean requiresComparisonRow() {
        return false;
    }

    public boolean getSatisfy() {
        return satisfy;
    }

    public String getName() {
        return (!satisfy ? "\u00AC" : "") + "Exp";
    }

    protected String paramsToString() {
        return table + " " + expression.toString();
    }

    public ExpressionClause duplicate() {
        Expression duplicateExpression = expression.duplicate();
        return new ExpressionClause(table, duplicateExpression, satisfy);
    }

    public void accept(ClauseVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExpressionClause that = (ExpressionClause) o;

        if (satisfy != that.satisfy) return false;
        if (!expression.equals(that.expression)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + expression.hashCode();
        result = 31 * result + (satisfy ? 1 : 0);
        return result;
    }
}
