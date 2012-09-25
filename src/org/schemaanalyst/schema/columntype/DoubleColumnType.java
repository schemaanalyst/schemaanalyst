package org.schemaanalyst.schema.columntype;

public class DoubleColumnType extends ColumnType {

	private static final long serialVersionUID = -6971949457336742243L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
