package org.schemaanalyst.sqlrepresentation.expression;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ListExpression extends CompoundExpression {
    
    private static final long serialVersionUID = -1439840191933978361L;

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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ListExpression other = (ListExpression) obj;
        if (subexpressions == null) {
            if (other.subexpressions != null) {
                return false;
            }
        } else if (!subexpressions.equals(other.subexpressions)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = ListExpression.class.getName().hashCode();
        result = prime * result + ((subexpressions == null) ? 0 : subexpressions.hashCode());
        return result;
    }
}
