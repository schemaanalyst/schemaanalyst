package org.schemaanalyst.unittest.util.collection;

import org.junit.Test;
import org.schemaanalyst.util.collection.Identifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestIdentifier {

    @Test
    public void testEqualsSameCase() {
        Identifier id1 = new Identifier("Phil");
        Identifier id2 = new Identifier("Phil");
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
    
    @Test
    public void testEqualsNotSameCase() {
        Identifier id1 = new Identifier("Phil");
        Identifier id2 = new Identifier("phil");
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
    
    @Test
    public void testNotEquals() {
        Identifier id1 = new Identifier("Philemon");
        Identifier id2 = new Identifier("Phil");
        
        assertFalse(id1.equals(id2));
    }    
}
