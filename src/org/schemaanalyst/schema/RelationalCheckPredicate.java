package org.schemaanalyst.schema;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

public class RelationalCheckPredicate extends RelationalPredicate<Operand>
									  implements CheckPredicate {

	private static final long serialVersionUID = -7353662857915662883L;
	
	public RelationalCheckPredicate(Operand lhs, RelationalOperator operator, Operand rhs) {
		super(lhs, operator, rhs);
	}
        
        public RelationalCheckPredicate(Operand lhs, RelationalOperator operator, int rhs) {
                super(lhs, operator, new NumericValue(rhs));
        }
	
	public RelationalCheckPredicate(Operand lhs, String operator, Operand rhs) {
		super(lhs, operator, rhs);
	}	

	public RelationalCheckPredicate(Operand lhs, String operator, int rhs) {
		super(lhs, operator, new NumericValue(rhs));
	}	
	
	public void accept(CheckPredicateVisitor visitor) {
		visitor.visit(this);
	}
}
