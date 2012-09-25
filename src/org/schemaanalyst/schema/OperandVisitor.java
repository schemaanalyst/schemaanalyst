package org.schemaanalyst.schema;

import org.schemaanalyst.data.Value;

/**
 * An interface (extended ValueVisitor) that visits operand elements that can be used as predicate and function parameters. 
 * @author Phil McMinn
 *
 */

public interface OperandVisitor {
	
	/**
	 * Visits Column operands.
	 * @param column The Column instance to visit.
	 */
	public void visit(Column column);			
	
	/**
	 * Visits Value operands.
	 * @param value The Value instance to visit.
	 */
	public void visit(Value value);
}
