package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

public abstract class ExpressionLeaf implements Expression {

    @Override
    public Expression getSubexpression(ExpressionPath expressionPath) {
        List<Integer> indices = expressionPath.getIndices();
        if (indices.size() == 0) {
            return null;
        } else {
            throw new NonExistentSubexpressionException(this, indices.get(0));
        }
    }

    @Override
    public Expression getSubexpression(int index, int... furtherIndexes) {
        throw new NonExistentSubexpressionException(this, index);
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
