package org.schemaanalyst.schema.columntype;

public class CharColumnType extends ColumnType implements LengthLimited {
	
	private static final long serialVersionUID = 1159098580458473495L;
	
	private int length;
	
	public CharColumnType(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}	
}
