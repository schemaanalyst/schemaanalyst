package org.schemaanalyst.sqlrepresentation.datatype;

public class MediumIntDataType extends IntDataType {

    private static final long serialVersionUID = -5770480167229887183L;

    public MediumIntDataType() {
        super(true);
    }

    public MediumIntDataType(boolean signed) {
        super(signed);
    }

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    public MediumIntDataType duplicate() {
        return new MediumIntDataType(signed);
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
        MediumIntDataType other = (MediumIntDataType) obj;
        if (signed != other.signed)
            return false;
        return true;
    }    
}