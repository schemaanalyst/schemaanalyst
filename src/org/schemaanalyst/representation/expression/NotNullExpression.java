package org.schemaanalyst.representation.expression;

public class NotNullExpression implements Expression {

	private static final long serialVersionUID = 4658484520631453173L;

	private Operand operand;
	
	public NotNullExpression(Operand operand) {
		this.operand = operand;
	}
	
	public Operand getOperand() {
		return operand;
	}
	
	public void accept(ExpressionVisitor visitor) {
		// visitor.visit(this);	
	}
}
