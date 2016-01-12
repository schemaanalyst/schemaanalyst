package org.schemaanalyst.unittest.sqlrepresentation.expression;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestExpressionFilterWalker {

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
	public void testFilterByType() {
	
		ExpressionFilter constantExpressionsFilter = new ExpressionFilter() {
	
			@Override
			public boolean accept(Expression expression) {
				return expression instanceof ConstantExpression;
			}
				
		};
		
		ExpressionWalker walker = new ExpressionWalker(testExpression);
		List<ExpressionPath> paths = walker.filter(constantExpressionsFilter);
		
		assertEquals(3, paths.size());
		assertEquals(new ExpressionPath(0, 0), paths.get(0));
		assertEquals(new ExpressionPath(0, 1), paths.get(1));
		assertEquals(new ExpressionPath(1, 0), paths.get(2));
	}
	
	@Test
	public void testFilterByTypeAndCharacteristic() {
	
		ExpressionFilter constantExpressionsFilter = new ExpressionFilter() {
	
			@Override
			public boolean accept(Expression expression) {
				if (expression instanceof ConstantExpression) {
					ConstantExpression constantExpression = (ConstantExpression) expression;
					if (((NumericValue) constantExpression.getValue()).get().intValue() == 3) {
						return true;
					}
				}
				return false;
			}
				
		};
		
		ExpressionWalker walker = new ExpressionWalker(testExpression);
		List<ExpressionPath> paths = walker.filter(constantExpressionsFilter);
		
		assertEquals(1, paths.size());
		assertEquals(new ExpressionPath(1, 0), paths.get(0));
	}	
	
	@Test
	public void testFilterNonRevealing() {
	
		ExpressionFilter constantExpressionsFilter = new ExpressionFilter() {
	
			@Override
			public boolean accept(Expression expression) {
				if (expression instanceof ConstantExpression) {
					ConstantExpression constantExpression = (ConstantExpression) expression;
					if (((NumericValue) constantExpression.getValue()).get().intValue() == 10) {
						return true;
					}
				}
				return false;
			}
				
		};
		
		ExpressionWalker walker = new ExpressionWalker(testExpression);
		List<ExpressionPath> paths = walker.filter(constantExpressionsFilter);
		assertEquals(0, paths.size());
	}		
	
}
