package org.schemaanalyst.sqlrepresentation.checkcondition;

import java.io.Serializable;


/**
 * Interface that represents an abstraction for elements that can be placed as parameters to predicates and functions.
 * @author Phil McMinn
 * @deprecated
 */
public interface Operand extends Serializable {

	/**
	 * Method for accepting visitors of type OperandVisitor.
	 * @param visitor The OperandVisitor instance visiting this constraint. 
	 */	
	public void accept(OperandVisitor visitor);

}
