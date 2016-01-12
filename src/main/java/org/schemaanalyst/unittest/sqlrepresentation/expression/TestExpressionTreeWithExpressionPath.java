package org.schemaanalyst.unittest.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.*;

import static org.junit.Assert.assertSame;

public class TestExpressionTreeWithExpressionPath {

	Expression exp_0_0 = new ConstantExpression(new NumericValue(1));
	Expression exp_0_1 = new ConstantExpression(new NumericValue(2));
	Expression exp_0 = new RelationalExpression(
			exp_0_0,
			RelationalOperator.GREATER_OR_EQUALS,
			exp_0_1);	
	
	Expression exp_1_0 = new ConstantExpression(new NumericValue(3));
	Expression exp_1 = new ParenthesisedExpression(exp_1_0);
	
	Expression exp_2 = new ColumnExpression(new Table("test"), new Column("column", new IntDataType()));
	
	Expression testExpression = new AndExpression(
			exp_0,
			exp_1,
			exp_2);
	
	@Test
	public void testEmptyPath() {		
		ExpressionPath path = new ExpressionPath();
		assertSame(testExpression, testExpression.getSubexpression(path));		
	}
	
	@Test
	public void testOneLevelPaths() {		
		assertSame(exp_0, testExpression.getSubexpression(new ExpressionPath(0)));
		assertSame(exp_1, testExpression.getSubexpression(new ExpressionPath(1)));
		assertSame(exp_2, testExpression.getSubexpression(new ExpressionPath(2)));
	}
	
	@Test
	public void testTwoLevelPaths() {		
		assertSame(exp_0_0, testExpression.getSubexpression(new ExpressionPath(0, 0)));
		assertSame(exp_0_1, testExpression.getSubexpression(new ExpressionPath(0, 1)));
		assertSame(exp_1_0, testExpression.getSubexpression(new ExpressionPath(1, 0)));
	}	
	
	@Test(expected=NonExistentSubexpressionException.class)
	public void testNonExistentPath() {		
		testExpression.getSubexpression(new ExpressionPath(0, 0, 5));		
	}
}
