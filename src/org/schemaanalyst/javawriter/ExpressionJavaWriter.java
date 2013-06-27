package org.schemaanalyst.javawriter;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ComposedExpression;
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
	protected ValueJavaWriter valueJavaWriter;
	
	public ExpressionJavaWriter(JavaWriter javaWriter,
								ValueJavaWriter valueJavaWriter) {
		this.javaWriter = javaWriter;
		this.valueJavaWriter = valueJavaWriter;
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
				java += writeComposedExpresionConstruction(expression);				
			}

			public void visit(BetweenExpression expression) {
				java = javaWriter.writeConstruction(
						expression,
						writeExpression(expression.getSubject()),						
						writeExpression(expression.getLHS()),
						writeExpression(expression.getRHS()),
						javaWriter.writeBoolean(expression.isNotBetween()));					
			}
			
			public void visit(Column expression) {
				java += javaWriter.writeGetColumn(expression);
			}
			
			public void visit(InExpression expression) {
				java = javaWriter.writeConstruction(
						expression, 
						writeExpression(expression.getLHS()),
						writeExpression(expression.getRHS()),
						javaWriter.writeBoolean(expression.isNotIn()));				
			}
			
			public void visit(ListExpression expression) {
				java += writeComposedExpresionConstruction(expression);
			}			
			
			public void visit(NullExpression expression) {
				java = javaWriter.writeConstruction(
						expression, 
						writeExpression(expression.getSubexpression()),
						javaWriter.writeBoolean(expression.isNotNull()));
			}

			public void visit(OrExpression expression) {
				java += writeComposedExpresionConstruction(expression);
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
						javaWriter.writeEnumValue(expression.getRelationalOperator()),
						writeExpression(expression.getRHS()));
			}

			public void visit(Value expression) {
				java += valueJavaWriter.writeConstruction(expression);
			}
		}
		
		return (new WriterExpressionVisitor()).writeExpression(expression);
	}
	
	String writeComposedExpresionConstruction(ComposedExpression expression) {
		List<String> args = new ArrayList<>();
		for (Expression subexpression : expression.getSubexpressions()) {
			args.add(writeConstruction(subexpression));
		}
		return javaWriter.writeConstruction(expression, args);				
	}	
}
