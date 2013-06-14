package org.schemaanalyst.representation.datatype;

public class TextDataType extends DataType {

	private static final long serialVersionUID = -98925722746243549L;

	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
