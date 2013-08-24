package org.schemaanalyst.mutation.supplier.expression;

import java.util.List;

import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

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
