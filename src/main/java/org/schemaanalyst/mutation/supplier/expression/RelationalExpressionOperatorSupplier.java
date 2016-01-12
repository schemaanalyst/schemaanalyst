package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 * <p>
 * A {@link Supplier} class that retrieves the {@link RelationalOperator} from 
 * a {@link RelationalExpression}.
 * </p>
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
