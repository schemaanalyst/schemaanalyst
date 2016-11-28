package org.schemaanalyst.schema.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.*;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;

public class ExpressionParser {

	private String expression;
	private String rightVar;
	private String leftVar;
	private String operator;
	private String rightItemsList;
	private String leftItemsList;
	private String betweenExpressionStart;
	private String betweenExpressionEnd;
	public ItemsList rightItemsLists;
	
	public ExpressionParser() {
	}

	public ExpressionParser(String expression) {
		this.expression = expression;
	}
	
	
	public void evaluate(String expr) throws JSQLParserException {
		this.expression = expr;
		Expression parseExpression = CCJSqlParserUtil.parseCondExpression(expr);
		//System.out.println(parseExpression);
		ExpressionDeParser deparser = new ExpressionDeParser() {
			@Override
			public void visit(GreaterThanEquals expression) {
				//super.visit(expression);
//				System.out.println("*********** GreaterThanEquals ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
//				System.out.println("is it Not = " + expression.isNot());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = ">=";

			}
			
			@Override
			public void visit(EqualsTo expression) {
				//super.visit(expression);
//				System.out.println("*********** EqualsTo ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "=";
			}
			
			@Override
			public void visit(GreaterThan expression) {
//				//super.visit(expression);
//				System.out.println("*********** GreaterThan ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = ">";
			}
			@Override
			public void visit(InExpression expression) {
//				//super.visit(expression);
//				System.out.println("*********** InExpression ***********");
//				System.out.println("Operator = IN");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right Items List = " + expression.getRightItemsList().toString());
//				//System.out.println("Left Items List = " + expression.getLeftItemsList().toString());
//				System.out.println("is it NOT = " + expression.isNot());
//				System.out.println("Oracle Join Syntax = " + expression.getOldOracleJoinSyntax());
//				System.out.println("Oracle Prior Position = " + expression.getOraclePriorPosition());

				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightItemsList().toString();
				if (expression.getLeftItemsList() != null)
					leftItemsList = expression.getLeftItemsList().toString();
				if (expression.getRightItemsList() != null) {
					rightItemsList = expression.getRightItemsList().toString();
					rightItemsLists = expression.getRightItemsList();
				}
				operator = "in";
			}
			@Override
			public void visit(LikeExpression expression) {
				//super.visit(expression);
//				System.out.println("*********** LikeExpression ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "like";
			}
			
			@Override
			public void visit(MinorThan expression) {
//				//super.visit(expression);
//				System.out.println("*********** MinorThan ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "<";
			}
			
			@Override
			public void visit(MinorThanEquals expression) {
				//super.visit(expression);
//				System.out.println("*********** MinorThanEquals ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "<=";
			}
			
			@Override
			public void visit(NotEqualsTo expression) {
				//super.visit(expression);
//				System.out.println("*********** NotEqualsTo ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "notequalto";
			}
			
			@Override
			public void visit(OrExpression expression) {
				//super.visit(expression);
//				System.out.println("*********** OrExpression ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "or";
			}
			
			@Override
			public void visit(AndExpression expression) {
				//super.visit(expression);
//				System.out.println("*********** AndExpression ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Right = " + expression.getRightExpression().toString());
//				System.out.println("Operator = " + expression.getStringExpression());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getRightExpression().toString();
				operator = "and";
			}
			
			@Override
			public void visit(Between expression) {
				//super.visit(expression);
//				System.out.println("*********** BetweenExpression ***********");
//				System.out.println("Left = " + expression.getLeftExpression().toString());
//				System.out.println("Operator = BETWEEN");
//				System.out.println("Expression Start = " + expression.getBetweenExpressionStart());
//				System.out.println("Expression End = " + expression.getBetweenExpressionEnd());
//				System.out.println("is it Not = " + expression.isNot());
				leftVar = expression.getLeftExpression().toString();
				rightVar = expression.getBetweenExpressionStart().toString();
				betweenExpressionStart = expression.getBetweenExpressionStart().toString();
				betweenExpressionEnd = expression.getBetweenExpressionEnd().toString();
				operator = "between";
			}
		};
		StringBuilder b = new StringBuilder();
		deparser.setBuffer(b);
		parseExpression.accept(deparser);

	}
	
	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public String getRightVar() {
		return rightVar;
	}


	public void setRightVar(String rightVar) {
		this.rightVar = rightVar;
	}


	public String getLeftVar() {
		return leftVar;
	}


	public void setLeftVar(String leftVar) {
		this.leftVar = leftVar;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRightItemsList() {
		return rightItemsList;
	}

	public void setRightItemsList(String rightItemsList) {
		this.rightItemsList = rightItemsList;
	}

	public String getBetweenExpressionStart() {
		return betweenExpressionStart;
	}

	public void setBetweenExpressionStart(String betweenExpressionStart) {
		this.betweenExpressionStart = betweenExpressionStart;
	}

	public String getBetweenExpressionEnd() {
		return betweenExpressionEnd;
	}

	public void setBetweenExpressionEnd(String betweenExpressionEnd) {
		this.betweenExpressionEnd = betweenExpressionEnd;
	}

	public String getLeftItemsList() {
		return leftItemsList;
	}

	public void setLeftItemsList(String leftItemsList) {
		this.leftItemsList = leftItemsList;
	}
	
}
