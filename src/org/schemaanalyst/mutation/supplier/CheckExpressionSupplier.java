package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class CheckExpressionSupplier extends IntermediaryIteratingSupplier<Schema, CheckConstraint, Expression>{

	@Override
	protected List<CheckConstraint> getIntermediaries(Schema schema) {
		return schema.getAllCheckConstraints();
	}

	@Override
	protected Expression getComponentFromIntermediary(Schema schema,
			CheckConstraint checkConstraint) {
		return checkConstraint.getExpression();
	}

	@Override
	public void putComponentBackInIntermediary(CheckConstraint checkConstraint, Expression expression) {
		if (expression == null) {
			currentDuplicate.removeCheckConstraint(checkConstraint);
		} else {
			checkConstraint.setExpression(expression);
		}
	}
}
