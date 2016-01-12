package org.schemaanalyst.sqlrepresentation.expression;

/**
 * An {@link ExpressionFilter} is test for filtering expressions.
 * 
 * It is used by {@link ExpressionWalker} to check if the
 * {@link ExpressionPath} of a subexpression in the tree of an
 * {@link Expression} should be returned by the
 * {@link ExpressionWalker#filter(ExpressionFilter)} method.
 * 
 * @author Phil McMinn
 * 
 */

public interface ExpressionFilter {

	/**
	 * A test
	 * 
	 * @param expression
	 *            the expression being tested.
	 * @return true if the expression is to be filtered.
	 */
	public boolean accept(Expression expression);
}
