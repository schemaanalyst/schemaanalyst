package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.data.Value;

public class ConstantExpression extends ExpressionLeaf {

    protected Value value;

    public ConstantExpression(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
