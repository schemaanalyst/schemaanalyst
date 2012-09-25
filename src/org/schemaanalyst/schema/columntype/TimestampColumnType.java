package org.schemaanalyst.schema.columntype;

public class TimestampColumnType extends ColumnType {

	private static final long serialVersionUID = -4632277633586907266L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
