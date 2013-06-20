package org.schemaanalyst.sqlrepresentation.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ComposedExpression implements Expression {

	protected List<Expression> subexpressions;
	
	public ComposedExpression(Expression... subexpressions) {
		this.subexpressions = Arrays.asList(subexpressions);
	}
	
	public List<Expression> getSubexpressions() {
		return Collections.unmodifiableList(subexpressions);
	}
	
	public abstract void accept(ExpressionVisitor visitor);
}
