package org.schemaanalyst.representation.datatype;

public class DoubleDataType extends DataType {

	private static final long serialVersionUID = -6971949457336742243L;

	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
