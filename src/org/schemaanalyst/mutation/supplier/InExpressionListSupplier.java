package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionPath;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilterWalker;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

public class InExpressionListSupplier extends IteratingSupplier<Expression, ExpressionPath, List<Expression>> {

    private ExpressionFilter expressionFilter;
    
    public InExpressionListSupplier(Expression originalArtefact) {
        super(originalArtefact);
        this.expressionFilter = new ExpressionFilter() {            
            @Override
            public boolean accept(Expression e) {
                if (e instanceof InExpression) {
                    InExpression inExpression = (InExpression) e;
                    if (inExpression.getRHS() instanceof ListExpression) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    protected List<ExpressionPath> getIntermediaries(Expression artefact) {
        ExpressionFilterWalker expressionWalker = new ExpressionFilterWalker(artefact);
        return expressionWalker.filter(expressionFilter);
    }

    @Override
    protected List<Expression> getComponentFromIntermediary(Expression expression, ExpressionPath path) {
        InExpression inExpression = (InExpression) expression.getSubexpression(path);
        ListExpression rhs = (ListExpression) inExpression.getRHS();
        return rhs.getSubexpressions();
    }

    @Override
    public void putComponentBackInDuplicate(List<Expression> subexpressions) {
        ExpressionPath path = getDuplicateIntermediary();   
        InExpression inExpression = (InExpression) currentDuplicate.getSubexpression(path);
        ListExpression rhs = (ListExpression) inExpression.getRHS();
        rhs.setSubexpressions(subexpressions);
    }
}
