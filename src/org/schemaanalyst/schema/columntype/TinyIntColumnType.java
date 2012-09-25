package org.schemaanalyst.schema.columntype;

public class TinyIntColumnType extends IntColumnType {

	private static final long serialVersionUID = -8000767399672412543L;
	
	public TinyIntColumnType() {
		super(true);
	}
	
	public TinyIntColumnType(boolean signed) {
		super(signed);
	}		
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
