package org.schemaanalyst.schema.columntype;

public class SmallIntColumnType extends IntColumnType {

	private static final long serialVersionUID = 558676234375466697L;
	
	public SmallIntColumnType() {
		super(true);
	}
	
	public SmallIntColumnType(boolean signed) {
		super(signed);
	}	
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}

}
