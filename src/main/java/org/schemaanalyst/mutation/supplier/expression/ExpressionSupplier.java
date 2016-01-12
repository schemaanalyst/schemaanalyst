package org.schemaanalyst.mutation.supplier.expression;

import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionFilter;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionPath;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link Supplier} class that uses an {@link ExpressionFilter} to determine 
 * which subexpressions of an {@link Expression} to return.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class ExpressionSupplier<E extends Expression> extends IteratingSupplier<Expression, E> {

	private ExpressionFilter expressionFilter;
	
	public ExpressionSupplier(ExpressionFilter expressionFilter) {
		super(new Expression.Duplicator());
		this.expressionFilter = expressionFilter;
	}
	
	@Override
	public void putComponentBackInDuplicate(Expression expression) {
		// nothing to do here, unless the expression needs to be 
		// removed by some operator in the future.  If this is the
		// the case, the original expression paths will need to be
		// stored in order to get "back" to the parent expression.
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<E> getComponents(Expression expression) {
        ExpressionWalker expressionWalker = new ExpressionWalker(
                expression);
        List<ExpressionPath> paths = expressionWalker.filter(expressionFilter);
        List<E> expressions = new ArrayList<>();
        for (ExpressionPath path : paths) {
        	expressions.add((E) expression.getSubexpression(path));        	
        }
        return expressions;
	}
}
