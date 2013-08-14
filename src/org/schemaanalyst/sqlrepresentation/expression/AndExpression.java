package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

import org.schemaanalyst.util.StringUtils;

public class AndExpression extends CompoundExpression {

    public AndExpression(List<Expression> subexpressions) {
        super(subexpressions);
    }

    public AndExpression(Expression... subexpressions) {
        super(subexpressions);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public AndExpression duplicate() {
        return new AndExpression(duplicateSubexpressions());
    }
    
    @Override
    public String toString() {
        return StringUtils.implode(subexpressions, " AND ");
    }
}
