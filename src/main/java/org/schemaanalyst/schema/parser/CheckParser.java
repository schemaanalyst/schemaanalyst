package org.schemaanalyst.schema.parser;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import net.sf.jsqlparser.JSQLParserException;

import java.util.regex.Matcher;

public class CheckParser {
	private String sqlSchema;
	private Table table;
	private List<String> checkStatments;
	private List<CheckConstraint> checks = new ArrayList<CheckConstraint>();
	// \\s\\((.*)\\)
	// ((CHECK)\\s\\((.*)\\)|(CHECK)\\((.*)\\))
	private static Pattern PATTERN = Pattern.compile("\\((.*)\\)");
	private static Pattern PATTERN2 = Pattern.compile("((CHECK)\\s\\((.*)\\)|(CHECK)\\((.*)\\))");

	public CheckParser(Table table, String tableSqlCreateStatement) {
		this.sqlSchema = tableSqlCreateStatement;
		this.table = table;
	}

	private void parseCheckStatment() {
		List<String> statments = Arrays.asList(sqlSchema.split("\n"));
		checkStatments = new ArrayList<String>();
		for (String statment : statments) {
			if (statment.contains("CHECK")) {
				Matcher m = PATTERN2.matcher(statment.trim());
				while (m.find()) {
					//System.out.println("Group Counter " + m.groupCount());
					//System.err.println(m.group(1));
					Matcher m2 = PATTERN.matcher(m.group(1));
					while (m2.find()) {
						//System.err.println(m2.group(1));
						checkStatments.add(m2.group(1));
					}
				}
			}
		}
		//System.out.println(checkStatments);
	}

	public void printCheckStatments() throws JSQLParserException {
		this.parseCheckStatment();
		int i = 1;
		for (String stm : checkStatments) {
			Expression expression = this.getExpression(stm);
			// List<String> conditions = this.getConditions(stm, expression);
			//System.out.println(i + " " + expression);
			CheckConstraint check = new CheckConstraint(table, expression);
			checks.add(check);
			// System.out.println(i + " " + conditions);
			i++;
		}
		//System.err.println(checks);
	}

