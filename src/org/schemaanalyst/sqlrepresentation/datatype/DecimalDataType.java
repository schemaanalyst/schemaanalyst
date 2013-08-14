package org.schemaanalyst.sqlrepresentation.datatype;

public class DecimalDataType extends DataType
        implements PrecisionedAndScaled {

    private static final long serialVersionUID = 3323085304471030116L;
    private Integer precision, scale;

    public DecimalDataType() {
        this(null, null);
    }

    public DecimalDataType(Integer precision) {
        this(precision, 0);
    }

    public DecimalDataType(Integer precision, Integer scale) {
        this.precision = precision;
        this.scale = scale;
    }

    @Override
    public Integer getPrecision() {
        return precision;
    }

    @Override
    public Integer getScale() {
        return scale;
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((PrecisionedAndScaled) this);
    }
    
    public DecimalDataType duplicate() {
    	return new DecimalDataType(precision, scale);
    }    
}
