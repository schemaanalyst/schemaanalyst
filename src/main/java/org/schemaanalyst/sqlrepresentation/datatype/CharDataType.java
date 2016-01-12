package org.schemaanalyst.sqlrepresentation.datatype;

public class CharDataType extends DataType
        implements LengthLimited {

    private static final long serialVersionUID = 1159098580458473495L;
    protected Integer length;

    public CharDataType() {
        length = null;
    }

    public CharDataType(Integer length) {
        this.length = length;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public void setLength(Integer length) {
        this.length = length;
    }    
    
    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit((LengthLimited) this);
    }
    
    public CharDataType duplicate() {
    	return new CharDataType(length);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((length == null) ? 0 : length.hashCode());
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
        CharDataType other = (CharDataType) obj;
        if (length == null) {
            if (other.length != null)
                return false;
        } else if (!length.equals(other.length))
            return false;
        return true;
    } 
}