	public Expression getExpression(String stm) throws JSQLParserException {
		Expression exper = null;

		ExpressionParser parser = new ExpressionParser();

		parser.evaluate(stm);

		String operator = parser.getOperator();

		if (operator.equals("or")) {
			//System.out.println("OR expression");
			String leftVar = parser.getLeftVar();
			String rightVar = parser.getRightVar();
			// System.out.println(this.getExpression(leftVar));
			List<Expression> subexpression = new ArrayList<Expression>();
			// if ((this.getExpression(leftVar) instanceof RelationalExpression)
			// && (this.getExpression(rightVar) instanceof
			// RelationalExpression)) {
			subexpression.add(this.getExpression(leftVar));
			subexpression.add(this.getExpression(rightVar));
			// } else {
			// System.err.println("Shit multiple ORs in check");
			// }
			exper = new OrExpression(subexpression);
		} else if (operator.equals("and")) {
			//System.out.println("AND expression");
			String leftVar = parser.getLeftVar();
			String rightVar = parser.getRightVar();
			List<Expression> subexpression = new ArrayList<Expression>();
			if (this.getExpression(leftVar) instanceof RelationalExpression
					&& this.getExpression(rightVar) instanceof RelationalExpression) {
				subexpression.add(this.getExpression(leftVar));
				subexpression.add(this.getExpression(rightVar));
			} else {
				//System.err.println("Shit multiple ANDs in check");
			}

			exper = new AndExpression(subexpression);
			// exper = "and";
		} else if (operator.equals("in")) {
			//System.out.println("IN expression");
			String s1 = parser.getRightItemsList();
			List<Object> myList = null;
			if (s1.contains("(") && s1.contains(")")) {
				String replace = s1.replace("(", "");
				String replace1 = replace.replace(")", "");
				myList = new ArrayList<Object>(Arrays.asList(replace1.split(",")));
			}
			List<Expression> expressions = new ArrayList<Expression>();
			for (Object obj : myList) {
				expressions.add(new ConstantExpression(this.getValue(obj)));
			}
			exper = new InExpression(new ColumnExpression(table, table.getColumn(parser.getLeftVar())), new ListExpression(expressions), false);
			// exper = "in";
		} else if (operator.equals("between")) {
			//System.out.println("BETWEEN expression");
			// exper = "between";
		} else {
			//System.out.println("Relational expression");
			if (this.isItAConstantValue(parser.getRightVar())) {
				exper = new RelationalExpression(new ColumnExpression(table, table.getColumn(parser.getLeftVar())),
						this.relationalExpression(operator),
						new ColumnExpression(table, table.getColumn(parser.getRightVar())));
			} else {
				//System.out.println("There is a constant");
				//System.out.println(new NumericValue(parser.getRightVar()));
				exper = new RelationalExpression(new ColumnExpression(table, table.getColumn(parser.getLeftVar())),
						this.relationalExpression(operator),
						new ConstantExpression(new NumericValue(parser.getRightVar())));
			}
			// System.out.println(exper.toString());
			// exper = "r";
		}

		/*
		 * String exper = null; if (stm.contains("OR") || stm.contains("or")) {
		 * System.out.println("OR expression ==> " + stm.toUpperCase()); exper =
		 * "OR"; } else if (stm.contains("and") || stm.contains("AND")) {
		 * System.out.println("AND expression ==> " +
		 * stm.toUpperCase().split("AND")); exper = "AND"; } else if
		 * (stm.contains("in") || stm.contains("IN")) {
		 * System.out.println("IN expression ==> " +
		 * stm.toUpperCase().split("IN")); exper = "AND"; } else if
		 * (stm.contains("between") || stm.contains("BETWEEN")) {
		 * System.out.println("BETWEEN expression ==> " +
		 * stm.toUpperCase().split("BETWEEN")); exper = "BETWEEN"; } else {
		 * System.out.println("Relational expression ==> " + stm.trim()); exper
		 * = "R"; }
		 */
		return exper;
	}

	public List<String> getConditions(String stm, String expression) {
		List<String> conditions = new ArrayList<String>();
		if (!expression.equals("R")) {
			for (String condition : stm.split("((" + expression + "))")) {
				//System.out.println(condition.trim());
				conditions.add(condition.trim());
			}
		} else {
			//System.out.println(stm.trim());
			conditions.add(stm.trim());
		}

		return conditions;

	}

	private RelationalOperator relationalExpression(String operator) {
		return RelationalOperator.getRelationalOperator(operator);
	}

	private boolean isItAConstantValue(String value) {
		// Expression returnedExpression = null;
		if (!table.hasColumn(value)) {
			return false;
			// returnedExpression = new ConstantExpression(new
			// NumericValue(value));
		}
		return true;
	}

	private Value getValue(Object object) {
		Value value = null;
		if (object instanceof String || object instanceof Character) {
			StringValue stringvalue = new StringValue();
			stringvalue.setCharacterRange(32, 126);
			stringvalue.set((String) object);
			value = stringvalue;
			// value = new StringValue(rs.getString(column_name.toString()));
		} else if (object instanceof Integer)
			value = new NumericValue((Integer) object);
		else if (object instanceof Double)
			value = new NumericValue(new BigDecimal((Double) object));
		else if (object instanceof Long)
			value = new NumericValue(new BigDecimal((Long) object));
		else if (object instanceof Boolean)
			value = new BooleanValue((Boolean) object);
		else
			value = null;

		return value;
	}

	private void orExpression() {

	}

	private void betweenExpression() {

	}

	
	public List<CheckConstraint> getChecks() {
		return checks;
	}

	public void setChecks(List<CheckConstraint> checks) {
		this.checks = checks;
	}
}
