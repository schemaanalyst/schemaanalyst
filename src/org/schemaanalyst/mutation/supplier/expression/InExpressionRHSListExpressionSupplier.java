package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/**
 * 
 * @author Phil McMinn
 *
 */
public class InExpressionRHSListExpressionSupplier extends
		ExpressionSupplier<InExpression> {

	public InExpressionRHSListExpressionSupplier() {
		super(new ExpressionFilter() {
			@Override
			public boolean accept(Expression e) {
				if (e instanceof InExpression) {
					InExpression inExpression = (InExpression) e;
					if (inExpression.getRHS() instanceof ListExpression) {
						return true;
					}
				}
				return false;
			}
		});
	}

}
