package org.schemaanalyst.sqlrepresentation.datatype;

public class DateTimeDataType extends DataType {

    private static final long serialVersionUID = -4863979851522497316L;

    @Override
    public void accept(DataTypeVisitor typeVisitor) {
        typeVisitor.visit(this);
    }

    @Override
    public void accept(DataTypeCategoryVisitor categoryVisitor) {
        categoryVisitor.visit(this);
    }
    
    public DateTimeDataType duplicate() {
    	return new DateTimeDataType();
    }      
}
