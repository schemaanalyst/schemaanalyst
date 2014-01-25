package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 19/01/2014.
 */
public class ExpressionFunction extends Function {

    private Expression expression;
    private boolean satisfy;

    public ExpressionFunction(Table table, Expression expression, boolean satisfy) {
        super(table);
        this.expression = expression;
        this.satisfy = satisfy;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean getSatisfy() {
        return satisfy;
    }

    public String getName() {
        return (!satisfy ? "\u00AC" : "") + "Exp";
    }

    protected String argumentsToString() {
        return expression.toString();
    }
}
