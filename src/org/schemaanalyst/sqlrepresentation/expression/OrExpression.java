package org.schemaanalyst.sqlrepresentation.expression;

import java.util.Arrays;
import java.util.List;

import org.schemaanalyst.util.StringUtils;

public class OrExpression extends CompoundExpression {

    public OrExpression(Expression... subexpressions) {
        this(Arrays.asList(subexpressions));
    }    
    
    public OrExpression(List<Expression> subexpressions) {
        super(subexpressions);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public OrExpression duplicate() {
        return new OrExpression(duplicateSubexpressions());
    }    
    
    @Override
    public String toString() {
        return StringUtils.implode(subexpressions, " OR ");        
    }
}
