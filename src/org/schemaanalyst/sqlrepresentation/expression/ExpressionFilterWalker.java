package org.schemaanalyst.sqlrepresentation.expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionFilterWalker {

    private Expression expression;

    public ExpressionFilterWalker(Expression expression) {
        this.expression = expression;
    }

    public List<ExpressionPath> filter(ExpressionFilter filter) {
        return filter(expression, filter, new ExpressionPath(),
                new ArrayList<ExpressionPath>());
    }

    private List<ExpressionPath> filter(
            Expression expression,
            ExpressionFilter filter, ExpressionPath path,
            List<ExpressionPath> paths) {

        if (filter.accept(expression)) {
            paths.add(path);
        }

        for (int index = 0; index < expression.getNumSubexpressions(); index++) {
            ExpressionPath currentPath = path.duplicate();
            path.add(index);
            filter(expression.getSubexpression(index), filter, currentPath, paths);
        }

        return paths;
    }
}
