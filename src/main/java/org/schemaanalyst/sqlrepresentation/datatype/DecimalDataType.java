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
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Override
    public Integer getScale() {
        return scale;
    }
    
    @Override
    public void setScale(Integer scale) {
        this.scale = scale;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((precision == null) ? 0 : precision.hashCode());
        result = prime * result + ((scale == null) ? 0 : scale.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DecimalDataType other = (DecimalDataType) obj;
        if (precision == null) {
            if (other.precision != null)
                return false;
        } else if (!precision.equals(other.precision))
            return false;
        if (scale == null) {
            if (other.scale != null)
                return false;
        } else if (!scale.equals(other.scale))
            return false;
        return true;
    }
}
