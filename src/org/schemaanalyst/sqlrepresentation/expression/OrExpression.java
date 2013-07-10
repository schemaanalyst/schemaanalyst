package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public class OrExpression extends CompoundExpression {

	public OrExpression(List<Expression> subexpressions) {
		super(subexpressions);
	}		
	
	public OrExpression(Expression... subexpressions) {
		super(subexpressions);
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString() {
		return toString(" OR ");
	}
}
