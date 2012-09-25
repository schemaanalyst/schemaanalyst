package org.schemaanalyst.schema.columntype;

import java.io.Serializable;

public abstract class ColumnType implements Serializable {

	private static final long serialVersionUID = -7105047166176083429L;

	public abstract void accept(ColumnTypeVisitor typeVisitor);
}
