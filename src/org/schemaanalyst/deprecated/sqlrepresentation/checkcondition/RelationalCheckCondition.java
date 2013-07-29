package org.schemaanalyst.deprecated.sqlrepresentation.checkcondition;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.logic.RelationalPredicate;

/**
 * @deprecated
 */
public class RelationalCheckCondition extends RelationalPredicate<Operand>
        implements CheckCondition {

    private static final long serialVersionUID = -7353662857915662883L;

    public RelationalCheckCondition(Operand lhs, RelationalOperator operator, Operand rhs) {
        super(lhs, operator, rhs);
    }

    public RelationalCheckCondition(Operand lhs, RelationalOperator operator, int rhs) {
        super(lhs, operator, new NumericValue(rhs));
    }

    public RelationalCheckCondition(Operand lhs, String operator, Operand rhs) {
        super(lhs, RelationalOperator.valueOf(operator), rhs);
    }

    public RelationalCheckCondition(Operand lhs, String operator, int rhs) {
        super(lhs, RelationalOperator.valueOf(operator), new NumericValue(rhs));
    }

    @Override
    public void accept(CheckConditionVisitor visitor) {
        visitor.visit(this);
    }
}
