package org.schemaanalyst.sqlrepresentation.datatype;

public class VarCharDataType extends CharDataType {

    private static final long serialVersionUID = 92948344507720958L;

    public VarCharDataType() {
        super();
    }

    public VarCharDataType(Integer length) {
        super(length);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public VarCharDataType duplicate() {
    	return new VarCharDataType(length);
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
        VarCharDataType other = (VarCharDataType) obj;
        if (length == null) {
            if (other.length != null)
                return false;
        } else if (!length.equals(other.length))
            return false;
        return true;
    }    
}
