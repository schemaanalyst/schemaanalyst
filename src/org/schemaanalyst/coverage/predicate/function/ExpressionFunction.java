package org.schemaanalyst.coverage.predicate.function;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Created by phil on 19/01/2014.
 */
public class ExpressionFunction extends Function {

    private Expression expression;

    public ExpressionFunction(Table table, Expression expression) {
        super(table, expression.getColumnsInvolved());
        this.expression = expression;
    }

    public String toString() {
        return "exp(" + expression + ")";
    }

}
