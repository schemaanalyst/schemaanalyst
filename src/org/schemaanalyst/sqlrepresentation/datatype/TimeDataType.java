package org.schemaanalyst.sqlrepresentation.datatype;

public class TimeDataType extends DataType {

	private static final long serialVersionUID = 2067286896633355675L;

	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

	public void accept(DataTypeCategoryVisitor categoryVisitor) {
		categoryVisitor.visit(this);
	}		
}
