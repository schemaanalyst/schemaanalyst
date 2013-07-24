package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.sqlrepresentation.Column;

public class ColumnExpression extends ExpressionLeaf {

    protected Column column;

    public ColumnExpression(Column column) {
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
