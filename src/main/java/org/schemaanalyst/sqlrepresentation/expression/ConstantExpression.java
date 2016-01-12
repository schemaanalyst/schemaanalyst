package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.data.Value;

public class ConstantExpression extends ExpressionLeaf {

    private Value value;

    public ConstantExpression(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
    
    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public ConstantExpression duplicate() {
        return new ConstantExpression(value.duplicate());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        ConstantExpression other = (ConstantExpression) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    public String toString() {
        return (value == null) ? "NULL" : value.toString();
    }
}
