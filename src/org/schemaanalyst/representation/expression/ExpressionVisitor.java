package org.schemaanalyst.representation.expression;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.representation.Column;

public interface ExpressionVisitor {

	public void visit(AndExpression expression);

	public void visit(BetweenExpression expression);	
	
	public void visit(Column expression);	

	public void visit(InExpression expression);	
	
	public void visit(ListExpression expression);	
	
	public void visit(NullExpression expression);

	public void visit(OrExpression expression);	
	
	public void visit(ParenthesisedExpression expression);
	
	public void visit(RelationalExpression expression);
	
	public void visit(Value expression);	
}


