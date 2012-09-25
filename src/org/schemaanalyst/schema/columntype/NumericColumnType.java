package org.schemaanalyst.schema.columntype;

public class NumericColumnType extends ColumnType implements PrecisionedAndScaled {

	private static final long serialVersionUID = 3323085304471030116L;

	private int precision;
	private int scale;
	
	public NumericColumnType(int precision) {
		this(precision, 0);
	}
	
	public NumericColumnType(int precision, int scale) {
		this.precision = precision;
		this.scale = scale;
	}	
	
	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}		
	
	public void accept(ColumnTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
