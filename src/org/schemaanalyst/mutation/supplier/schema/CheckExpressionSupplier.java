package org.schemaanalyst.mutation.supplier.schema;

import java.util.List;

import org.schemaanalyst.mutation.supplier.IntermediaryIteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

public class CheckExpressionSupplier extends IntermediaryIteratingSupplier<Schema, CheckConstraint, Expression>{

    public CheckExpressionSupplier() {
        super(new Schema.Duplicator());
    }    
    
	@Override
	protected List<CheckConstraint> getIntermediaries(Schema schema) {
		return schema.getCheckConstraints();
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
