package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CompoundExpression extends ExpressionTree {

    protected List<Expression> subexpressions;

    public CompoundExpression(Expression... subexpressions) {
        this(Arrays.asList(subexpressions));
    }
    
    public CompoundExpression(List<Expression> subexpressions) {
        setSubexpressions(subexpressions);
    }

    @Override
    public List<Expression> getSubexpressions() {
        return Collections.unmodifiableList(subexpressions);
    }
    
    @Override
    public int getNumSubexpressions() {
        return subexpressions.size();
    }

    @Override
    public Expression getSubexpression(int index) {
        if (index < getNumSubexpressions()) {
            return subexpressions.get(index);
        }
        throw new NonExistentSubexpressionException(this, index);
    }
    
    @Override
    public void setSubexpression(int index, Expression subexpression) {
        subexpressions.set(index, subexpression);
    }    
    
    public void setSubexpressions(List<Expression> subexpressions) {
        this.subexpressions = new ArrayList<>(subexpressions);
    }
    
    protected List<Expression> duplicateSubexpressions() {
        List<Expression> duplicateSubexpressions = new ArrayList<>();
        for (Expression expression : subexpressions) {
            duplicateSubexpressions.add(expression.duplicate());
        }
        return duplicateSubexpressions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((subexpressions == null) ? 0 : subexpressions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompoundExpression other = (CompoundExpression) obj;
        if (subexpressions == null) {
            if (other.subexpressions != null)
                return false;
        } else if (!subexpressions.equals(other.subexpressions))
            return false;
        return true;
    }
}
