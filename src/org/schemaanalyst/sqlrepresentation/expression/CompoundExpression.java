package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CompoundExpression extends ExpressionTree {

	protected List<Expression> subexpressions;
	
	public CompoundExpression(List<Expression> subexpressions) {
		this.subexpressions = new ArrayList<>();
		for (Expression expression : subexpressions) {
			this.subexpressions.add(expression);
		}
	}
	
	public CompoundExpression(Expression... subexpressions) {
		this.subexpressions = Arrays.asList(subexpressions);
	}
	
	public List<Expression> getSubexpressions() {
		return Collections.unmodifiableList(subexpressions);
	}
	
	public int getNumSubexpressions() {
		return subexpressions.size();
	}
	
	public Expression getSubexpression(int index) {
		if (index < getNumSubexpressions()) {
			return subexpressions.get(index);
		} 
		throw new NonExistentSubexpressionException(this, index);
	}
	
	public abstract void accept(ExpressionVisitor visitor);
	
	public String toString(String separator) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Expression expression : subexpressions) {
			if (first) {
				first = false;
			} else {
				sb.append(separator);
			}
			sb.append(expression.toString());
		}
		return sb.toString();
	}
}
