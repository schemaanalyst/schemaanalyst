package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

import org.schemaanalyst.util.StringUtils;

public class OrExpression extends CompoundExpression {

    public OrExpression(List<Expression> subexpressions) {
        super(subexpressions);
    }

    public OrExpression(Expression... subexpressions) {
        super(subexpressions);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return StringUtils.implode(subexpressions, " OR ");        
    }
}
