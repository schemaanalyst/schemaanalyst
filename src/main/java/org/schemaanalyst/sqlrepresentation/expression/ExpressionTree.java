package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.*;

public abstract class ExpressionTree implements Expression {

    @Override
    public Expression getSubexpression(ExpressionPath expressionPath) {
        List<Integer> indices = expressionPath.getIndices();
        Expression expression = this;
        Iterator<Integer> iterator = indices.iterator();
        while (iterator.hasNext()) {
        	expression = expression.getSubexpression(iterator.next());        	
        }
        return expression;
    }    

    @Override
    public abstract Expression getSubexpression(int index);
    
    @Override
    public abstract void setSubexpression(int index, Expression subexpression);
    
    @Override
    public void setSubexpressions(List<Expression> subexpressions) {
    	if (subexpressions.size() > getNumSubexpressions()) {
    		throw new NonExistentSubexpressionException(this, subexpressions.size());
    	}
    	for (int i=0; i < subexpressions.size(); i++) {
    		setSubexpression(i, subexpressions.get(i));
    	}
    }
    
    @Override
    public abstract int getNumSubexpressions();

    @Override
    public List<Expression> getSubexpressions() {
        List<Expression> subexpressions = new ArrayList<>();
        for (int i = 0; i < getNumSubexpressions(); i++) {
            subexpressions.add(getSubexpression(i));
        }
        return subexpressions;
    }
    
    @Override
    public List<Column> getColumnsInvolved() {
        Set<Column> columns = new HashSet<>();
        for (Expression expression : getSubexpressions()) {
            columns.addAll(expression.getColumnsInvolved());
        }
        return new ArrayList<>(columns);
    }

    @Override
    public void remap(Table table) {
        for (Expression expression : getSubexpressions()) {
            expression.remap(table);
        }
    }
}
