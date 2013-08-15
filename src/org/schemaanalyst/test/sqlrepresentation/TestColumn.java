package org.schemaanalyst.test.sqlrepresentation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestColumn {

    @Test
    public void testDuplication() {
        DataType d1 = new IntDataType();
        Column c1 = new Column("test", d1);
        Column c2 = c1.duplicate();
        
        assertNotSame(
                "A column's duplicate should not be the same object", c1, c2);

        assertNotSame(
                "A column's duplicate data type should not be the same object", 
                c1.getDataType(), c2.getDataType());        
        
        assertEquals(
                "A column and its duplicate should be equal", c1, c2);
        
        assertEquals(
                "A column and its duplicate should have the same name", 
                c1.getName(), c2.getName());
        
        assertEquals(
                "A column and its duplicate should have the same data type", 
                c1.getDataType(), c2.getDataType());
        
        assertEquals(
                "A column and its duplicate should have the same hashcode", 
                c1.hashCode(), c2.hashCode());              
    }
    
}
