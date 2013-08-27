package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
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
