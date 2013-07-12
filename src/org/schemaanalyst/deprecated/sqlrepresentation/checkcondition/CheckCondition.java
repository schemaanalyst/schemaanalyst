package org.schemaanalyst.deprecated.sqlrepresentation.checkcondition;

import java.io.Serializable;

/**
 * @deprecated
 */
public interface CheckCondition extends Serializable {

    public abstract void accept(CheckConditionVisitor visitor);
}
