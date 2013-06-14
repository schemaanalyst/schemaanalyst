package org.schemaanalyst.representation.datatype;

public class DecimalDataType extends NumericDataType {

	private static final long serialVersionUID = -4297561355456316241L;
		
	public DecimalDataType(int precision) {
		super(precision);
	}
	
	public DecimalDataType(int precision, int scale) {
		super(precision, scale);
	}
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
