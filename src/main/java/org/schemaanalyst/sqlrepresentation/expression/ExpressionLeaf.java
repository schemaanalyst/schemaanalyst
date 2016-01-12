package org.schemaanalyst.sqlrepresentation.expression;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionLeaf implements Expression {

    @Override
    public Expression getSubexpression(ExpressionPath expressionPath) {
        List<Integer> indices = expressionPath.getIndices();
        if (indices.isEmpty()) {
            return null;
        } else {
            throw new NonExistentSubexpressionException(this, indices.get(0));
        }
    }

    @Override
    public Expression getSubexpression(int index) {
        throw new NonExistentSubexpressionException(this, index);
    }

    @Override
    public void setSubexpression(int index, Expression subexpression) {
        throw new NonExistentSubexpressionException(this, index);
    }
    
    @Override
    public void setSubexpressions(List<Expression> subexpressions) {
    	if (subexpressions.size() > 0) {
    		throw new NonExistentSubexpressionException(this, 0);
    	}
    }    
    
    @Override
    public int getNumSubexpressions() {
        return 0;
    }

    @Override
    public List<Expression> getSubexpressions() {
        return new ArrayList<>();
    }
    
    @Override
    public List<Column> getColumnsInvolved() {
        return new ArrayList<>();
    }
    
    @Override
    public void remap(Table table) {        
    }
}
