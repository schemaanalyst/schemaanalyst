package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.representation.expression.BetweenExpression;
import org.schemaanalyst.representation.expression.Expression;
import org.schemaanalyst.representation.expression.ExpressionVisitor;
import org.schemaanalyst.representation.expression.InExpression;
import org.schemaanalyst.representation.expression.RelationalExpression;

public class ExpressionSQLWriter {
	
	protected OperandSQLWriter operandSQLWriter;
	
	public void setOperandSQLWriter(OperandSQLWriter operandSQLWriter) {
		this.operandSQLWriter = operandSQLWriter;
	}
	
	public String writePredicate(Expression expression) {
		
		class ExpressionSQLWriterVisitor implements ExpressionVisitor {
			String sql;
			
			public String writeExpression(Expression expression) {
				sql = "";
				expression.accept(this);
				return sql;
			}
			
			public void visit(BetweenExpression expression) {
				sql = writeBetweenExpression(expression);
			}

			public void visit(InExpression expression) {
				sql = writeInExpression(expression);
			}

			public void visit(RelationalExpression expression) {
				sql = writeRelationalExpression(expression);
			}
			
		}
		
		return (new ExpressionSQLWriterVisitor()).writeExpression(expression);
	}
	
	public String writeBetweenExpression(BetweenExpression expression) {
		return expression.getColumn().getName() + 
			   " BETWEEN " + 
			   operandSQLWriter.writeOperand(expression.getLower()) + 
			   " AND "+ 
			   operandSQLWriter.writeOperand(expression.getUpper());
	}	
	
	public String writeInExpression(InExpression inPredicate) {
		StringBuilder sb = new StringBuilder();
		sb.append(inPredicate.getColumn().getName());
		
		boolean first = true;
		sb.append(" IN (");
		for (Value value : inPredicate.getValues()) {
			if (first) first = false;
			else sb.append(", ");
			sb.append(operandSQLWriter.writeOperand(value));
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String writeRelationalExpression(RelationalExpression expression) {
		return operandSQLWriter.writeOperand(expression.getLHS()) + " " +
               expression.getOperator() + " " + 
               operandSQLWriter.writeOperand(expression.getRHS());
	}
}
