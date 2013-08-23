package org.schemaanalyst.mutation.supplier.expression;

import java.util.List;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.supplier.IntermediaryIteratingSupplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionPath;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilterWalker;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class RelationalExpressionOperatorSupplier
        extends IntermediaryIteratingSupplier<Expression, ExpressionPath, RelationalOperator> {

    public RelationalExpressionOperatorSupplier() {
        super(new Expression.Duplicator());
    }
    
    private ExpressionFilter expressionFilter = new ExpressionFilter() {
        @Override
        public boolean accept(Expression e) {
            return (e instanceof RelationalExpression);
        }
    };

    @Override
    public void initialise(Expression expression) {
        super.initialise(expression);
    }

    @Override
    protected List<ExpressionPath> getIntermediaries(Expression expression) {
        ExpressionFilterWalker expressionWalker = new ExpressionFilterWalker(
                expression);
        List<ExpressionPath> paths = expressionWalker.filter(expressionFilter);
        return paths;
    }

    @Override
    protected RelationalOperator getComponentFromIntermediary(
            Expression expression, ExpressionPath path) {
        RelationalExpression relationalExpression = (RelationalExpression) expression.getSubexpression(path);
        return relationalExpression.getRelationalOperator();
    }

    @Override
    public void putComponentBackInIntermediary(ExpressionPath expressionPath,
            RelationalOperator relationalOp) {
        RelationalExpression relationalExpression = (RelationalExpression) currentDuplicate
                .getSubexpression(expressionPath);
        relationalExpression.setRelationalOperator(relationalOp);
    }
}
