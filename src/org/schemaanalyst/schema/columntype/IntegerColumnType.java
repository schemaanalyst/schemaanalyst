package org.schemaanalyst.schema.columntype;

public class IntegerColumnType extends IntColumnType {
	
	private static final long serialVersionUID = 2169552578681268752L;
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
