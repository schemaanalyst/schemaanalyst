package org.schemaanalyst.sqlrepresentation.datatype;

public class RealDataType extends DoubleDataType {

    private static final long serialVersionUID = -4773368969321372732L;

    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
}
