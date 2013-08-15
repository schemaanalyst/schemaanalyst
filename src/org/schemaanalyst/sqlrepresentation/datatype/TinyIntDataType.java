package org.schemaanalyst.sqlrepresentation.datatype;

public class TinyIntDataType extends IntDataType {

    private static final long serialVersionUID = -8000767399672412543L;

    public TinyIntDataType() {
        super(true);
    }

    public TinyIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public TinyIntDataType duplicate() {
    	return new TinyIntDataType(signed);
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
        TinyIntDataType other = (TinyIntDataType) obj;
        if (signed != other.signed)
            return false;
        return true;
    }     
}
