package org.schemaanalyst.sqlparser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.nodes.TConstant;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TExpressionList;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class ExpressionMapper {

    protected Table currentTable;

    public ExpressionMapper() {
    }

    public Expression getExpression(Table currentTable, TExpression node) {
        this.currentTable = currentTable;
        return getExpression(node);
    }

    // REFER TO the JavaDocs for TExpression
    // http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TExpression.html
    protected Expression getExpression(TExpression node) {

        switch (node.getExpressionType()) {

            // *** OBJECT NAME/CONSTANT/SOURCE TOKEN/FUNCTION CALL ***
            case simple_object_name_t:
                String columnName = QuoteStripper.stripQuotes(node.getObjectOperand());
                Column column = currentTable.getColumn(columnName);
                if (column == null) {
                    throw new SQLParseException("Unknown column \"" + column + "\" for \"" + node + "\"");
                }
                return new ColumnExpression(column);

            case simple_constant_t:
                TConstant constant = node.getConstantOperand();
                String valueString = constant.toString();

                if (QuoteStripper.isQuoted(valueString)) {
                    return new ConstantExpression(
                            new StringValue(QuoteStripper.stripQuotes(valueString)));
                } else {
                    return new ConstantExpression(
                            new NumericValue(valueString));
                }

            // *** UNARY ***
            case unary_minus_t:
                // is it just a negative number...
                if (node.getRightOperand().getExpressionType() == EExpressionType.simple_constant_t) {

                    String value = node.toString();
                    return new ConstantExpression(new NumericValue(value));
                }

            // *** LOGICAL ***		
            case logical_and_t:
                return new AndExpression(getExpression(node.getLeftOperand()),
                        getExpression(node.getRightOperand()));

            case logical_or_t:
                return new OrExpression(getExpression(node.getLeftOperand()),
                        getExpression(node.getRightOperand()));

            // *** EXPRESSION WITH PARENTHESIS ***		
            case parenthesis_t:
                TExpression subnode = node.getLeftOperand();
                return new ParenthesisedExpression(getExpression(subnode));

            // *** LIST EXPRESSION ***		
            case list_t:
                TExpressionList expressionList = node.getExprList();

                List<Expression> subexpressions = new ArrayList<>();
                for (int i = 0; i < expressionList.size(); i++) {
                    TExpression subNode = expressionList.getExpression(i);
                    subexpressions.add(getExpression(subNode));
                }

                return new ListExpression(subexpressions.toArray(new Expression[0]));

            // *** COMPARISON *** 		
            case simple_comparison_t:
                TExpression lhsNode = node.getLeftOperand();
                TExpression rhsNode = node.getRightOperand();
                String operatorString = node.getOperatorToken().toString();

                RelationalOperator op = RelationalOperator.getRelationalOperator(operatorString);
                return new RelationalExpression(getExpression(lhsNode), op, getExpression(rhsNode));

            // *** IN ***
            case in_t:
                boolean notIn = node.getNotToken() != null;

                return new InExpression(getExpression(node.getLeftOperand()),
                        getExpression(node.getRightOperand()),
                        notIn);

            // *** NULL *** 				
            case null_t:
                boolean notNull = node.getOperatorToken().toString().equals("NOTNULL") || node.getNotToken() != null;
                return new NullExpression(getExpression(node.getLeftOperand()), notNull);

            // *** BETWEEN ***
            case between_t:
                boolean notBetween = node.getNotToken() != null;
                boolean symmetric = node.isSymmetric();
                
                return new BetweenExpression(getExpression(node.getBetweenOperand()),
                        getExpression(node.getLeftOperand()),
                        getExpression(node.getRightOperand()),
                        notBetween,
                        symmetric);

            default:
                throw new UnsupportedSQLException(node);
        }
    }
}
