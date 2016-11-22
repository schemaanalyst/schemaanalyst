package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.DateValue;

public class DateDataType extends DataType {

    private static final long serialVersionUID = -4384103397003392234L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }
    
    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new DateValue());
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public DateDataType duplicate() {
    	return new DateDataType();
    }     
}
