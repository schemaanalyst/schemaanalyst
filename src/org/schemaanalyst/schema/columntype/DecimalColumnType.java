package org.schemaanalyst.schema.columntype;

public class DecimalColumnType extends NumericColumnType {

	private static final long serialVersionUID = -4297561355456316241L;
		
	public DecimalColumnType(int precision) {
		super(precision);
	}
	
	public DecimalColumnType(int precision, int scale) {
		super(precision, scale);
	}
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
