package org.schemaanalyst.schema.columntype;

public class BooleanColumnType extends ColumnType {

	private static final long serialVersionUID = -4384103397003392234L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}	
}
