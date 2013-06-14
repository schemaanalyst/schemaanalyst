package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.nodes.TExpression;

import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.expression.Expression;
import org.schemaanalyst.representation.expression.ParenthesisExpression;

public class ExpressionMapper {

	Schema schema;
	
	ExpressionMapper(Schema schema) {
		this.schema = schema;
	}	
	
	Expression getExpression(TExpression node) {
		System.out.println("EXPRESSION: "+node);
		System.out.println("TYPE: "+node.getExpressionType());
		
		EExpressionType expressionType = node.getExpressionType();
		
		if (expressionType == EExpressionType.parenthesis_t) {
			TExpression subNode = node.getLeftOperand();
			return new ParenthesisExpression(getExpression(subNode));
		}
		
		if (expressionType == EExpressionType.simple_comparison_t) {
			TExpression lhsNode = node.getLeftOperand();
			TExpression rhsNode = node.getRightOperand();
			String operatorStr = node.getOperatorToken().toString();
			
			System.out.println("\nLHS:  "+lhsNode+" "+lhsNode.getExpressionType());
			System.out.println("operatorStr: "+node.getExpressionType());
			System.out.println("RHS: "+rhsNode+" "+rhsNode.getExpressionType());
		}
		
		return null;
	}
}
