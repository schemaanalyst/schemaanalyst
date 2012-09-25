package org.schemaanalyst.schema.columntype;

public class TimeColumnType extends ColumnType {

	private static final long serialVersionUID = 2067286896633355675L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
