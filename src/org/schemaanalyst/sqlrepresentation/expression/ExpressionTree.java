package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
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
				expression = ((ExpressionTree) expression).getSubexpression(i);				
			} else {
				throw new NonExistentSubexpressionException(
						"Cannot get subexpressions for expression \"" + expression + 
						"\" -- its not an ExpressionTree");
			}			
		}
		return expression;
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
