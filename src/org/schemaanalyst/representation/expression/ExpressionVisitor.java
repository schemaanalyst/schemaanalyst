package org.schemaanalyst.representation.expression;

public interface ExpressionVisitor {
	
	public void visit(BetweenExpression expression);
	
	public void visit(InExpression expression);	
	
	public void visit(RelationalExpression expression);
}
