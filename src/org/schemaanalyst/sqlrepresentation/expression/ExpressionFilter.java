package org.schemaanalyst.sqlrepresentation.expression;


public interface ExpressionFilter {

    public boolean accept(Expression expression);
}
