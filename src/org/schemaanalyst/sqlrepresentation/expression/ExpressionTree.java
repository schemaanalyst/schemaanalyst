package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionTree implements Expression {
	
	public Expression getSubexpression(List<Integer> indexes) {
		if (indexes.size() == 0) {
			return null;
		}
		int index = indexes.get(0);
		int[] furtherIndexes = new int[indexes.size()-1];
		for (int i=1; i < indexes.size(); i++) {
			furtherIndexes[i] = indexes.get(i);
		}
		return getSubexpression(index, furtherIndexes);
	}
	
	public Expression getSubexpression(int index, int... furtherIndexes) {
		Expression subexpression = getSubexpression(index);
		for (int i : furtherIndexes) {
			subexpression = subexpression.getSubexpression(i);				
		}
		return subexpression;
	}
	
	public abstract Expression getSubexpression(int index);
	
	public abstract int getNumSubexpressions();		
	
	public List<Expression> getSubexpressions() {
		List<Expression> subexpressions = new ArrayList<Expression>();
		for (int i=0; i < getNumSubexpressions(); i++) {
			subexpressions.add(getSubexpression(i));
		}
		return subexpressions;
	}
}
