package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.expression.AndExpression;
import org.schemaanalyst.representation.expression.BetweenExpression;
import org.schemaanalyst.representation.expression.ComposedExpression;
import org.schemaanalyst.representation.expression.Expression;
import org.schemaanalyst.representation.expression.ExpressionVisitor;
import org.schemaanalyst.representation.expression.InExpression;
import org.schemaanalyst.representation.expression.ListExpression;
import org.schemaanalyst.representation.expression.NullExpression;
import org.schemaanalyst.representation.expression.OrExpression;
import org.schemaanalyst.representation.expression.ParenthesisedExpression;
import org.schemaanalyst.representation.expression.RelationalExpression;

public class ExpressionSQLWriter {

	protected ValueSQLWriter valueSQLWriter;
	
	public void setValueSQLWriter(ValueSQLWriter valueSQLWriter) {
		this.valueSQLWriter = valueSQLWriter;
	}
	
	public String writeExpression(Expression expression) {
		
		class ExpressionSQLWriterVisitor implements ExpressionVisitor {

			String sql;
			
			public String writeExpression(Expression expression) {
				sql = "";
				expression.accept(this);
				return sql;
			}
			
			
			public void visit(AndExpression expression) {
				sql = writeAndExpression(expression);
				
			}

			public void visit(BetweenExpression expression) {
				sql = writeBetweenExpression(expression);
			}
			
			public void visit(Column expression) {
				sql = writeColumn(expression);				
			}
			
			public void visit(InExpression expression) {
				sql = writeInExpression(expression);				
			}
			
			public void visit(ListExpression expression) {
				sql = writeListExpression(expression);				
			}			
			
			public void visit(NullExpression expression) {
				sql = writeNullExpression(expression);				
			}

			public void visit(OrExpression expression) {
				sql = writeOrExpression(expression);
			}

			public void visit(ParenthesisedExpression expression) {
				sql = writeParenthesisedExpression(expression);
			}
			
			public void visit(RelationalExpression expression) {
				sql = writeRelationalExpression(expression);				
			}

			public void visit(Value expression) {
				sql = writeValue(expression);			
			}
		}
		
		return (new ExpressionSQLWriterVisitor()).writeExpression(expression);
	}
	
	public String writeAndExpression(AndExpression expression) {
		return writeComposedExpression(expression, "AND");
	}
	
	
	public String writeBetweenExpression(BetweenExpression expression) {
		String sql = writeExpression(expression.getSubject());
		if (expression.isNotBetween()) {
			sql += " NOT"; 
		}
		sql += " BETWEEN ";
		sql += writeExpression(expression.getLHS());
		sql += " AND ";
		sql += writeExpression(expression.getRHS());		
		return sql;		
		
	}
	
	public String writeColumn(Column column) {
		return column.toString();
	}
	
	protected String writeComposedExpression(ComposedExpression expression, String operator) {
		String sql = "";
		boolean first = true;
		for (Expression subexpression : expression.getSubexpressions()) {
			if (first) {
				first = false;
			} else {
				sql += " " + operator + " ";
			}
			sql += writeExpression(subexpression);
		}
		return sql;
	}	
	
	public String writeInExpression(InExpression expression) {
		String sql = writeExpression(expression.getLHS());
		if (expression.isNotIn()) {
			sql += " NOT"; 
		}
		sql += " IN ";
		sql += writeExpression(expression.getRHS());
		return sql;
	}
	
	public String writeListExpression(ListExpression expression) {
		return "(" + writeComposedExpression(expression, ", ") + ")";
	}	
	
	public String writeNullExpression(NullExpression expression) {
		String sql = writeExpression(expression.getSubexpression());
		sql += " IS ";
		if (expression.isNotNull()) {
			sql += "NOT "; 
		}
		sql += "NULL";
		return sql;
	}
	
	public String writeOrExpression(OrExpression expression) {
		return writeComposedExpression(expression, "OR");
	}	
	
	public String writeParenthesisedExpression(ParenthesisedExpression expression) {
		return " ("+ writeExpression(expression.getSubexpression()) + ") ";
	}	
	
	public String writeRelationalExpression(RelationalExpression expression) {
		String sql = writeExpression(expression.getLHS());
		sql += " " + expression.getRelationalOperator() + " ";
		sql += writeExpression(expression.getRHS());
		return sql;
	}	
	
	public String writeValue(Value expression) {
		return valueSQLWriter.writeValue(expression);
	}
}
