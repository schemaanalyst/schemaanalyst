package org.schemaanalyst.sqlrepresentation.expression;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

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
        return StringUtils.join(subexpressions, " OR ");        
    }
}
