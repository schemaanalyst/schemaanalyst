package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.sqlrepresentation.Column;

public abstract class ExpressionTree implements Expression {

    @Override
    public Expression getSubexpression(List<Integer> indexes) {
        if (indexes.size() == 0) {
            return null;
        }
        int index = indexes.get(0);
        int[] furtherIndexes = new int[indexes.size() - 1];
        for (int i = 1; i < indexes.size(); i++) {
            furtherIndexes[i] = indexes.get(i);
        }
        return getSubexpression(index, furtherIndexes);
    }

    @Override
    public Expression getSubexpression(int index, int... furtherIndexes) {
        Expression subexpression = getSubexpression(index);
        for (int i : furtherIndexes) {
            subexpression = subexpression.getSubexpression(i);
        }
        return subexpression;
    }

    @Override
    public abstract Expression getSubexpression(int index);

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
        return new ArrayList<Column>(columns);
    }
}
