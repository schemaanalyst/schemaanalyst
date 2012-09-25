package org.schemaanalyst.schema;

import java.io.Serializable;

public interface CheckPredicate extends Serializable {

	public abstract void accept(CheckPredicateVisitor visitor);
}
