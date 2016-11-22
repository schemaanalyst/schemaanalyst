package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.NumericValue;

public class RealDataType extends DataType {

    private static final long serialVersionUID = -4773368969321372732L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new NumericValue());
    }
    
    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }    
    
    public RealDataType duplicate() {
    	return new RealDataType();
    }    
}
