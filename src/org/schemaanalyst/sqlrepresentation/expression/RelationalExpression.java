package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.logic.RelationalOperator;

public class RelationalExpression implements Expression {
	
	protected Expression lhs, rhs;
	protected RelationalOperator op;
	
	public RelationalExpression(Expression lhs, RelationalOperator op, Expression rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}
	
	public Expression getLHS() {
		return lhs;
	}
	
	public RelationalOperator getRelationalOperator() {
		return op;
	}
	
	public Expression getRHS() {
		return rhs;
	}

	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}	
}
