package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionLeaf implements Expression {

    @Override
    public Expression getSubexpression(List<Integer> indexes) {
        if (indexes.size() == 0) {
            return null;
        } else {
            throw new NonExistentSubexpressionException(this, indexes.get(0));
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
    public int getNumSubexpressions() {
        return 0;
    }

    @Override
    public List<Expression> getSubexpressions() {
        return new ArrayList<>();
    }
}
