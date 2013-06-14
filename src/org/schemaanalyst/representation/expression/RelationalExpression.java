package org.schemaanalyst.representation.expression;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

public class RelationalExpression extends RelationalPredicate<Operand>
									  implements Expression {

	private static final long serialVersionUID = -7353662857915662883L;
	
	public RelationalExpression(Operand lhs, RelationalOperator operator, Operand rhs) {
		super(lhs, operator, rhs);
	}
        
        public RelationalExpression(Operand lhs, RelationalOperator operator, int rhs) {
                super(lhs, operator, new NumericValue(rhs));
        }
	
	public RelationalExpression(Operand lhs, String operator, Operand rhs) {
		super(lhs, operator, rhs);
	}	

	public RelationalExpression(Operand lhs, String operator, int rhs) {
		super(lhs, operator, new NumericValue(rhs));
	}	
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}
}
