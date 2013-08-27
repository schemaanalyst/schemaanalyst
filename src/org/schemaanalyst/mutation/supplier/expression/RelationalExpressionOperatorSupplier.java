package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 * 
 * @author Phil McMinn
 *
 */
public class RelationalExpressionOperatorSupplier extends
		SolitaryComponentSupplier<RelationalExpression, RelationalOperator> {

	@Override
	public void putComponentBackInDuplicate(RelationalOperator relationalOperator) {
		currentDuplicate.setRelationalOperator(relationalOperator);
	}

	@Override
	protected RelationalOperator getComponent(RelationalExpression relationalExpression) {
		return relationalExpression.getRelationalOperator();
	}
}
