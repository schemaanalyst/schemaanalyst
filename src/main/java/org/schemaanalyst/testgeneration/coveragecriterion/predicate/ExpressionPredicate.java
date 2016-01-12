package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 18/07/2014.
 */
public class ExpressionPredicate extends Predicate {

    private Table table;
    private Expression expression;
    private boolean truthValue;

    public ExpressionPredicate(Table table, Expression expression, boolean truthValue) {
        this.table = table;
        this.expression = expression;
        this.truthValue = truthValue;
    }

    public Table getTable() {
        return table;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean getTruthValue() {
        return truthValue;
    }

    @Override
    public void accept(PredicateVisitor predicateVisitor) {
        predicateVisitor.visit(this);
    }

    @Override
    public String toString() {
        return (!truthValue ? "\u00AC" : "") + "Exp[" + table + ": " + expression.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpressionPredicate that = (ExpressionPredicate) o;

        if (truthValue != that.truthValue) return false;
        if (!expression.equals(that.expression)) return false;
        if (!table.equals(that.table)) return false;

        return true;
    }

    @Override
    // This has been modified from the auto-generated version to return a more different hashcode
    // subcomponent for truthValue (previously it just added 1 if the truthValue was true)
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + expression.hashCode();
        result = 31 * result * (truthValue ? 3 : 1);
        return result;
    }
}
