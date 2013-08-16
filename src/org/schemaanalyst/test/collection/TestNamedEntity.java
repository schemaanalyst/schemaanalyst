package org.schemaanalyst.test.collection;

import org.junit.Test;
import org.schemaanalyst.util.collection.Name;

import static org.junit.Assert.*;

public class TestNamedEntity {

    @Test
    public void testEqualsSameCase() {
        Name n1 = new Name("Phil");
        Name n2 = new Name("Phil");
        
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }
    
    @Test
    public void testEqualsNotSameCase() {
        Name n1 = new Name("Phil");
        Name n2 = new Name("phil");
        
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }
    
    @Test
    public void testNotEquals() {
        Name n1 = new Name("Philemon");
        Name n2 = new Name("Phil");
        
        assertFalse(n1.equals(n2));
    }    
}
