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
    	return new TinyIntDataType(isSigned());
    }  
}
