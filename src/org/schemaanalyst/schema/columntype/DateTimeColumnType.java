package org.schemaanalyst.schema.columntype;

public class DateTimeColumnType extends ColumnType {

	private static final long serialVersionUID = -4863979851522497316L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
