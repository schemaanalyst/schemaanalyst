package org.schemaanalyst.sqlrepresentation.checkcondition;

import java.io.Serializable;

public interface CheckCondition extends Serializable {

	public abstract void accept(CheckConditionVisitor visitor);
}
