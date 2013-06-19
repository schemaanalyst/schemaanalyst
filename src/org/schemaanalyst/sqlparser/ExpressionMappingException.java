package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TExpression;

@SuppressWarnings("serial")
public class ExpressionMappingException extends RuntimeException {
		
	public ExpressionMappingException(TExpression node) {
		super("Expression: \"" + node + "\" is not supported [GSP EExpressionType: " + node.getExpressionType() + "]");
	}
}
