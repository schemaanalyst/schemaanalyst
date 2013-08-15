package org.schemaanalyst.sqlrepresentation.datatype;

public interface PrecisionedAndScaled {

    public Integer getPrecision();

    public void setPrecision(Integer precision);
    
    public Integer getScale();
    
    public void setScale(Integer precision);
}
