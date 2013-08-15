package org.schemaanalyst.sqlrepresentation.datatype;

public class BigIntDataType extends IntDataType {

    private static final long serialVersionUID = -7099333135061494192L;

    public BigIntDataType() {
        super(true);
    }

    public BigIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public BigIntDataType duplicate() {
    	return new BigIntDataType(signed);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (signed ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BigIntDataType other = (BigIntDataType) obj;
        if (signed != other.signed)
            return false;
        return true;
    }    
}
