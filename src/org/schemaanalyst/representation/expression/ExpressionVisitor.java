package org.schemaanalyst.representation.expression;

public interface ExpressionVisitor {
	
	public void visit(BetweenExpression predicate);
	
	public void visit(InExpression predicate);
	
	public void visit(RelationalExpression predicate);
}
