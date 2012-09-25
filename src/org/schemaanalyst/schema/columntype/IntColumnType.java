package org.schemaanalyst.schema.columntype;

public class IntColumnType extends ColumnType implements Signed {

	private static final long serialVersionUID = 748636310116552558L;

	private boolean signed;
	
	public IntColumnType() {
		this(true);
	}
	
	public IntColumnType(boolean signed) {
		this.signed = signed;
	}
		
	public boolean isSigned() {
		return signed;
	}
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
