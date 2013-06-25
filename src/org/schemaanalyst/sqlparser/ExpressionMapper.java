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
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

public class ExpressionMapper {

	static Expression map(Table currentTable, TExpression node) {
		return (new ExpressionMapper(currentTable)).getExpression(node);
	}	
	
	Table currentTable;	
	
	ExpressionMapper(Table currentTable) {
		this.currentTable = currentTable;
	}

	// REFER TO the JavaDocs for TExpression
	// http://sqlparser.com/kb/javadoc/gudusoft/gsqlparser/nodes/TExpression.html
		
	Expression getExpression(TExpression node) {
		
		switch (node.getExpressionType()) {
		
		// *** OBJECT NAME/CONSTANT/SOURCE TOKEN/FUNCTION CALL ***
			case simple_object_name_t:			
				String columnName = QuoteStripper.stripQuotes(node.getObjectOperand());
				Column column = currentTable.getColumn(columnName);
				if (column == null) {
					throw new SQLParseException("Unknown column \"" + column + "\" for \"" + node + "\"");
				}
				return column;
			
			case simple_constant_t:
				TConstant constant = node.getConstantOperand();
				String valueString = constant.toString();
			
				if (QuoteStripper.isQuoted(valueString)) {
					return new StringValue(QuoteStripper.stripQuotes(valueString));
				} else {
					return new NumericValue(valueString);
				}	
			
			// *** UNARY ***
			case unary_minus_t:
				// is it just a negative number...
				if (node.getRightOperand().getExpressionType() == EExpressionType.simple_constant_t) {
					
					String value = node.toString();
					return new NumericValue(value);
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
				for (int i=0; i < expressionList.size(); i++) {
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
				return new BetweenExpression(getExpression(node.getBetweenOperand()), 
											 getExpression(node.getLeftOperand()),
											 getExpression(node.getRightOperand()),
											 notBetween);
		
			case arithmetic_compound_operator_t:
			case arithmetic_divide_t:
			case arithmetic_minus_t:
			case arithmetic_modulo_t:
			case arithmetic_plus_t:
			case arithmetic_t:
			case arithmetic_times_t:
			case array_constructor_t:			
			case arrayaccess_t:
			case assignment_t:
			case at_local_t:
			case at_time_zone_t:
			case bitwise_and_t:
			case bitwise_exclusive_or_t:
			case bitwise_or_t:
			case bitwise_shift_left_t:
			case bitwise_shift_right_t:
			case bitwise_t:
			case bitwise_xor_t:
			case case_t:
			case collate_t:
			case collection_constructor_list_t:
			case collection_constructor_multiset_t:
			case collection_constructor_set_t:
			case concatenate_t:
			case cursor_t:
			case datetime_t:
			case day_to_second_t:
			case exists_t:
			case exponentiate_t:
			case fieldselection_t:
			case floating_point_t:
			case function_t:
			case group_comparison_t:
			case group_t:
			case interval_t:
			case is_distinct_from_t:
			case is_document_t:
			case is_false_t:
			case is_of_type_t:
			case is_t:
			case is_true_t:
			case is_unknown_t:
			case left_join_t:
			case left_shift_t:
			case logical_not_t:
			case logical_t:
			case logical_xor_t:
			case member_of_t:
			case model_t:
			case multiset_except_distinct_t:
			case multiset_except_t:
			case multiset_intersect_distinct_t:
			case multiset_intersect_t:
			case multiset_t:
			case multiset_union_distinct_t:
			case multiset_union_t:
			case new_structured_type_t:
			case new_variant_type_t:
			case next_value_for_t:
			case not_initialized_yet_t:
			case object_access_t:
			case pattern_matching_t:
			case period_ldiff_t:
			case period_p_intersect_t:
			case period_p_normalize_t:
			case period_rdiff_t:
			case place_holder_t:
			case power_t:
			case range_t:
			case ref_arrow_t:
			case right_join_t:
			case right_shift_t:
			case row_constructor_t:
			case scope_resolution_t:
			case simple_source_token_t:
			case sqlserver_proprietary_column_alias_t:
			case subquery_t:
			case type_constructor_t:
			case typecast_t:
			case unary_absolutevalue_t:
			case unary_binary_operator_t:
			case unary_bitwise_not_t:
			case unary_connect_by_root_t:
			case unary_cuberoot_t:
			case unary_factorial_t:
			case unary_factorialprefix_t:
			case unary_left_unknown_t:
			case unary_plus_t:
			case unary_prior_t:
			case unary_right_unknown_t:
			case unary_squareroot_t:
			case unary_t:
			case units_t:
			case unknown_t:
			case until_changed_t:
			case xml_t:
			case year_to_month_t:			

			default:	
				throw new UnsupportedFeatureException(node);
		}
	}
}
