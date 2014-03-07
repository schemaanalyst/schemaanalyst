package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

import java.util.List;

/**
 * <p>
 * A {@link Supplier} class that retrieves the {@link ListExpression} from the 
 * RHS of a {@link InExpression}.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class InExpressionRHSListExpressionSubexpressionsSupplier extends
		SolitaryComponentSupplier<InExpression, List<Expression>> {

	@Override
	public void putComponentBackInDuplicate(List<Expression> expressionList) {
		((ListExpression) currentDuplicate.getRHS()).setSubexpressions(expressionList);		
	}

	@Override
	protected List<Expression> getComponent(InExpression inExpression) {
		return ((ListExpression) inExpression.getRHS()).getSubexpressions();
	}
}
