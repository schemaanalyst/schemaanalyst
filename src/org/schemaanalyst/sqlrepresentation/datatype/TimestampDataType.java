package org.schemaanalyst.sqlrepresentation.datatype;

public class TimestampDataType extends DataType {

	private static final long serialVersionUID = -4632277633586907266L;

	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
