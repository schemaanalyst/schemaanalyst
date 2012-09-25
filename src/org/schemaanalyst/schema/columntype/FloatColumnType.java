package org.schemaanalyst.schema.columntype;

public class FloatColumnType extends ColumnType {
	
	private static final long serialVersionUID = -1350006344393093990L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
