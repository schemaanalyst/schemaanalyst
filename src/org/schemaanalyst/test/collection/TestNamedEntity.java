package org.schemaanalyst.test.collection;

import org.junit.Test;
import org.schemaanalyst.util.collection.NamedEntity;

import static org.junit.Assert.*;

public class TestNamedEntity {

    class NamedEntityMock extends NamedEntity {
        public NamedEntityMock(String str) {
            super(str);
        }        
    }
    
    @Test
    public void testNamedEntity() {
        NamedEntityMock ne1 = new NamedEntityMock("Phil");
        assertEquals("Phil", ne1.getName());
        
        NamedEntityMock ne2 = new NamedEntityMock("Phil");
        assertEquals(ne1, ne2);
        
        NamedEntityMock ne3 = new NamedEntityMock("Chris");
        assertFalse(ne1.equals(ne3));
    }
    
    @Test
    public void testNullNamedEntity() {
        NamedEntityMock ne1 = new NamedEntityMock(null);
        assertNull(ne1.getName());
        
        NamedEntityMock ne2 = new NamedEntityMock(null);
        assertFalse(ne1.equals(ne2));
    }
}
