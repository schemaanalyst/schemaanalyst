package org.schemaanalyst.representation.datatype;

public class NumericDataType extends DataType implements PrecisionedAndScaled {

	private static final long serialVersionUID = 3323085304471030116L;

	private int precision;
	private int scale;
	
	public NumericDataType(int precision) {
		this(precision, 0);
	}
	
	public NumericDataType(int precision, int scale) {
		this.precision = precision;
		this.scale = scale;
	}	
	
	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}		
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
