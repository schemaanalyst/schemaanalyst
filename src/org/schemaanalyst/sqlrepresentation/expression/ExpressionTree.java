package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public abstract class ExpressionTree implements Expression {
	
	public Expression getSubexpression(List<Integer> indexes) {
		Integer[] indexesArray = indexes.toArray(new Integer[indexes.size()]);
		return getSubexpression(indexesArray);
	}
	
	public Expression getSubexpression(Integer... indexes) {
		Expression expression = this;
		for (Integer i : indexes) {
			if (expression instanceof ExpressionTree) {
				ExpressionTree subTree = (ExpressionTree) expression;
				if (i < subTree.getNumSubexpressions()) {
					expression = subTree.getSubexpression(i);
				} else {
					throw new RuntimeException();
				}
			} else {
				throw new RuntimeException();
			}			
		}
		return expression;
	}
	
	public abstract Expression getSubexpression(int index);
	
	public abstract int getNumSubexpressions();		
}
