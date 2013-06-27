package org.schemaanalyst.javawriter;

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

public class ExpressionJavaWriter {

	protected JavaWriter javaWriter;
	
	public ExpressionJavaWriter(JavaWriter javaWriter) {
		this.javaWriter = javaWriter;
	}
	
	public String writeConstruction(Expression expression) {
		
		class WriterExpressionVisitor implements ExpressionVisitor {

			String java;
			
			public String writeExpression(Expression expression) {
				java = "";
				expression.accept(this);
				return java;
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
			}

			public void visit(OrExpression expression) {
			}

			public void visit(ParenthesisedExpression expression) {
				java = javaWriter.writeConstruction(
						expression, 
						writeExpression(expression.getSubexpression()));				
			}
			
			public void visit(RelationalExpression expression) {	
				java = javaWriter.writeConstruction(
						expression, 
						writeExpression(expression.getLHS()),
						writeExpression(expression.getRHS()));
			}

			public void visit(Value expression) {
				
			}
		}
		
		return (new WriterExpressionVisitor()).writeExpression(expression);
	}
}
