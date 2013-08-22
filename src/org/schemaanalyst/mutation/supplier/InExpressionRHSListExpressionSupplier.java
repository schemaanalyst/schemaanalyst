package org.schemaanalyst.mutation.supplier;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionPath;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilterWalker;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

public class InExpressionRHSListExpressionSupplier
		extends
		IntermediaryIteratingSupplier<Expression, ExpressionPath, List<Expression>> {

	private ExpressionFilter expressionFilter = new ExpressionFilter() {
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

	@Override
	public void initialise(Expression expression) {
		super.initialise(expression);
	}

	public void setDuplicate(Expression duplicate) {
	    super.setDuplicate(duplicate);
	    System.out.println(duplicate);
	}
	
	@Override
	protected List<ExpressionPath> getIntermediaries(Expression expression) {
		ExpressionFilterWalker expressionWalker = new ExpressionFilterWalker(
				expression);
		List<ExpressionPath> paths = expressionWalker.filter(expressionFilter);
		return paths;
	}

	@Override
	protected List<Expression> getComponentFromIntermediary(
			Expression expression, ExpressionPath path) {
		InExpression inExpression = (InExpression) expression
				.getSubexpression(path);
		ListExpression rhs = (ListExpression) inExpression.getRHS();
		return rhs.getSubexpressions();
	}

	@Override
	public void putComponentBackInIntermediary(ExpressionPath expressionPath,
			List<Expression> subexpressions) {
		InExpression inExpression = (InExpression) currentDuplicate
				.getSubexpression(expressionPath);
		ListExpression rhs = (ListExpression) inExpression.getRHS();
		rhs.setSubexpressions(subexpressions);
	}
}
