package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class CheckExpressionSupplier extends SolitaryComponentSupplier<CheckConstraint, Expression> {

    @Override
    public void putComponentBackInDuplicate(Expression expression) {
        currentDuplicate.setExpression(expression);
    }

    @Override
    protected Expression getComponent(CheckConstraint checkConstraint) {
        return checkConstraint.getExpression();
    }
}
