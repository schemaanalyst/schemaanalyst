package org.schemaanalyst.sqlrepresentation.datatype;

public class SmallIntDataType extends IntDataType {

    private static final long serialVersionUID = 558676234375466697L;

    public SmallIntDataType() {
        super(true);
    }

    public SmallIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public SmallIntDataType duplicate() {
    	return new SmallIntDataType(signed);
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
        SmallIntDataType other = (SmallIntDataType) obj;
        if (signed != other.signed)
            return false;
        return true;
    }     
}
