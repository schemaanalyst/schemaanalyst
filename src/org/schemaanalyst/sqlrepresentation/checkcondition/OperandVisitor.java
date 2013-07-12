package org.schemaanalyst.sqlrepresentation.checkcondition;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;

/**
 * An interface (extended ValueVisitor) that visits operand elements that can be
 * used as predicate and function parameters.
 *
 * @author Phil McMinn
 * @deprecated
 */
public interface OperandVisitor {

    /**
     * Visits Column operands.
     *
     * @param column The Column instance to visit.
     */
    public void visit(Column column);

    /**
     * Visits Value operands.
     *
     * @param value The Value instance to visit.
     */
    public void visit(Value value);
}
