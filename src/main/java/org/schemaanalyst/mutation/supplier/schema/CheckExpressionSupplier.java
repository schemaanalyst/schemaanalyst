package org.schemaanalyst.mutation.supplier.schema;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * Supplies {@link org.schemaanalyst.sqlrepresentation.expression.Expression}s
 * from a {@link org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint}
 * 
 * @author Phil McMinn
 * 
 */
public class CheckExpressionSupplier extends
		SolitaryComponentSupplier<CheckConstraint, Expression> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putComponentBackInDuplicate(Expression expression) {
		currentDuplicate.setExpression(expression);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Expression getComponent(CheckConstraint checkConstraint) {
		return checkConstraint.getExpression();
	}
}
