package org.schemaanalyst.sqlrepresentation.expression;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class OrExpression extends CompoundExpression {
    
    private static final long serialVersionUID = -7248930568916280298L;

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
        OrExpression other = (OrExpression) obj;
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
        int result = OrExpression.class.getName().hashCode();
        result = prime * result + ((subexpressions == null) ? 0 : subexpressions.hashCode());
        return result;
    }
}
