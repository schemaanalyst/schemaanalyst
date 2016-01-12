package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 * <p>
 * A {@link Supplier} class that returns {@link Expression}s if they are 
 * instances of {@link RelationalExpression}.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class RelationalExpressionSupplier extends ExpressionSupplier<RelationalExpression> {

	public RelationalExpressionSupplier() {
		super(new ExpressionFilter() {
	        @Override
	        public boolean accept(Expression e) {
	            return (e instanceof RelationalExpression);
	        }
	    });
	}	
}
