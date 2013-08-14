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
    
    public String toString() {
        return (value == null) ? "NULL" : value.toString();
    }
}
