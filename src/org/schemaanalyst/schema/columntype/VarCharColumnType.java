package org.schemaanalyst.schema.columntype;

public class VarCharColumnType extends CharColumnType {

	private static final long serialVersionUID = 92948344507720958L;
	
	public VarCharColumnType(int length) {
		super(length);
	}
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}		
}
