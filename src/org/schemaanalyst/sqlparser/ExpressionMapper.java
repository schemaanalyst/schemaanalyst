package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.nodes.TExpression;

import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.expression.Expression;

public class ExpressionMapper {

	Schema schema;
	
	ExpressionMapper(Schema schema) {
		this.schema = schema;
	}	
	
	Expression getExpression(TExpression node) {
		return null;
	}
}
