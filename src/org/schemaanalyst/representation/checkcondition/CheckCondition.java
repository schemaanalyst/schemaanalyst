package org.schemaanalyst.representation.checkcondition;

import java.io.Serializable;

public interface CheckCondition extends Serializable {

	public abstract void accept(CheckConditionVisitor visitor);
}
