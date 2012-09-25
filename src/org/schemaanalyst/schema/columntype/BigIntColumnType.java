package org.schemaanalyst.schema.columntype;

public class BigIntColumnType extends IntColumnType {

	private static final long serialVersionUID = -7099333135061494192L;
	
	public BigIntColumnType() {
		super(true);
	}
	
	public BigIntColumnType(boolean signed) {
		super(signed);
	}
		
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}	
}
