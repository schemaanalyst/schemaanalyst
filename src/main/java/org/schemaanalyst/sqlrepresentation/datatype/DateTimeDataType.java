package org.schemaanalyst.sqlrepresentation.datatype;

import org.schemaanalyst.data.DateTimeValue;

public class DateTimeDataType extends DataType {

    private static final long serialVersionUID = -4863979851522497316L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypePlusVisitor typePlusVisitor) {
    	typePlusVisitor.visit(this, new DateTimeValue());
    }
    
    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public DateTimeDataType duplicate() {
    	return new DateTimeDataType();
    }      
}
