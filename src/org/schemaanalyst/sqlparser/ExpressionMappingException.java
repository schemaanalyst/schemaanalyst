package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TExpression;

@SuppressWarnings("serial")
public class ExpressionMappingException extends RuntimeException {
		
	public ExpressionMappingException(TExpression node) {
		super("Cannot resolve expression: \"" + node + "\" [GSP EExpressionType: " + node.getExpressionType() + "]");
	}
}
