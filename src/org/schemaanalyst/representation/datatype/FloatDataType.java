package org.schemaanalyst.representation.datatype;

public class FloatDataType extends DataType {
	
	private static final long serialVersionUID = -1350006344393093990L;

	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
