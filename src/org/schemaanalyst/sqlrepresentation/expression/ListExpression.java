package org.schemaanalyst.sqlrepresentation.expression;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ListExpression extends CompoundExpression {

    public ListExpression(Expression... subexpressions) {
        this(Arrays.asList(subexpressions));
    }    
    
    public ListExpression(List<Expression> subexpressions) {
        super(subexpressions);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public ListExpression duplicate() {
        return new ListExpression(duplicateSubexpressions());
    }     

    @Override
    public String toString() {
        return "(" + StringUtils.join(subexpressions, ", ") + ")";        
    }
}
