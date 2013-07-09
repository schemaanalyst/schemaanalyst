package org.schemaanalyst.datageneration.search.objective.expression;

import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
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

public class RowExpressionObjectiveFunctionFactory {

	protected Expression expression;
	protected boolean goalIsToSatisfy, allowNull;
	
	public RowExpressionObjectiveFunctionFactory(Expression expression, 
												 boolean goalIsToSatisfy, 
												 boolean allowNull) {
		this.expression = expression;
		this.goalIsToSatisfy = goalIsToSatisfy;
		this.allowNull = allowNull;
	}
	
	public ObjectiveFunction<Row> create() {
		
		class ExpressionDispatcher implements ExpressionVisitor {

			ObjectiveFunction<Row> objFun;
			
			ObjectiveFunction<Row> dispatch() {
				objFun = null;
				expression.accept(this);
				return objFun;
			}
			
			public void visit(AndExpression expression) {
			}

			public void visit(BetweenExpression expression) {
			}

			public void visit(Column expression) {
			}

			public void visit(InExpression expression) {
			}

			public void visit(ListExpression expression) {
			}

			public void visit(NullExpression expression) {
				objFun = new NullExpressionObjectiveFunction(
									expression, goalIsToSatisfy);
			}

			public void visit(OrExpression expression) {
				objFun = new OrExpressionObjectiveFunction(
						expression, goalIsToSatisfy, allowNull);				
			}

			public void visit(ParenthesisedExpression expression) {
			}

			public void visit(RelationalExpression expression) {
				objFun = new RelationalExpressionObjectiveFunction(
									expression, goalIsToSatisfy, allowNull);
			}

			public void visit(Value expression) {
			}		
		}		
		
		return (new ExpressionDispatcher()).dispatch();
	}
}
