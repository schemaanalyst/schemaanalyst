package org.schemaanalyst.schema.columntype;

public class RealColumnType extends DoubleColumnType {

	private static final long serialVersionUID = -4773368969321372732L;

	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
