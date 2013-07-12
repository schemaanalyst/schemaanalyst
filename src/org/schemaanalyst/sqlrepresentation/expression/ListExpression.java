package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public class ListExpression extends CompoundExpression {

    public ListExpression(List<Expression> subexpressions) {
        super(subexpressions);
    }

    public ListExpression(Expression... subexpressions) {
        super(subexpressions);
    }

    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return toString(", ");
    }
}
