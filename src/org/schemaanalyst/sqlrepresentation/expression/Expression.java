package org.schemaanalyst.sqlrepresentation.expression;

import java.util.List;

public interface Expression {

    public Expression getSubexpression(List<Integer> indexes);

    public Expression getSubexpression(int index, int... furtherIndexes);

    public Expression getSubexpression(int index);

    public int getNumSubexpressions();

    public List<Expression> getSubexpressions();

    public void accept(ExpressionVisitor visitor);
}
