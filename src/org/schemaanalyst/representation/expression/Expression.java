package org.schemaanalyst.representation.expression;

import java.io.Serializable;

public interface Expression extends Serializable {

	public abstract void accept(ExpressionVisitor visitor);
}
