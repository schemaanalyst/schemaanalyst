package org.schemaanalyst.sqlrepresentation.datatype;

public class NumericDataType extends DataType implements PrecisionedAndScaled {

	private static final long serialVersionUID = 3323085304471030116L;

	private Integer precision, scale;
	
	public NumericDataType() {
		this(null, null);
	}	
	
	public NumericDataType(Integer precision) {
		this(precision, 0);
	}
	
	public NumericDataType(Integer precision, Integer scale) {
		this.precision = precision;
		this.scale = scale;
	}	
	
	public Integer getPrecision() {
		return precision;
	}

	public Integer getScale() {
		return scale;
	}		
	
	public void accept(DataTypeVisitor typeVisitor) {
		typeVisitor.visit(this);
	}
}
