package org.schemaanalyst.schema.columntype;

public class TextColumnType extends ColumnType {

	private static final long serialVersionUID = -98925722746243549L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
