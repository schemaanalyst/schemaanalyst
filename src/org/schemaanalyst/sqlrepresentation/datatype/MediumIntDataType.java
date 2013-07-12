package org.schemaanalyst.sqlrepresentation.datatype;

public class MediumIntDataType extends IntDataType {

    private static final long serialVersionUID = -5770480167229887183L;

    public MediumIntDataType() {
        super(true);
    }

    public MediumIntDataType(boolean signed) {
        super(signed);
    }

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
}