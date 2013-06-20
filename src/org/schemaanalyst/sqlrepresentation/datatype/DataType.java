package org.schemaanalyst.sqlrepresentation.datatype;

import java.io.Serializable;

public abstract class DataType implements Serializable {

	private static final long serialVersionUID = -7105047166176083429L;

	public abstract void accept(DataTypeVisitor typeVisitor);
}
