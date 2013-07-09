package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionVisitor;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class ExpressionEvaluator {

	protected Expression expression;
	
	public ExpressionEvaluator(Expression expression) {
		this.expression = expression;
	}
	
	public Value evaluate(Row row) {
		
		class ExpressionDispatcher implements ExpressionVisitor {
			
			Row row;
			Value value;
			
			Value dispatch(Row row) {
				this.row = row;
				value = null;
				expression.accept(this);
				return value;
			}

			public void visit(AndExpression expression) {
			}

			public void visit(BetweenExpression expression) {
			}

			public void visit(Column expression) {
				value = row.getCell(expression).getValue();				
			}

			public void visit(InExpression expression) {
			}

			public void visit(ListExpression expression) {
			}

			public void visit(NullExpression expression) {
			}

			public void visit(OrExpression expression) {
			}

			public void visit(ParenthesisedExpression expression) {
			}

			public void visit(RelationalExpression expression) {
			}

			public void visit(Value expression) {
				value = expression;
			}
		}
		
		return (new ExpressionDispatcher()).dispatch(row);
	}
}
